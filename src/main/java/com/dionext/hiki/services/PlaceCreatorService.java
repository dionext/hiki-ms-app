package com.dionext.hiki.services;

import com.dionext.hiki.db.cache.JWikiPropertyCache;
import com.dionext.hiki.db.entity.JGeoWikidata;
import com.dionext.hiki.db.entity.JWikiProperty;
import com.dionext.hiki.db.repositories.JCountryRepository;
import com.dionext.hiki.db.repositories.JGeoWikidataRepository;
import com.dionext.hiki.dto.JWikiPropertyValue;
import com.dionext.hiki.dto.JWikiPropertyWithValue;
import com.dionext.site.dto.*;
import com.dionext.utils.HtmlUtils;
import com.dionext.utils.OUtils;
import com.dionext.utils.Utils;
import com.dionext.utils.exceptions.DioRuntimeException;
import com.dionext.utils.exceptions.ResourceFindException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
@Service
@SuppressWarnings({"java:S5663", "java:S1192", "java:S3776", "java:S6541"})
public class PlaceCreatorService extends HikingLandPageCreatorService {

    // при MaxLevelForSinglePage = 2 отдельными страницами будут сгенерированы уровни 0, 1, 2 (на 2 будут все его дочерние регионы)
    //1 Austria 2 Tyrol
    public static final int MAX_LEVEL_FOR_SINGLE_PAGE = 20;
    public static final String IMAGES_P_FOLDER = "images_p";
    private static final String COUNTRIES_LIST_FOLDER = "list";
    private static final String WIKIMEDIA_ADDRESS = "https://en.wikipedia.org";
    private static final String WIKIMEDIA_ADDRESS_FOR_FILE_HREF = "https://commons.wikimedia.org/wiki/File:";
    JGeoWikidataRepository jGeoWikidataRepository;
    JCountryRepository jCountryRepository;
    JWikiPropertyCache jWikiPropertyCache;
    @Value("${web-config.multiSites.hiking.places.useExternalUrlForImages}")
    private boolean useExternalUrlForImages;

    public static boolean isAppropriateImage(String image) {
        if (Strings.isNullOrEmpty(image)) {
            return false;
        }
        String imageLw = image.toLowerCase();
        return !imageLw.contains("satellite") && !imageLw.contains("topographic");
    }

    @Autowired
    public void setjGeoWikidataRepository(JGeoWikidataRepository jGeoWikidataRepository) {
        this.jGeoWikidataRepository = jGeoWikidataRepository;
    }

    @Autowired
    public void setjCountryRepository(JCountryRepository jCountryRepository) {
        this.jCountryRepository = jCountryRepository;
    }

    @Autowired
    public void setjWikiPropertyCache(JWikiPropertyCache jWikiPropertyCache) {
        this.jWikiPropertyCache = jWikiPropertyCache;
    }

    public String createPlacesPage(SrcPageContent srcPageContent) {
        String src = srcPageContent.getBody();
        String placeName = pageInfo.getRelativePath();
        int dotIndex = placeName.lastIndexOf(".htm");
        if (dotIndex > -1) {
            placeName = placeName.substring(0, dotIndex);
        }
        int slashIndex = placeName.indexOf("/");
        if (slashIndex > -1) {
            placeName = placeName.substring(slashIndex + 1);
            placeName = HtmlUtils.urlDecode(placeName);
        } else placeName = "list";

        JGeoWikidata item = null;
        if (!"list".equals(placeName)) {
            item = findPlaceByName(placeName);
            if (item == null){
                throw new ResourceFindException("Place with name \"" + placeName
                    + "\" not found in our catalogue");
            }
        }
        if (item != null) {
            log.debug("Generate place level " + item.getLevel() + " " + item.LocalFileName());
        } else
            log.debug("Generate countries list ");
        return generatePlacePage(item, src);

    }
    public List<PageUrl> findAllPlacesPages() {
        List<JGeoWikidata> items = jGeoWikidataRepository.findAllHaveChildren();
        List<PageUrl> pages = new ArrayList<>();
        for (var item : items) {
            String relUri = "places/" + HtmlUtils.urlEncodePath(item.GetLocalUrl());
            PageUrl pageUrl = new PageUrl();
            pageUrl.setRelativePath(relUri);
            for(String lang : pageInfo.getSiteSettings().getSiteLangs()){
                PageUrlAlt pageUrlAlt = new PageUrlAlt();
                pageUrlAlt.setLang(lang);
                pageUrl.getAltUrls().add(pageUrlAlt);
            }
            pages.add(pageUrl);
        }
        return pages;
    }

    @Override
    public String createHeadContentType() {
        StringBuilder str = new StringBuilder();
        str.append(super.createHeadContentType());
        if (geoPageInfo.getItem() != null &&
                geoPageInfo.getItem().getChildrenCount() == 0) {
            str.append("""
                     <meta name="robots" content="noindex"/>
                     """);
        }
        return str.toString();
    }


