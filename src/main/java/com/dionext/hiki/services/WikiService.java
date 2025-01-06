package com.dionext.hiki.services;

import com.dionext.hiki.db.entity.JCountry;
import com.dionext.hiki.db.entity.JGeoWikidata;
import com.dionext.hiki.db.repositories.JCountryRepository;
import com.dionext.hiki.db.repositories.JGeoWikidataRepository;
import com.dionext.hiki.dto.ClaimItem;
import com.dionext.hiki.dto.JWikiPropertyValue;
import com.dionext.hiki.dto.WikiQidSearchResult;
import com.dionext.hiki.dto.WikiQidSearchResultItem;
import com.dionext.utils.OUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import com.dionext.wikibase.client.model.AddItemRequestAllOfItemStatementsValueInner;
import com.dionext.wikibase.client.model.AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner;
import com.dionext.wikibase.client.model.AddItemRequestAllOfItemStatementsValueInnerAllOfValue;
import com.dionext.wikibase.client.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

@Slf4j
@Service
public class WikiService {

    @Autowired
    WebClient wikiRestWebClient;
    @Autowired
    WebClient wikiApiWebClient;
    @Autowired
    JGeoWikidataRepository jGeoWikidataRepository;
    @Autowired
    JCountryRepository jCountryRepository;

    public JGeoWikidata loadWikiFromDb(String qid) {
        return jGeoWikidataRepository.findById(qid).get();
    }
    public String searchQid(String title){
        //https://www.wikidata.org/w/api.php?action=help&modules=wbgetentities
        WikiQidSearchResult item = wikiApiWebClient
                .get()
                .uri("?action=wbgetentities&format=json&sites=ruwiki|enwiki&props=info&titles=" + title)
                .retrieve()
                .bodyToMono(WikiQidSearchResult.class)
                .block();
        if (item != null && item.entities != null){
            for (Map.Entry<String, WikiQidSearchResultItem> e : item.entities.entrySet()) {
                if (e.getKey().startsWith("Q")) return e.getKey();
            }
        }
        return null;
    }

    //todo
    public String searchQids(String title){
        //help https://www.wikidata.org/w/api.php?action=help&modules=wbsearchentities

        //example https://www.wikidata.org/w/api.php?action=wbsearchentities&format=json&search=Berlin&sites=enwiki&language=en
        return null;
    }

    public JGeoWikidata loadWiki(String qid) {
        //https://www.wikidata.org/wiki/Wikidata:REST_API
        Item item = wikiRestWebClient
                .get()
                .uri(String.join("", "entities/items/", qid))
                .retrieve()
                .bodyToMono(Item.class)
                .block();

        JGeoWikidata jGeoWikidata = new JGeoWikidata();
        jGeoWikidata.setJGeoWikidataId(qid);


        parseStatements(item, jGeoWikidata);

        String lang = "en";
        Optional<JCountry> jCountry = jCountryRepository.findByWikidataId(qid);
        //if (!jCountry.isEmpty()) {
          //  if (jGeoWikidata.getJCountryId() != null){
            //    jCountry = jCountryRepository.findByWikidataId(jGeoWikidata.getJCountryId());
            //}
        //}
        if (!jCountry.isEmpty()) {
            String langs = jCountry.get().getLanguages();
            if (langs != null){
                String[] langsArray = langs.split(",");
                if (langsArray.length > 0){
                    lang = langsArray[0];
                    int slashIndex = lang.indexOf("-");
                    if (slashIndex > -1)  lang = lang.substring(0, slashIndex);
                }
            }
        }
        jGeoWikidata.setLang(lang);

        //label
        parseLabel(item, jGeoWikidata, lang);

        //todo
        if (jGeoWikidata.getLabelEn() != null) jGeoWikidata.setName(jGeoWikidata.getLabelEn());
        else if (jGeoWikidata.getLabelNative() != null) jGeoWikidata.setName(jGeoWikidata.getLabelNative());
        //description
        parseDescription(item, jGeoWikidata, lang);

        parseSiteLinks(item, jGeoWikidata, lang);

        log.info(jGeoWikidata.toString());

        //imageFirstChild
        //isAlps
        //isCAlps
        //isML
        //level
        //parent
            //childrenCount
            //jcountryId

        return jGeoWikidata;
    }

