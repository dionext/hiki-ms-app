package com.dionext.hiki.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;

/**
 * The persistent class for the JWikiProperties database table.
 */
@Entity
@Table(name = "JWikiProperties")
@NamedQuery(name = "JWikiProperty.findAll", query = "SELECT j FROM JWikiProperty j")
@SuppressWarnings({"java:S116", "java:S117", "java:S100", "java:S3776"})
public class JWikiProperty implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("JWikiPropertyId")
    @Id
    private String JWikiPropertyId;

    @JsonProperty("Count")
    @Column(name = "Count")
    private int count;

    @JsonProperty("DescriptionEn")
    @Column(name = "DescriptionEn", columnDefinition="TEXT")
    private String descriptionEn;

    @JsonProperty("DescriptionNative")
    @Column(name = "DescriptionNative", columnDefinition="TEXT")
    private String descriptionNative;

    @JsonProperty("DescriptionRu")
    @Column(name = "DescriptionRu", columnDefinition="TEXT")
    private String descriptionRu;

    @JsonProperty("ExampleValue")
    @Column(name = "ExampleValue", columnDefinition="TEXT")
    private String exampleValue;

    @JsonProperty("ExampleValue1")
    @Column(name = "ExampleValue1", columnDefinition="TEXT")
    private String exampleValue1;

    @JsonProperty("FormatterRegExp")
    @Column(name = "FormatterRegExp", columnDefinition="TEXT")
    private String formatterRegExp;

    @JsonProperty("FormatterURL")
    @Column(name = "FormatterURL", columnDefinition="TEXT")
    private String formatterURL;

    @JsonProperty("FormatterURLAlt")
    @Column(name = "FormatterURLAlt", columnDefinition="TEXT")
    private String formatterURLAlt;

    @JsonProperty("LabelEn")
    @Column(name = "LabelEn", columnDefinition="TEXT")
    private String labelEn;

    @JsonProperty("LabelNative")
    @Column(name = "LabelNative", columnDefinition="TEXT")
    private String labelNative;

    @JsonProperty("LabelRu")
    @Column(name = "LabelRu", columnDefinition="TEXT")
    private String labelRu;

    @JsonProperty("ResultTestUrl")
    @Column(name = "ResultTestUrl", columnDefinition="TEXT")
    private String resultTestUrl;

    @JsonProperty("ResultTestUrlAlt")
    @Column(name = "ResultTestUrlAlt", columnDefinition="TEXT")
    private String resultTestUrlAlt;

    @JsonProperty("WebSite")
    @Column(name = "WebSite", columnDefinition="TEXT")
    private String webSite;


    public static String MakeFormatedUrl(String formatedStr, String value) {
        if (formatedStr == null) {
            return null;
        }
        if (value == null) {
            return formatedStr;
        }
        return formatedStr.replace("$1", value);
    }

    public String getJWikiPropertyId() {
        return this.JWikiPropertyId;
    }

    public void setJWikiPropertyId(String JWikiPropertyId) {
        this.JWikiPropertyId = JWikiPropertyId;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescriptionEn() {
        return this.descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionNative() {
        return this.descriptionNative;
    }

    public void setDescriptionNative(String descriptionNative) {
        this.descriptionNative = descriptionNative;
    }

    public String getDescriptionRu() {
        return this.descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getExampleValue() {
        return this.exampleValue;
    }

    public void setExampleValue(String exampleValue) {
        this.exampleValue = exampleValue;
    }

    public String getExampleValue1() {
        return this.exampleValue1;
    }

    public void setExampleValue1(String exampleValue1) {
        this.exampleValue1 = exampleValue1;
    }

    public String getFormatterRegExp() {
        return this.formatterRegExp;
    }

    public void setFormatterRegExp(String formatterRegExp) {
        this.formatterRegExp = formatterRegExp;
    }

    public String getFormatterURL() {
        return this.formatterURL;
    }

    public void setFormatterURL(String formatterURL) {
        this.formatterURL = formatterURL;
    }

    public String getFormatterURLAlt() {
        return this.formatterURLAlt;
    }

    public void setFormatterURLAlt(String formatterURLAlt) {
        this.formatterURLAlt = formatterURLAlt;
    }

    public String getLabelEn() {
        return this.labelEn;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelNative() {
        return this.labelNative;
    }

    public void setLabelNative(String labelNative) {
        this.labelNative = labelNative;
    }

    public String getLabelRu() {
        return this.labelRu;
    }

    public void setLabelRu(String labelRu) {
        this.labelRu = labelRu;
    }

    public String getResultTestUrl() {
        return this.resultTestUrl;
    }

    public void setResultTestUrl(String resultTestUrl) {
        this.resultTestUrl = resultTestUrl;
    }

    public String getResultTestUrlAlt() {
        return this.resultTestUrlAlt;
    }

    public void setResultTestUrlAlt(String resultTestUrlAlt) {
        this.resultTestUrlAlt = resultTestUrlAlt;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    /////////////////////////////////////////////
    public final String getUrlExampleValue() {
        return MakeFormatedUrl(getFormatterURL(), getExampleValue());
    }

    public final String getUrlAltExampleValue() {
        return MakeFormatedUrl(getFormatterURLAlt(), getExampleValue());
    }

    public final String GetLabel(boolean ru) {
        if (ru && getLabelRu() != null) {
            return getLabelRu();
        } else if (getLabelEn() != null) {
            return getLabelEn();
        } else if (getLabelNative() != null) {
            return getLabelNative();
        } else {
            return "Wikidata property  " + JWikiPropertyId;
        }
    }

    public final boolean IsLoaded() {
        return getLabelNative() != null || getLabelEn() != null;
    }


}