    public final JGeoWikidata findPlaceByName(String name) {
        JGeoWikidata item = null;
        item = Utils.firstOrNull(jGeoWikidataRepository.findByName(name));
        if (item == null) {
            log.debug("Warning Item not quick found by Name: " + name);
        }
        if (item == null) {
            item = Utils.firstOrNull(jGeoWikidataRepository.findByEnwikiLink(name));
        }
        if (item == null) {
            item = Utils.firstOrNull(jGeoWikidataRepository.findByNativewikiLink(name));
        }
        if (item == null) {
            item = Utils.firstOrNull(jGeoWikidataRepository.findByLabelEn(name));
        }
        if (item == null) {
            item = Utils.firstOrNull(jGeoWikidataRepository.findByLabelNative(name));
        }
        if (item == null && name.startsWith("Q")) {
            Optional<JGeoWikidata> o = jGeoWikidataRepository.findById(name);
            item = o.orElse(null);
        }
        return item;
    }

    private String generateRootCountries() {
        List<JGeoWikidata> items = jGeoWikidataRepository.findByParent(null);//to do ?
        StringBuilder str = new StringBuilder();
        for (var item : items) {
            str.append("""
                    <div class="col-md-4 mb-3">"""); //col-md-4 - 3 колонки на больших экранах, col-6 - 2 на маленьких
            //mb-3 - нужен для margin между строк
            str.append(generatePlaceBlock(item, false));
            str.append("</div>");
        }
        return str.length() > 0 ? str.toString() : null;
    }

    private String generatePlacePage(JGeoWikidata item, String src) {

        StringBuilder body = new StringBuilder();
        body.append(makeHierNavigation(item));
        if (item != null) {
            body.append(generatePlaceBlock(item, true));
        } else {
            pageInfo.addPageTitle(i18n.getString("places.countries", pageInfo.getLocale()));
            body.append(src);
            body.append("""
                    <div class="row">""");
            body.append(generateRootCountries());
            body.append("</div>");
        }
        if (item != null) {
            StringBuilder str = new StringBuilder();
            Set<String> checkDoubleSet = new HashSet<>();
            String containsPlaces = generateBlocksForConainsRecursive(item, checkDoubleSet);
            if (!Strings.isNullOrEmpty(containsPlaces)) {
                str.append("""
                        <div class="container">""");
                str.append("<h1> ");
                str.append(i18n.getString("places.includes.regions", pageInfo.getLocale()));
                str.append("</h1>");
                str.append("""
                        <div class="row">""");
                str.append(containsPlaces);
                str.append("</div>");
                str.append("</div>");
            }

            //Граничит с регионами
            String sharedPlacesStr = generateBlocksForSharedBorders(item);
            if (!Strings.isNullOrEmpty(sharedPlacesStr)) {
                str.append("""
                        <div class="container">""");
                str.append("<h1> ");
                str.append(i18n.getString("places.shares.borders.with.regions", pageInfo.getLocale()));
                str.append("</h1>");
                str.append("""
                        <div class="row">""");
                str.append(sharedPlacesStr);
                str.append("</div></div>");
            }
            if (str.length() > 0) {
                body.append("""
                        <section id="blocks">""");
                body.append(str);
                body.append("</section>");
            }
        }
        return createHtmlAll(new SrcPageContent(body.toString()));
    }

    private String generateBlocksForSharedBorders(JGeoWikidata parent) {
        StringBuilder str = new StringBuilder();
        if (parent.getSharesBorderWith() != null) {
            List<String> shs = OUtils.readList(parent.getSharesBorderWith(), String.class);
            if (shs != null && !shs.isEmpty()) {
                List<JGeoWikidata> items = jGeoWikidataRepository.findByJGeoWikidataIdIn(shs);//to do
                if (items.isEmpty()) {
                    return null;
                }
                for (var item : items) {
                    str.append("""
                            <div class="col-md-4 mb-3">"""); //col-md-4 - 3 колонки на больших экранах, col-6 - 2 на маленьких
                    //mb-3 - нужен для margin между строк
                    str.append(generatePlaceBlock(item, false));
                    str.append("</div>");
                }
            }
        }
        return str.toString();
    }

    private String generateBlocksForConainsRecursive(JGeoWikidata parent, Set<String> checkDoubleSet) {
        List<JGeoWikidata> items = jGeoWikidataRepository.findByParent(parent.getJGeoWikidataId());
        StringBuilder str = new StringBuilder();
        for (var item : items) {
            if (checkDoubleSet.contains(item.getJGeoWikidataId())) {
                log.warn("!!!!! Item " + item.getJGeoWikidataId() + " allready in child set");
                continue;
            } else {
                checkDoubleSet.add(item.getJGeoWikidataId());
            }

            str.append("""
                    <div class="col-md-4 mb-3">"""); //col-md-4 - 3 колонки на больших экранах, col-6 - 2 на маленьких
            //mb-3 - нужен для margin между строк
            str.append(generatePlaceBlock(item, false));
            str.append("</div>");
            if (item.getLevel() >= MAX_LEVEL_FOR_SINGLE_PAGE) {
                str.append(generateBlocksForConainsRecursive(item, checkDoubleSet));
            }
        }
        return str.toString();
    }