    private void parseStatements(Item item, JGeoWikidata jGeoWikidata) {
        List<JWikiPropertyValue> extIds = new ArrayList<JWikiPropertyValue>();

        for (Map.Entry<String, List<AddItemRequestAllOfItemStatementsValueInner>> statement : item.getStatements().entrySet()) {
            String propertyId = statement.getKey();//.GetAttributeValue("id", null);
            boolean isExternalId = false;

            ArrayList<ClaimItem> claimList = new ArrayList<ClaimItem>();
            for (AddItemRequestAllOfItemStatementsValueInner claim : statement.getValue()) {
                AddItemRequestAllOfItemStatementsValueInner.RankEnum rank = claim.getRank();
                //normal
                //preferred
                //deprecated
                if (AddItemRequestAllOfItemStatementsValueInner.RankEnum.DEPRECATED.equals(rank)) {
                    continue;
                }
                String dataType = claim.getProperty().getDataType();// datavalue.GetAttributeValue("type", null);
                if ("external-id".equals(dataType)) {
                    isExternalId = true;
                }

                ClaimItem claimItem = new ClaimItem();
                claimItem.setType(dataType);
                if (AddItemRequestAllOfItemStatementsValueInner.RankEnum.PREFERRED.equals(rank)) {
                    claimItem.setPrefered(true);
                }

                List<AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner> qualifiers = claim.getQualifiers();
                if (qualifiers != null) {
                    boolean replacedFlag = false;
                    for (AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner qualifier : qualifiers) {
                        String qualifierPropId = qualifier.getProperty().getId();//.GetAttributeValue("id", null);
                        String qualifierPropDataType = qualifier.getProperty().getDataType();
                        if ("P1366".equals(qualifierPropId)) {
                            if ("P150".equals(propertyId) || "P47".equals(propertyId)) { //территории, замененные после административной реформы
                                //claimItem.Replaced = true;
                                replacedFlag = true;
                                //break;
                            }
                        } else if ("P610".equals(propertyId) && "P2044".equals(qualifierPropId)) //highest point
                        {
                            claimItem.setValue1(extractContentValue(qualifier.getValue(), "amount"));
                            claimItem.setQualifierPropId(qualifierPropId);
                        } else if ("P1589".equals(propertyId) && "P2044".equals(qualifierPropId)) //lowest point
                        {
                            claimItem.setValue(extractContentValue(qualifier.getValue(), "amount"));
                            claimItem.setQualifierPropId(qualifierPropId);
                        }
                        if ("time".equals(qualifierPropDataType)) {
                            String timeStr = extractContentValue(qualifier.getValue(), "time");
                            if (timeStr != null) {
                                LocalDateTime time = parseDateTime(timeStr);
                                if ("P580".equals(qualifierPropId)) {
                                    claimItem.setStartTime(time);
                                } else if ("P582".equals(qualifierPropId)) {
                                    claimItem.setEndTime(time);
                                } else if ("P585".equals(qualifierPropId)) {
                                    claimItem.setMiddleTime(time);
                                }
                            }
                        }
                    }
                    if (replacedFlag) {
                        continue;
                    }
                }//end qali
                if ("monolingualtext".equals(dataType)) {
                    /*
                    String language = extractContentValue(claim.getValue(), "language");
                    String text = extractContentValue(claim.getValue(), "text");
                    if ("en".equals(language)) {
                        // = value;
                    } else if ("ru".equals(language)) {
                        // = value;
                    } else if (lang != null && lang.equals(language)) {
                        // = value;
                    }
                     */
                } else if ("string".equals(dataType)
                        || "wikibase-item".equals(dataType)
                        || "commonsMedia".equals(dataType)
                        || "external-id".equals(dataType)
                        || "url".equals(dataType)
                ) {
                    if ( claim.getValue() != null && claim.getValue().getContent() != null)
                        claimItem.setValue(claim.getValue().getContent().toString());
                } else if ("globe-coordinate".equals(dataType)) {
                    String latitude = extractContentValue(claim.getValue(), "latitude");
                    String longitude = extractContentValue(claim.getValue(), "longitude");
                    claimItem.setValue(latitude);
                    claimItem.setValue1(longitude);
                } else if ("quantity".equals(dataType)) {
                    String amount = extractContentValue(claim.getValue(), "amount");
                    claimItem.setValue(amount);
                }
                claimList.add(claimItem);
            } //claim foreach

            StringBuilder allValuesStr = new StringBuilder();
            String firstValue = null;
            String firstValue1 = null;
            String qualifierPropId = null;
            ArrayList<String> allValuesList = new ArrayList<String>();
            if (claimList.size() > 0) {
                ArrayList<ClaimItem> newList = SortClaims(claimList);
                firstValue = newList.get(0).getValue();
                firstValue1 = newList.get(0).getValue1();
                qualifierPropId = newList.get(0).getQualifierPropId();

                for (var v : newList) {
                    if (allValuesStr.length() > 0) {
                        allValuesStr.append("; ");
                    }
                    allValuesStr.append(v.getValue());
                    allValuesList.add(v.getValue());

                }
                ////////////////////////////////////////
                //properties
                if ("P18".equals(propertyId)) {
                    jGeoWikidata.setImage(firstValue); //https://commons.wikimedia.org/wiki/File:Heiligenblut_am_Gro%C3%9Fglockner_(1).JPG
                } else if ("P856".equals(propertyId)) //site
                {
                    jGeoWikidata.setWebSite(firstValue);
                } else if ("P242".equals(propertyId)) //https://commons.wikimedia.org/wiki/File:K%C3%A4rnten_in_Austria.svg 1,256 × 700; 318 KB
                {
                    jGeoWikidata.setLocatorMapImage(firstValue);
                } else if ("P1943".equals(propertyId)) //https://commons.wikimedia.org/wiki/File:Karte_oesterreich_kaernten.png 299 × 154; 14 KB
                {
                    jGeoWikidata.setLocationMap(firstValue);
                } else if ("P41".equals(propertyId)) {
                    jGeoWikidata.setFlagImage(firstValue);
                } else if ("P94".equals(propertyId)) {
                    jGeoWikidata.setCoatOfArmsImage(firstValue);
                } else if ("P948".equals(propertyId)) {
                    jGeoWikidata.setPageBanner(firstValue);
                }
                //else if ("P402".Equals(id))
                //   OSMrelationID = lastValue;
                else if ("P1621".equals(propertyId)) {
                    jGeoWikidata.setDetailMapImage(firstValue);
                } else if ("P935".equals(propertyId)) //https://commons.wikimedia.org/wiki/K%C3%A4rnten%20-%20Kor%C3%B3%C5%A1ka
                {
                    jGeoWikidata.setCommonsGallery(firstValue);
                } else if ("P373".equals(propertyId)) //https://commons.wikimedia.org/wiki/Category:Carinthia
                {
                    jGeoWikidata.setCommonsCategory(firstValue);
                } else if ("P3722".equals(propertyId)) //https://commons.wikimedia.org/wiki/Category:Maps%20of%20Carinthia
                {
                    //нет в новом
                    jGeoWikidata.setCommonsMapsCategory(firstValue);
                }
                //else if ("P646".Equals(id))//https://tools.wmflabs.org/freebase/google/m/01gpy4
                // FreebaseID = lastValue;
                //else if ("P1566".Equals(id))//https://www.geonames.org/2774686
                //GeoNamesID = lastValue;
                else if ("P473".equals(propertyId)) {
                    jGeoWikidata.setLocalDialingCode(allValuesStr.toString());
                } else if ("P395".equals(propertyId)) {
                    jGeoWikidata.setLicencePlateCode(allValuesStr.toString());
                } else if ("P281".equals(propertyId)) {
                    jGeoWikidata.setPostalCode(allValuesStr.toString());
                }
                //else if ("P1997".Equals(id))
                //FacebookPlacesID = lastValue;
                //else if ("P1417".Equals(id))
                //BritannicaID = lastValue;
                //else if ("P998".Equals(id))
                //CurlieID = lastValue;
                else if ("P610".equals(propertyId)){
                    jGeoWikidata.setHighestPointPlace(firstValue);
                    if ( "P2044".equals(qualifierPropId)) //elevation above sea level http://www.wikidata.org/entity/Q11573 metre
                    {
                        jGeoWikidata.setAboveSeaLevel(firstValue1);
                    }
                } else if ("P1589".equals(propertyId)) //elevation above sea level http://www.wikidata.org/entity/Q11573 metre
                {
                    jGeoWikidata.setLowestPointPlace(firstValue);
                } else if ("P2046".equals(propertyId)) //+area square kilometre
                {
                    jGeoWikidata.setArea(firstValue);
                } else if ("P1082".equals(propertyId)) //Population чел
                {
                    jGeoWikidata.setPopulation(firstValue);
                } else if ("P17".equals(propertyId)) //country
                {
                    jGeoWikidata.setCountry(firstValue); //allValuesDatedStr.ToString();//
                } else if ("P36".equals(propertyId)) //capital
                {
                    jGeoWikidata.setCapital(firstValue);
                } else if ("P150".equals(propertyId)) //Contains
                {
                    //add to array
                    jGeoWikidata.setContains(OUtils.saveJsonToString(allValuesList));
                    jGeoWikidata.setChildrenCount(claimList.size());
                } else if ("P47".equals(propertyId)) //shares border with
                {
                    //add to array
                    jGeoWikidata.setSharesBorderWith(OUtils.saveJsonToString(allValuesList));
                } else if ("P2872".equals(propertyId)) {
                    jGeoWikidata.setTouristOffice(firstValue);
                } else if ("P625".equals(propertyId)) {
                    jGeoWikidata.setLatitude(firstValue);
                    jGeoWikidata.setLongitude(firstValue1);
                } else if ("P37".equals(propertyId)) {
                    jGeoWikidata.setOfficialLangs(OUtils.saveJsonToString(allValuesList));
                } else if ("P297".equals(propertyId)) {
                    //возможно, есть только на уровне страны
                    if (firstValue != null)
                        jGeoWikidata.setJCountryId(firstValue.toLowerCase());
                } else if ("P131".equals(propertyId)) {
                    jGeoWikidata.setParent(firstValue);
                }

                if (isExternalId) {
                    //List<JWikiPropertyValue> extIds = null;
                    //if (jGeoWikidata.getExtIds() == null) {
                      //  extIds = new ArrayList<JWikiPropertyValue>();
                    //} else {
                      //  extIds = OUtils.readList(jGeoWikidata.getExtIds(), JWikiPropertyValue.class);
                    //}

                    JWikiPropertyValue pv = new JWikiPropertyValue();
                    pv.setProp(propertyId);
                    pv.setValue(firstValue);
                    extIds.add(pv);

                    //jGeoWikidata.setExtIds(OUtils.saveJsonToString(extIds));
                }
            }
        }
        if (extIds.size() > 0)
            jGeoWikidata.setExtIds(OUtils.saveJsonToString(extIds));
        else
            jGeoWikidata.setExtIds(null);

    }

