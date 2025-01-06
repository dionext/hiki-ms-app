package com.dionext.hiki.services;

import com.dionext.hiki.db.entity.JMedia;
import com.dionext.hiki.db.repositories.JMediaRepository;
import com.dionext.site.dto.ImageDrawInfo;
import com.dionext.site.dto.SrcPageContent;
import com.dionext.utils.HtmlUtils;
import com.dionext.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings({"java:S6541", "java:S5663", "java:S1192", "java:S3776"})
public class MediaGalleryCreatorService extends HikingLandPageCreatorService {

    public static final String TM_DB_MOVIE_BASE_URL = "https://www.themoviedb.org/movie/";
    public static final String POSTER_IMAGE_W_FOR_CARD = "w342";
    public static final String POSTER_IMAGE_W_FOR_SINGLE_PAGE = "w780";
    public static final String BACKDROP_IMAGE_W_FOR_CARD = "w300";
    public static final String BACKDROP_IMAGE_W_FOR_SINGLE_PAGE = "w780";
    public static final String IMAGES_FOLDER = "images";
    public static final String TM_DB_FOLDER = "tmdb";
    public static final String TM_DB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    JMediaRepository jMediaRepository;
    @Value("${web-config.multiSites.hiking.mediaGallery.useExternalUrlForImages}")
    private boolean useExternalUrlForImages;

    @Autowired
    public void setjMediaRepository(JMediaRepository jMediaRepository) {
        this.jMediaRepository = jMediaRepository;
    }


    public String createMediaGalleryPage(SrcPageContent srcPageContent) {
        String src = srcPageContent.getBody();
        String mediaName = pageInfo.getRelativePath();
        int dotIndex = mediaName.lastIndexOf(".htm");
        if (dotIndex > -1) {
            mediaName = mediaName.substring(0, dotIndex);
        }
        int slashIndex = mediaName.indexOf("/");
        if (slashIndex > -1) {
            mediaName = mediaName.substring(slashIndex + 1);
        } else mediaName = "list";

        StringBuilder body = new StringBuilder();
        JMedia item = null;
        if (!"list".equals(mediaName)) {
            mediaName = HtmlUtils.urlDecode(mediaName);
            item = Utils.firstOrNull(jMediaRepository.findBySafeName(mediaName));
        }

        if (item != null) {
            //movie page
            body.append(generateMediaBlock(item, true));
        } else {
            pageInfo.addPageTitle(i18n.getString("media.list.of.movies.about.tourism", pageInfo.getLocale()));
            List<JMedia> list = jMediaRepository.findAll();

            if (!list.isEmpty()) {

                body.append("""
                        <div class="container">""");
                body.append(src);
                body.append("""
                        <div class="row">""");
                for (var item1 : list) {
                    body.append("""
                            <div class="col-md-3 mb-3">"""); //col-md-3 - 4 columns ,    col-md-4 - 3 columns on big screens, col-6 - 2 columns on small screens
                    //mb-3 - for margin
                    body.append(generateMediaBlock(item1, false));
                    body.append("</div>");
                }
                body.append("</div>");
                body.append("</div>");
            }

            if (body.length() > 0) {
                body.append("""
                        <section id="blocks">""");
                body.append(body);
                body.append("</section>");
            }
        }

        return createHtmlAll(new SrcPageContent(body.toString()));

    }