    //генерация блока для гео страницы
    private String generatePlaceBlock(JGeoWikidata item, boolean singlePage) {
        if (singlePage) {
            geoPageInfo.setItem(item);
            geoPageInfo.setLat(item.getLatitude());
            geoPageInfo.setLon(item.getLongitude());
            geoPageInfo.setPlacename(item.GetLabel(false)); //en
            if (item.getJCountryId() != null) {
                geoPageInfo.setRegion(item.getJCountryId().toUpperCase());
            }
            pageInfo.addPageTitle(item.GetLabel(getRu()));
            pageInfo.addKeywords(item.GetLabel(getRu()));
            pageInfo.addDescription(i18n.getString("places.region", pageInfo.getLocale())
                    + item.GetLabel(getRu()));
        }
        StringBuilder str = new StringBuilder();
        if (singlePage) {
            //ищем банер не менее 1280 (по факту в хранилище загружен максимальный)
            str.append(getImageStringForWikimedia(item.getPageBanner(), IMAGES_P_FOLDER + "/" + item.getJCountryId() + "/page_banners", 1280, null, null, false));
            str.append("<h1>");
            str.append(item.GetFullLabel(getRu()));
            str.append("</h1>");
        }

        if (!singlePage) {
            //str.append(@"<div class=""card mb-4 shadow-sm"">");// w-25
            //h-100 дает одинаковую высоту для всех карточек строки
            str.append("""
                    <div class="card shadow-sm h-100">"""); //mb-4
            Set<String> checkDoubleSet = new HashSet<>();
            checkDoubleSet.add(item.getJGeoWikidataId());
            ImageDrawInfo imageInfo = null;
            //ищем картинку не менее 640 (по факту в хранилище загружен не более 1280)
            if (item.getImage() != null || item.getImageFirstChild() != null) {
                imageInfo = getImageInfoForWikimedia(isAppropriateImage(item.getImage()) ? item.getImage() : item.getImageFirstChild(),
                        IMAGES_P_FOLDER + "/" + item.getJCountryId(), 640, null, null, false);
            }

            //ищем картинку минимальную (по факту в хранилище загружена не более 1280)
            if (imageInfo == null) {
                imageInfo = getImageInfoForWikimedia(item.getDetailMapImage(),
                        IMAGES_P_FOLDER + "/" + item.getJCountryId() + "/detailed_maps", 0, null, null, false);
            }
            //ищем картинку минимальную (по факту в хранилище загружена минимальная)
            if (imageInfo == null) {
                imageInfo = getImageInfoForWikimedia(item.getFlagImage(),
                        IMAGES_P_FOLDER + "/" + item.getJCountryId() + "/flags", 0, null, null, true);
            }
            if (imageInfo != null && (item.getLevel() < MAX_LEVEL_FOR_SINGLE_PAGE)) {
                imageInfo.setHref(HtmlUtils.urlEncodePath(item.GetLocalUrl()));
                imageInfo.setBlank(false);
                imageInfo.setNoindex(isNoindexItem(item));
            }
            str.append(createImage(imageInfo));

            if (imageInfo != null) {
                str.append("<br/>");
                //ищем картинку минимальную (по факту в хранилище загружена не более 800)
                imageInfo = getImageInfoForWikimedia(item.getLocatorMapImage(),
                        IMAGES_P_FOLDER + "/" + item.getJCountryId() + "/locator_maps", 0, null, null, true);
                if (imageInfo != null && (item.getLevel() < MAX_LEVEL_FOR_SINGLE_PAGE)) {
                    imageInfo.setHref(HtmlUtils.urlEncodePath(item.GetLocalUrl()));
                    imageInfo.setNoindex(isNoindexItem(item));
                    imageInfo.setBlank(false);
                }
                str.append(createImage(imageInfo));
            }

            str.append("""
                    <div class="card-body">""");
            str.append("""
                    <h3 class="card-title">""");
            str.append(item.GetLabel(getRu()));
            str.append("""
                    </h3>""");
        } else//single page
        {
            Set<String> checkDoubleSet = new HashSet<>();
            checkDoubleSet.add(item.getJGeoWikidataId());
            //ищем картинку не менее 1280 (по факту в хранилище загружен не более 1280)
            if (item.getImage() != null || item.getImageFirstChild() != null) {
                String imageStr = null;
                ImageDrawInfo imageDrawInfo = getImageInfoForWikimedia(isAppropriateImage(item.getImage()) ? item.getImage() : item.getImageFirstChild(),
                        item.getJCountryId(), 1280, null, null, false);
                if (imageDrawInfo != null) imageStr = createImage(imageDrawInfo);
                if (!Strings.isNullOrEmpty(imageStr)) {
                    str.append(imageStr);
                }
                if (pageInfo != null && !Strings.isNullOrEmpty(imageStr) && imageDrawInfo.getImagePath() != null) {
                    pageInfo.setPageImage(pageInfo.getDomainUrl() + "/" + imageDrawInfo.getImagePath());
                }
            }
            str.append("<div>");
            if (item.getFlagImage() != null) {
                //ищем картинку минимальную (по факту в хранилище загружена минимальная)
                str.append(getImageStringForWikimedia(item.getFlagImage(),
                        IMAGES_P_FOLDER + "/" + item.getJCountryId() + "/flags", 0, null, null, true)); //"float-left  w-25 img-thumbnail", "width: 200px;"
            }
            if (item.getCoatOfArmsImage() != null) {
                //ищем картинку минимальную (по факту в хранилище загружена минимальная)
                //str.append(GetImageStringForWikimedia(item.getCoatOfArmsImage(), item.getJCountryId() + "/coat_of_arms", 0, null, null, curLevel, true, out resultImageUrl));//"float-right w-25 img-thumbnail", "width: 200px;"
                String href = WIKIMEDIA_ADDRESS_FOR_FILE_HREF + HtmlUtils.urlEncodePath(item.getCoatOfArmsImage());
                str.append(createExtLink(href, null, i18n.getString("places.coat.of.arms", pageInfo.getLocale()), i18n.getString("places.go.to.official.wikipedia.site.to.see.coat.of.arms", pageInfo.getLocale())));
            }
            str.append("</div>");

            if (item.getLocatorMapImage() != null) {
                str.append("<div>");
                //ищем картинку минимальную (по факту в хранилище загружена не более 800)
//C# TO JAVA CONVERTER TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'OutObject' helper class unless the method is within the code being modified:
                str.append(getImageStringForWikimedia(item.getLocatorMapImage(),
                        IMAGES_P_FOLDER + "/" + item.getJCountryId() + "/locator_maps", 0, null, null, true)); //"float - center w - 50 img - thumbnail
                str.append("</div>");
            }
            if (item.getDetailMapImage() != null) {
                str.append("<div>");
                //ищем картинку минимальную (по факту в хранилище загружена не более 1280)
//C# TO JAVA CONVERTER TASK: The following method call contained an unresolved 'out' keyword - these cannot be converted using the 'OutObject' helper class unless the method is within the code being modified:
                str.append(getImageStringForWikimedia(item.getDetailMapImage(),
                        IMAGES_P_FOLDER + "/" + item.getJCountryId() + "/detailed_maps", 0, null, null, true)); //"w-50 img-thumbnail"
                str.append("</div>");
            }
            if (item.getLatitude() != null && item.getLongitude() != null) {
                str.append("<div>");
                str.append(makeGoogleIFrame(item.getLatitude(), item.getLongitude()));
                str.append("</div>");
            }

        }
        str.append("""
                <ul class="list-unstyled">"""); // mt-3 mb-4 geo1

        str.append(generateBlockAttributeLine(null, item.getDescription(getRu()))); //Ru ? "Описание" : "Description"
        if (item.getCountry() != null) {

            JGeoWikidata countryItem = jGeoWikidataRepository.findById(item.getCountry()).orElse(null);
            if (countryItem != null) {
                ImageDrawInfo tempVar = new ImageDrawInfo();
                tempVar.setImagePath("images/countries/" + item.getJCountryId() + ".png");
                tempVar.setWidth(-1);
                tempVar.setHeight(-1);
                tempVar.setTitle( countryItem.GetLabel(getRu()));
                tempVar.setHref(HtmlUtils.urlEncodePath(countryItem.GetLocalUrl()));
                tempVar.setBlank(false);
                tempVar.setNoindex(isNoindexItem(countryItem));

                str.append(generateBlockAttributeLine(i18n.getString("places.country", pageInfo.getLocale()), createImage(tempVar)
                ));

            }
        }
        if (item.getCapital() != null) {
            JGeoWikidata capitalItem = jGeoWikidataRepository.findById(item.getCapital()).orElse(null);
            if (capitalItem != null) {
                str.append(generateBlockAttributeLine(i18n.getString("places.capital", pageInfo.getLocale()), "<a href=\"" + HtmlUtils.urlEncodePath(capitalItem.GetLocalUrl())
                    + "\"" +  addNoindexAttrIfNeed(item)
                    + ">" + capitalItem.GetLabel(getRu()) + "</a>"));
            }
        }
        str.append(generateBlockAttributeLine(i18n.getString("places.postal.code", pageInfo.getLocale()), item.getPostalCode()));
        str.append(generateBlockAttributeLine(i18n.getString("places.local.dialing.code", pageInfo.getLocale()), item.getLocalDialingCode()));
        str.append(generateBlockAttributeLine(i18n.getString("places.licence.plate.code", pageInfo.getLocale()), item.getLicencePlateCode()));
        if (item.getLatitude() != null && item.getLongitude() != null) {
            str.append(generateBlockAttributeLine(i18n.getString("places.coordinates", pageInfo.getLocale()), makeAllMapHref(item.getLatitude(), item.getLongitude())));
            str.append(generateBlockAttributeLine(i18n.getString("places.gps.tracks.wikiloc", pageInfo.getLocale()), createExtLink(makeWikilocMapHref(item.getLatitude(), item.getLongitude()), null, (i18n.getString("places.link", pageInfo.getLocale())), i18n.getString("places.go.to.official.wikiloc.site.to.see.gps.ttacks.in.this.region", pageInfo.getLocale()))));
        }
        if (item.getAboveSeaLevel() != null) {
            str.append(generateBlockAttributeLine(i18n.getString("places.abovesealevel", pageInfo.getLocale()), (item.getAboveSeaLevel().startsWith("+") ? item.getAboveSeaLevel().substring(1) : item.getAboveSeaLevel()) + (" м m")));
        }
        if (item.getArea() != null) {
            str.append(generateBlockAttributeLine(i18n.getString("places.area", pageInfo.getLocale()), (item.getArea().startsWith("+") ? item.getArea().substring(1) : item.getArea()) + " " + i18n.getString("places.sq.km", pageInfo.getLocale())));
        }
        if (item.getPopulation() != null) {
            str.append(generateBlockAttributeLine(i18n.getString("places.population", pageInfo.getLocale()), (item.getPopulation().startsWith("+") ? item.getPopulation().substring(1) : item.getPopulation())));
        }

        str.append(generateBlockAttributeLine(i18n.getString("places.web.site", pageInfo.getLocale()), createExtLink(item.getWebSite(), null, singlePage ? item.getWebSite() : (i18n.getString("places.link", pageInfo.getLocale())), i18n.getString("places.official.web.site.link", pageInfo.getLocale()))));
        if (singlePage) {

            if (getRu()) {
                str.append(generateBlockAttributeLine(i18n.getString("places.wikipedia.rus", pageInfo.getLocale()), createExtLink(item.getRuwikiLink(), "https://ru.wikipedia.org/wiki/", "wiki(ru)",
                        i18n.getString("places.wikipedia.rus1", pageInfo.getLocale()))));
            }
            str.append(generateBlockAttributeLine(i18n.getString("places.wikipedia.en", pageInfo.getLocale()), createExtLink(item.getEnwikiLink(), "https://en.wikipedia.org/wiki/", "wiki(en)",
                    i18n.getString("places.wikipedia.english1", pageInfo.getLocale()))));
            if (!item.getLang().equals("en") && !item.getLang().equals("ru")) {
                str.append(generateBlockAttributeLine((i18n.getString("places.wikipedia", pageInfo.getLocale())), createExtLink(item.getEnwikiLink(), "https://" + item.getLang() + ".wikipedia.org/wiki/", "wiki(" + item.getLang() + ")",
                        i18n.getString("places.wikipedia.go", pageInfo.getLocale()))));
            }
            str.append(generateBlockAttributeLine(i18n.getString("places.wikidata.storage", pageInfo.getLocale()), createExtLink(item.getJGeoWikidataId(), "https://www.wikidata.org/wiki/", "Wikidata: " + item.getJGeoWikidataId(),
                    i18n.getString("places.wikidata.wikidata.storage.link", pageInfo.getLocale()))));
            str.append(generateBlockAttributeLine(i18n.getString("places.wikipedia.commons.gallery", pageInfo.getLocale()), createExtLink(item.getCommonsGallery(), "https://commons.wikimedia.org/wiki/", (i18n.getString("places.link", pageInfo.getLocale())),
                    i18n.getString("places.wikipedia.commons.gallery.go", pageInfo.getLocale()))));
            str.append(generateBlockAttributeLine(i18n.getString("places.wikipedia.commons.category", pageInfo.getLocale()), createExtLink(item.getCommonsCategory(), "https://commons.wikimedia.org/wiki/", (i18n.getString("places.link", pageInfo.getLocale())),
                    i18n.getString("places.wikipedia.commons.category.go", pageInfo.getLocale()))));
            str.append(generateBlockAttributeLine(i18n.getString("places.wikipedia.commons.maps.category", pageInfo.getLocale()), createExtLink(item.getCommonsMapsCategory(), "https://commons.wikimedia.org/wiki/", (i18n.getString("places.link", pageInfo.getLocale())),
                    i18n.getString("places.wikipedia.commons.maps.category.go", pageInfo.getLocale()))));
        }
        str.append("</ul>");


        if (singlePage) {

            List<JWikiPropertyValue> exts = null;
            exts = OUtils.readList(item.getExtIds(), JWikiPropertyValue.class);

            if (exts != null && !exts.isEmpty()) {
                str.append("""
                        <button class="btn btn-primary" type="button" data-bs-toggle="collapse" data-bs-target="#footwear" aria-expanded="false" aria-controls="footwear">
                        """);
                str.append(i18n.getString("places.another.links.and.codes", pageInfo.getLocale()));
                str.append("</button>");

                str.append("""
                        <div class="collapse" id="footwear">""");
                str.append("""
                        <ul class="list-unstyled">"""); // mt-3 mb-4 geo1
                ArrayList<JWikiPropertyWithValue> propList = new ArrayList<>();
                ArrayList<JWikiPropertyWithValue> propList1 = new ArrayList<>();
                for (var ext : exts) {
                    if (ext.getProp() == null) {
                        break;
                    }

                    JWikiProperty prop = jWikiPropertyCache.getById(ext.getProp());
                    if (prop != null) {
                        JWikiPropertyWithValue pv = new JWikiPropertyWithValue();
                        pv.setProp(prop);
                        pv.setValue(ext.getValue());
                        if ((pv.getProp().getFormatterURL() != null && !pv.getProp().getResultTestUrl().equals("2") && !pv.getProp().getResultTestUrl().equals("1")) || (pv.getProp().getFormatterURLAlt() != null && !pv.getProp().getResultTestUrlAlt().equals("2") && !pv.getProp().getResultTestUrlAlt().equals("1"))) {
                            propList.add(pv);
                        } else {
                            propList1.add(pv);
                        }
                    }
                }
                //sort by count desc
                Collections.sort(propList, (x, y) -> Integer.compare(y.getProp().getCount(), x.getProp().getCount()));
                Collections.sort(propList1, (x, y) -> Integer.compare(y.getProp().getCount(), x.getProp().getCount()));

                for (var pv : propList) {

                    String valueStr = "";
                    if (pv.getProp().getFormatterURL() != null && !pv.getProp().getResultTestUrl().equals("2") && !pv.getProp().getResultTestUrl().equals("1")) {

                        valueStr = valueStr + createExtLink(JWikiProperty.MakeFormatedUrl(pv.getProp().getFormatterURL(), pv.getValue()), null, "[" + pv.getValue() + "]", i18n.getString("places.go.link", pageInfo.getLocale()));


                    }
                    if (pv.getProp().getFormatterURLAlt() != null && !pv.getProp().getResultTestUrlAlt().equals("2") && !pv.getProp().getResultTestUrlAlt().equals("1")) {
                        if (valueStr.length() != 0) {
                            valueStr = i18n.getString("places.alt", pageInfo.getLocale());
                        }
                        valueStr = valueStr + createExtLink(JWikiProperty.MakeFormatedUrl(pv.getProp().getFormatterURLAlt(), pv.getValue()), null, "[" + pv.getValue() + "]", i18n.getString("places.go.link", pageInfo.getLocale()));

                    }
                    if (valueStr.length() == 0) {
                        valueStr = pv.getValue();
                    }
                    str.append(generateBlockAttributeLine(pv.getProp().GetLabel(getRu()), valueStr));
                }
                for (var pv : propList1) {
                    String valueStr = pv.getValue();
                    str.append(generateBlockAttributeLine(pv.getProp().GetLabel(getRu()), valueStr));
                }
                str.append("</div>");
                str.append("</ul>");
            }

        }

        if (!singlePage && (item.getLevel() < MAX_LEVEL_FOR_SINGLE_PAGE)) {
            str.append("""
                    <a href=\"""");
            str.append(HtmlUtils.urlEncodePath(item.GetLocalUrl()));
            str.append("""
                    " class="btn btn-primary"
                    """);
            str.append(addNoindexAttrIfNeed(item));
            str.append("""
                    "">""");
            str.append(i18n.getString("places.details", pageInfo.getLocale()));
            str.append("""
                    </a>""");
        }
        if (!singlePage) {
            str.append("</div>"); //card-body
            str.append("</div>"); //card
        }
        return str.toString();
    }
    private boolean isNoindexItem (JGeoWikidata item) {
        if (item.getChildrenCount() == 0) return true;
        else return false;
    }
    private String addNoindexAttrIfNeed(JGeoWikidata item){
        if (isNoindexItem(item)) {
            return """
                    rel="noindex"
                    """;
        }
        else return "";

    }