    private static void parseSiteLinks(Item item, JGeoWikidata jGeoWikidata, String lang) {
        for (var e : item.getSitelinks().entrySet()) {
            String siteStr = e.getKey();
            String title = e.getValue().getTitle();
            if ("ruwiki".equals(siteStr) && !Strings.isNullOrEmpty(title)) {
                jGeoWikidata.setRuwikiLink(title);
            } else if ("enwiki".equals(siteStr) && !Strings.isNullOrEmpty(title)) {
                jGeoWikidata.setEnwikiLink(title);
            } else if (lang != null && (lang + "wiki").equals(siteStr) && !Strings.isNullOrEmpty(title)) {
                jGeoWikidata.setNativewikiLink(title);
            } else if ("enwikivoyage".equals(siteStr) && !Strings.isNullOrEmpty(title)) {
                jGeoWikidata.setEnWikivoyage(title);
            } else if ("ruwikivoyage".equals(siteStr) && !Strings.isNullOrEmpty(title)) {
                jGeoWikidata.setRuWikivoyage(title);
            } else if (lang != null && (lang + "wikivoyage").equals(siteStr) && !Strings.isNullOrEmpty(title)) {
                jGeoWikidata.setNativeWikivoyage(title);
            }

        }
    }

    private static void parseDescription(Item item, JGeoWikidata jGeoWikidata, String lang) {
        for (var e : item.getDescriptions().entrySet()) {
            if ("en".equals(e.getKey()))
                jGeoWikidata.setDescriptionEn(e.getValue());
            else if ("ru".equals(e.getKey()))
                jGeoWikidata.setDescriptionRu(e.getValue());
            //native
            if (lang.equals(e.getKey())) {
                jGeoWikidata.setDescriptionNative(e.getValue());
            }
        }
    }

