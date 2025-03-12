package com.dionext.hiki.services;


import com.dionext.hiki.components.GeoPageInfo;
import com.dionext.libauthspringstarter.com.dionext.security.services.AuthPageCreatorService;
import com.dionext.site.services.PageCreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@SuppressWarnings({"java:S5663", "java:S1192"})
public class HikingLandPageCreatorService extends AuthPageCreatorService {


    protected GeoPageInfo geoPageInfo;

    @Autowired
    public void setGeoPageInfo(GeoPageInfo geoPageInfo) {
        this.geoPageInfo = geoPageInfo;
    }

    @Override
    public String createBodyBottomInformation() {
        return """
                <small class="d-block mb-3 text-muted">&copy; 2018-2024 v 1.2</small>""" +
                createImage("images/wikidata.png", 60, 16, "Powered by the magic of Wikidata", "https://www.wikidata.org", true);
    }

    @Override
    public String createHeadMetaDescription() {
        return dfs(super.createHeadMetaDescription()) + createHeadGeoMeta();
    }

    private String createHeadGeoMeta() {
        StringBuilder str = new StringBuilder();
        //to do geo
        if (geoPageInfo.getLat() != null && geoPageInfo.getLon() != null) {
            str.append("""
                    <meta name="geo.position" content=\"""" + geoPageInfo.getLat() + ";" + geoPageInfo.getLon() + """
                    "/>"""); //"47.419164;13.354123"
            str.append("""
                    <meta name="ICBM" content=\"""" + geoPageInfo.getLat() + ", " + geoPageInfo.getLon() + """
                    "/>"""); //"47.419164, 13.354123"
        }
        if (geoPageInfo.getPlacename() != null) {
            str.append("""
                    <meta name="geo.placename" content=\"""" + geoPageInfo.getPlacename() + """
                    "/>""");
        }
        if (geoPageInfo.getRegion() != null) {
            str.append("""
                    <meta name="geo.region" content=\"""" + geoPageInfo.getRegion() + """
                    "/>""");
        }
        return str.toString();
    }
    public boolean getRu() {
        return "ru".equals(pageInfo.getLocaleLang());
    }

    /*
    @Override
    public String createBodyTopMenuLangSelector() {
        if (getRu()) {
            return super.createBodyTopMenuLangSelector();
        }
        else return "";
    }
    */

}