    private String getImageStringForWikimedia(String wikimediaName, String mediaPrefix, int minSize, String addClass, String style, boolean tumb) {
        return createImage(getImageInfoForWikimedia(wikimediaName, mediaPrefix, minSize,
                addClass, style, tumb));
    }

    public ImageDrawInfo getImageInfoForWikimedia(String wikimediaName, String mediaPrefix, int minSize, String addClass, String style, boolean tumb) {
        if (wikimediaName == null) return null;
        String mediaPath = Paths.get(mediaPrefix).resolve(wikimediaName).toString();
        MediaImageInfo info = resourceService.getMediaInfo(pageInfo.getSiteInternalStoragePaths(), mediaPath);
        if (info != null) {
            String uri = getImagePathForTumb(info, minSize, useExternalUrlForImages);
            if (useExternalUrlForImages) {
                if (uri != null && !uri.startsWith("http")) {
                    try {
                        uri = (new URI(WIKIMEDIA_ADDRESS)).resolve(uri).toString();
                    } catch (URISyntaxException e) {
                        throw new DioRuntimeException(e);
                    }
                }
            } else {
                if (mediaPrefix != null) {
                    uri = mediaPrefix + "/" + uri;
                }
            }
            if (uri != null) {
                ImageDrawInfo tempVar = new ImageDrawInfo();
                //tempVar.setSrcLevel(pageInfo.getLevel());//to do
                tempVar.setTitle(getImageCopyrightTitle(info));
                tempVar.setBlank(true);
                tempVar.setHref(WIKIMEDIA_ADDRESS_FOR_FILE_HREF + HtmlUtils.urlEncodePath(wikimediaName));
                tempVar.setImagePath(uri);//to do encode name only?
                tempVar.setAddClass(addClass);
                tempVar.setStyle(style);
                tempVar.setTumb(tumb);
                tempVar.setImgCard(!tumb);
                tempVar.setAlign(tumb ? Align.CENTER : Align.DEFAULT);
                tempVar.setNoExt(true);
                return tempVar;
            }
        }
        return null;
    }