    private static void parseLabel(Item item, JGeoWikidata jGeoWikidata, String lang) {
        for (var e : item.getLabels().entrySet()) {
            if ("en".equals(e.getKey()))
                jGeoWikidata.setLabelEn(e.getValue());
            else if ("ru".equals(e.getKey()))
                jGeoWikidata.setLabelRu(e.getValue());

            //native
            if (lang.equals(e.getKey())) {
                jGeoWikidata.setLabelNative(e.getValue());
            }
        }
    }

    @NotNull
    private static LocalDateTime parseDateTime(String timeStr) {
        DateTimeFormatter formatter =
                new DateTimeFormatterBuilder().appendPattern("yyyy[-MM][-dd][['T'][HH]:[mm]:[ss]['Z']]")
                        .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                        .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();
        LocalDateTime time = LocalDateTime.MIN;
        try {
            //+2016-01-01T00:00:00Z
            //+2016-00-00T00:00:00Z - invalid for statndard parsing
            if (timeStr.startsWith("+")) {
                timeStr = timeStr.substring(1);
            }
            if (timeStr.contains("-00-00")) {
                timeStr = timeStr.substring(0, 4);
                time = LocalDateTime.parse(timeStr, formatter);;
            } else {
                try {
                    time = LocalDateTime.parse(timeStr, formatter);
                } catch (RuntimeException e) {
                    try {
                        timeStr = timeStr.substring(0, 7);
                        time = LocalDateTime.parse(timeStr, formatter);
                    } catch (RuntimeException e2) {
                        timeStr = timeStr.substring(0, 4);
                        time = LocalDateTime.parse(timeStr, formatter);
                    }
                }
            }
        } catch (RuntimeException ex) {
            System.out.println("Error parsing date:  " + timeStr);
        }
        return time;
    }