    private String generateMediaBlock(JMedia item, boolean singlePage) {
        boolean ru = getRu();
        StringBuilder str = new StringBuilder();

        String posterImageStr = null;
        if (item.getPosterPath() != null)
            posterImageStr = getImageUrlForMedia(item.getPosterPath(), singlePage ? POSTER_IMAGE_W_FOR_SINGLE_PAGE : POSTER_IMAGE_W_FOR_CARD);
        String backdropImageStr = null;
        if (item.getBackdropPath() != null)
            backdropImageStr = getImageUrlForMedia(item.getBackdropPath(), singlePage ? BACKDROP_IMAGE_W_FOR_SINGLE_PAGE : BACKDROP_IMAGE_W_FOR_CARD);
        if (singlePage) {
            if (pageInfo != null) {
                pageInfo.addPageTitle(item.GetName(ru));
                pageInfo.addKeywords(item.GetName(ru));
                pageInfo.addDescription(i18n.getString("media.movie", pageInfo.getLocale()) + item.GetName(ru) + "." + (pageInfo.getDescription() != null ? " " + pageInfo.getDescription() : ""));
                pageInfo.setPageImage(posterImageStr);
            }
            str.append("<h1>");
            str.append(item.GetName(ru));
            str.append("</h1>");

            //to do

            ImageDrawInfo tempVar = new ImageDrawInfo();
            tempVar.setImgCard(true);
            tempVar.setImagePath(posterImageStr);
            str.append(createImage(tempVar));
        } else {
            //h-100 gives the same height for all cards in a row
            str.append("""
                    <div class="card mb-4 shadow-sm h-100">""");
            ImageDrawInfo tempVar2 = new ImageDrawInfo();
            tempVar2.setImagePath(posterImageStr);
            tempVar2.setImgCard(true);
            tempVar2.setTitle(i18n.getString("media.more.details.about.the.movie", pageInfo.getLocale()));
            tempVar2.setHref(HtmlUtils.urlEncodePath(item.GetLocalUrl()));
            str.append(createImage(tempVar2));

            str.append("""
                    <div class="card-body">""");
            str.append("""
                    <h3 class="card-title">""");
            str.append(item.GetName(ru));
            str.append("""
                    </h3>""");
        }


        str.append("""
                <ul class="list-unstyled">"""); // mt-3 mb-4 geo1
        if (!singlePage) {
            str.append(generateBlockAttributeLine(i18n.getString("media.tagline", pageInfo.getLocale()), item.GetTagline(ru)));
        }
        str.append(generateBlockAttributeLine(i18n.getString("media.genres", pageInfo.getLocale()), item.GetGenres(ru)));
        if (!singlePage) {
            str.append(generateBlockAttributeLine(i18n.getString("media.director", pageInfo.getLocale()), item.GetDirector(ru)));
        }
        String cast = item.GetCast(ru);

        if (cast != null) {
            StringBuilder castRestict = new StringBuilder();
            if (singlePage) {
                castRestict.append(cast);
            } else {
                String[] castArray = cast.split(",", -1);
                if (castArray.length > 0) {
                    for (int i = 0; (i < castArray.length) && (i < 4); i++) {
                        if (castRestict.length() > 0) {
                            castRestict.append(", ");
                        }
                        castRestict.append(castArray[i]);
                    }
                    if (castRestict.length() > 0) {
                        castRestict.append(", ...");
                    }
                }
            }
            str.append(generateBlockAttributeLine(i18n.getString("media.cast", pageInfo.getLocale()), castRestict.toString()));
        }
        str.append("</ul>");

        if (item.GetDescription(ru) != null) {
            str.append("""
                    <p>""");
            str.append(item.GetDescription(ru));
            str.append("""
                    </p>""");
        }
        String o = item.GetOverview(ru);
        if (o != null) {
            str.append("""
                    <p>""");
            str.append((singlePage || o.length() <= 200) ? o : (o.substring(0, 100) + "..."));
            str.append("""
                    </p>""");
        }
        if (singlePage) {
            if (item.getTMDbId() != null) {
                str.append(generateBlockAttributeLine(i18n.getString("media.tmdb.movies.page.in.tmdb", pageInfo.getLocale()), createExtLink(item.getTMDbId(), TM_DB_MOVIE_BASE_URL, (ru ? "[Ссылка]" : "[Link]"), ru ? "Переход на  сайт" : "Web Site link")));
            }
            if (item.getImdbId() != null) {
                str.append(generateBlockAttributeLine(i18n.getString("media.imdb.movies.page.in.imdb", pageInfo.getLocale()), createExtLink(item.getImdbId(), "https://www.imdb.com/title/", (ru ? "[Ссылка]" : "[Link]"), ru ? "Переход на  сайт" : "Web Site link")));
            }

            if (backdropImageStr != null) {
                str.append("""
                        <p>""");

                ImageDrawInfo tempVar = new ImageDrawInfo();
                tempVar.setImagePath(backdropImageStr);
                tempVar.setImgCard(true);
                str.append(createImage(tempVar));

                str.append("""
                        </p>""");
            }
        }
        //logo
        //408*161
        str.append("""
                <p>""");
        ImageDrawInfo tempVar2 = new ImageDrawInfo();
        tempVar2.setImagePath("images/TMDb_Logo.png");
        tempVar2.setWidth(51);
        tempVar2.setTitle("This product uses the TMDb API but is not endorsed or certified by TMDb.");
        tempVar2.setHref(TM_DB_MOVIE_BASE_URL + item.getTMDbId());
        tempVar2.setBlank(true);
        str.append(createImage(tempVar2));

        if (!singlePage) {
            str.append("""
                    </p>""");
            str.append("""
                    <a href=\"""");
            str.append(HtmlUtils.urlEncodePath(item.GetLocalUrl()));
            str.append("""
                    " class="btn btn-primary">""");
            str.append(i18n.getString("media.details", pageInfo.getLocale()));
            str.append("""
                    </a>""");

        }
        if (!singlePage) {
            str.append("</div>"); //card-body
            str.append("</div>"); //card
        }

        return str.toString();
    }

    public final String getImageUrlForMedia(String imagePath, String resolutionPrefix) {
        if (useExternalUrlForImages) {
            return getImageExtUrlForMedia(imagePath, resolutionPrefix);
        } else {
            return getImageInternamUrlForMedia(imagePath, resolutionPrefix);
        }
    }

    public final String getImageInternamUrlForMedia(String imagePath, String resolutionPrefix) {
        return IMAGES_FOLDER + "/" + TM_DB_FOLDER + "/" + resolutionPrefix + "/" + imagePath;
    }

    public final String getImageExtUrlForMedia(String imagePath, String resolutionPrefix) {
        //to do  mediainfo
        return TM_DB_IMAGE_BASE_URL + resolutionPrefix + "/" + imagePath;
    }


}