    private String getImagePathForTumb(MediaImageInfo info, int minSize, boolean getExternalUrl) {
        if (info.getAltList() != null && !info.getAltList().isEmpty()) {
            if (getExternalUrl) {
                int num = 0;
                for (var v : info.getAltList()) {
                    if (v.getWidth() >= minSize || num == (info.getAltList().size() - 1)) {
                        return v.getSourceUrl();
                    }
                    num++;
                }
            } else {
                ArrayList<MediaImageAltInfo> loadedAltList = new ArrayList<>();
                for (var v : info.getAltList()) {
                    if (v.getStoredFilePath() != null) {
                        loadedAltList.add(v);
                    }
                }
                int num = 0;
                for (var v : loadedAltList) {
                    if (v.getWidth() >= minSize || num == (loadedAltList.size() - 1)) {
                        return v.getStoredFilePath();
                    }
                    num++;
                }
            }
        }
        return null;
    }

    private String getImageCopyrightTitle(MediaImageInfo info) {
        StringBuilder str = new StringBuilder();
        if (info.getAuthor() != null) {
            str.append(info.getAuthor());
            str.append(" [CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0)], via Wikimedia Commons");
        } else {
            str.append("See page for author[Public domain], via Wikimedia Commons");
        }
        return str.toString();
        //https://commons.wikimedia.org/wiki/
        //Bwag [CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0)]
        //Bwag [<a href="https://creativecommons.org/licenses/by-sa/4.0">CC BY-SA 4.0</a>], <a href="https://commons.wikimedia.org/wiki/File:Heiligenblut_am_Gro%C3%9Fglockner_(1).JPG">via Wikimedia Commons</a>
        //<a title="Bwag [CC BY-SA 4.0 (https://creativecommons.org/licenses/by-sa/4.0)], via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File:Heiligenblut_am_Gro%C3%9Fglockner_(1).JPG"><img width="512" alt="Heiligenblut am Großglockner (1)" src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Heiligenblut_am_Gro%C3%9Fglockner_%281%29.JPG/512px-Heiligenblut_am_Gro%C3%9Fglockner_%281%29.JPG"></a>
        //<a title="See page for author [Public domain], via Wikimedia Commons" href="https://commons.wikimedia.org/wiki/File:Kaernten_CoA.svg"><img width="256" alt="Kaernten CoA" src="https://upload.wikimedia.org/wikipedia/commons/thumb/b/b5/Kaernten_CoA.svg/256px-Kaernten_CoA.svg.png"></a>

    }