    private ArrayList<ClaimItem> SortClaimsByTime(ArrayList<ClaimItem> claimList) {
        ArrayList<ClaimItem> newList = new ArrayList<ClaimItem>();
        ArrayList<ClaimItem> tmpList = new ArrayList<ClaimItem>();
        //вверху должны быть с незакрытой датой
        for (ClaimItem v : claimList) {
            if (LocalDateTime.MIN.equals(v.getEndTime())) {
                tmpList.add(v);
            }
        }
        Collections.sort(tmpList, (x, y) -> x.getControlTime().compareTo(y.getControlTime()));
        Collections.reverse(tmpList);
        newList.addAll(tmpList);

        //и только после них с закрытой
        tmpList = new ArrayList<ClaimItem>();
        for (var v : claimList) {
            if (!LocalDateTime.MIN.equals(v.getEndTime())) {
                tmpList.add(v);
            }
        }
        Collections.sort(tmpList, (x, y) -> x.getControlTime().compareTo(y.getControlTime()));
        Collections.reverse(tmpList);
        newList.addAll(tmpList);

        return newList;
    }

    private ArrayList<ClaimItem> SortClaims(ArrayList<ClaimItem> claimList) {
        ArrayList<ClaimItem> newList = new ArrayList<ClaimItem>();

        boolean timed = false;
        for (var v : claimList) {
            if (!LocalDateTime.MIN.equals(v.getMiddleTime()) || !LocalDateTime.MIN.equals(v.getStartTime()) || !LocalDateTime.MIN.equals(v.getEndTime())) {
                timed = true;
                break;
            }
        }

        ArrayList<ClaimItem> preferedList = new ArrayList<ClaimItem>();
        ArrayList<ClaimItem> normalList = new ArrayList<ClaimItem>();
        for (var v : claimList) {
            if (v.isPrefered()) {
                preferedList.add(v);
            } else {
                normalList.add(v);
            }
        }
        if (timed) {
            preferedList = SortClaimsByTime(preferedList);
            normalList = SortClaimsByTime(normalList);
        } else {
            Collections.reverse(preferedList);
            Collections.reverse(normalList);
        }
        //первыми должны идти предпочитаемые
        newList.addAll(preferedList);
        newList.addAll(normalList);
        return newList;
    }
    private static String extractContentValue(AddItemRequestAllOfItemStatementsValueInnerAllOfValue value,
                                              String name) {
        if (!AddItemRequestAllOfItemStatementsValueInnerAllOfValue.TypeEnum.NOVALUE.equals(value.getType())){
            return extractContent(value.getContent(), name);
        }
        else return null;
    }

    private static String extractContent(Object content,
                                         String name) {

        if (content == null) throw new RuntimeException("Content of value is null");
        if (content instanceof Map<?,?> contentMap){
            if (contentMap.get(name) != null)
                return contentMap.get(name).toString();
            else throw new RuntimeException("No property with name: " + name);
        }
        else {
            Field field = null;
            try {
                field = content.getClass().getDeclaredField(name);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(content);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (value != null) return value.toString();
            else return null;
        }
    }

}