    private String formatGeo(String latitude, String longitude) {
        return formatGeo(Double.parseDouble(latitude),
                Double.parseDouble(longitude));
    }

    //https://stackoverflow.com/questions/8851816/convert-decimal-coordinate-into-degrees-minutes-seconds-direction
    private String formatGeo(double latitude, double longitude) {
        int latSeconds = (int) Math.rint(latitude * 3600);
        int latDegrees = latSeconds / 3600;
        latSeconds = Math.abs(latSeconds % 3600);
        int latMinutes = latSeconds / 60;
        latSeconds %= 60;

        int longSeconds = (int) Math.rint(longitude * 3600);
        int longDegrees = longSeconds / 3600;
        longSeconds = Math.abs(longSeconds % 3600);
        int longMinutes = longSeconds / 60;
        longSeconds %= 60;

        return String.format("%1$s° %2$s' %3$s\" %4$s, %5$s° %6$s' %7$s\" %8$s", Math.abs(latDegrees), latMinutes, latSeconds, latDegrees >= 0 ? "N" : "S", Math.abs(longDegrees), longMinutes, longSeconds, latDegrees >= 0 ? "E" : "W");
    }


    //генерация навигации по иерархии гео страниц
    private String makeHierNavigation(JGeoWikidata item) {
        StringBuilder str = new StringBuilder();
        str.append("""
                <ul class="breadcrumb">""");
        ArrayList<JGeoWikidata> items = new ArrayList<>();
        if (item != null) {
            JGeoWikidata curItem = item;
            while (curItem != null) {
                items.add(curItem);
                if (curItem.getParent() != null) {
                    JGeoWikidata p = jGeoWikidataRepository.findById(curItem.getParent()).orElse(null);
                    if (p != null) {
                        curItem.setParent(p.getJGeoWikidataId());
                    }
                    curItem = p;
                } else {
                    break;
                }
            }
            Collections.reverse(items);
        }


        str.append("""
                <li class="breadcrumb-item">""");
        if (item != null) {
            str.append("""
                    <a href=\"""");
            str.append(COUNTRIES_LIST_FOLDER);
            str.append("""
                    ">""");
            str.append((i18n.getString("places.countries", pageInfo.getLocale())));
            str.append("</a>");
        } else {
            str.append((i18n.getString("places.countries", pageInfo.getLocale())));
        }
        str.append("</li>");

        for (var curItem1 : items) {
            if (curItem1 != null) {
                str.append("""
                        <li class="breadcrumb-item">""");
                if (item != curItem1) {
                    str.append("""
                            <a href=\"""");
                    str.append(HtmlUtils.urlEncodePath(curItem1.GetLocalUrl()));
                    str.append("\"");
                    str.append(addNoindexAttrIfNeed(curItem1));
                    str.append(">");
                    str.append(curItem1.GetLabel(getRu()));
                    str.append("</a>");
                } else {
                    str.append(curItem1.GetLabel(getRu()));
                }
                str.append("</li>");
            }
        }
        str.append("</ul>");
        return str.toString();
    }

    private String makeAllMapHref(String lat, String lon) {
        return formatGeo(lat, lon) +
                " " +
                createImage("images/gmap.png", 16, 16, i18n.getString("places.google.open.google.maps", pageInfo.getLocale()), makeGoogleMapHref(lat, lon), true) +
                " " +
                createImage("images/osm.png", 16, 16, i18n.getString("places.open.open.street.map", pageInfo.getLocale()), makeOSMMapHref(lat, lon), true) +
                " " +
                createImage("images/wikimapia.png", 16, 16, i18n.getString("places.map.open.wikimapia.map", pageInfo.getLocale()), makeWikimapiaMapHref(lat, lon), true);
    }

    private String makeGoogleMapHref(String lat, String lon) {
        return "https://maps.google.com/maps?ll=" + lat + "," + lon + "&q=" + lat + "," + lon + "&hl=" + pageInfo.getLocaleLang() + "&t=m&z=11";
    }

    private String makeGoogleIFrame(String lat, String lon) {

        return """
                <!-- Google map -->""" +
                """
                        <div id="map-container-google-1" class="z-depth-1-half map-container" >""" + //style=""height: 500px""
                """
                        <iframe src=\"""" +
                "https://maps.google.com/maps?ll=" + lat + "," + lon + "&q=" + lat + "," + lon + "&hl=" + pageInfo.getLocaleLang() + "&t=m&z=11&iwloc=&output=embed" +
                """
                        " frameborder="0\"""" +
                """
                         style="border:0" allowfullscreen>
                        """.stripTrailing() +
                """
                        </iframe>""" +
                """
                        </div>""";
    }


    private String makeOSMMapHref(String lat, String lon) {
        return "https://www.openstreetmap.org/?mlat=" + lat + "&mlon=" + lon + "&zoom=11&layers=M";
    }

    private String makeWikimapiaMapHref(String lat, String lon) {
        return "http://wikimapia.org/#lang=" + pageInfo.getLocaleLang() + "&lat=" + lat + "&lon=" + lon + "&z=11&m=w";
    }

    private String makeWikilocMapHref(String lat, String lon) {
        return "https://" + pageInfo.getLocaleLang() + ".wikiloc.com/wikiloc/map.do?lt=" + lat + "&ln=" + lon + "&z=10"; //&k=1

        //http://es.wikiloc.com/wikiloc/spatialArtifacts.do?event=view&id=981146&measures=off&title=off&near=off&images=off&maptype=S

			/*
			 * Примеры поисковых запросов
			    https://ru.wikiloc.com/wikiloc/find.do?t=&d=&lfr=&lto=&src=&act=1%2C&months=&q=Carinthia (поход)
			    https://ru.wikiloc.com/wikiloc/find.do?t=&d=&lfr=&lto=&src=&act=1%2C21%2C14%2C43%2C24%2C46%2C28%2C65%2C&months=&q=Carinthia (пешие активности и альпинизм)

			 */

    }

}

