package com.dionext.hiki.db.entity;

import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the JCountries database table.
 */
@Entity
@Table(name = "JCountries")
@NamedQuery(name = "JCountry.findAll", query = "SELECT j FROM JCountry j")
@SuppressWarnings({"java:S116", "java:S117", "java:S100"})
public class JCountry implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String JCountryId;

    @Lob
    @Column(name = "Capital")
    private String capital;

    @Lob
    @Column(name = "Continent")
    private String continent;

    @Lob
    @Column(name = "Dial")
    private String dial;

    @Lob
    @Column(name = "DS")
    private String ds;

    @Lob
    @Column(name = "EDGAR")
    private String edgar;

    @Lob
    @Column(name = "FIFA")
    private String fifa;

    @Lob
    @Column(name = "FIPS")
    private String fips;

    @Lob
    @Column(name = "GAUL")
    private String gaul;

    @Lob
    @Column(name = "Geoname_ID")
    private String geoname_ID;

    @Lob
    @Column(name = "Image")
    private String image;

    @Lob
    @Column(name = "IOC")
    private String ioc;

    @Lob
    @Column(name = "Is_independent")
    private String is_independent;

    @Lob
    private String ISO3166_1_Alpha_2;

    @Lob
    private String ISO3166_1_Alpha_3;

    @Lob
    private String ISO4217_currency_alphabetic_code;

    @Lob
    private String ISO4217_currency_country_name;

    @Lob
    private String ISO4217_currency_minor_unit;

    @Lob
    private String ISO4217_currency_name;

    @Lob
    private String ISO4217_currency_numeric_code;

    @Lob
    @Column(name = "ITU")
    private String itu;

    @Lob
    @Column(name = "Languages")
    private String languages;

    @Lob
    @Column(name = "M49")
    private String m49;

    @Lob
    @Column(name = "MARC")
    private String marc;

    @Lob
    @Column(name = "Name")
    private String name;

    @Lob
    @Column(name = "Official_name_en")
    private String official_name_en;

    @Lob
    @Column(name = "Official_name_fr")
    private String official_name_fr;

    @Lob
    @Column(name = "TLD")
    private String tld;

    @Lob
    @Column(name = "WikidataId")
    private String wikidataId;

    @Lob
    @Column(name = "WMO")
    private String wmo;

    public String getJCountryId() {
        return this.JCountryId;
    }

    public void setJCountryId(String JCountryId) {
        this.JCountryId = JCountryId;
    }

    public String getCapital() {
        return this.capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinent() {
        return this.continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getDial() {
        return this.dial;
    }

    public void setDial(String dial) {
        this.dial = dial;
    }

    public String getDs() {
        return this.ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getEdgar() {
        return this.edgar;
    }

    public void setEdgar(String edgar) {
        this.edgar = edgar;
    }

    public String getFifa() {
        return this.fifa;
    }

    public void setFifa(String fifa) {
        this.fifa = fifa;
    }

    public String getFips() {
        return this.fips;
    }

    public void setFips(String fips) {
        this.fips = fips;
    }

    public String getGaul() {
        return this.gaul;
    }

    public void setGaul(String gaul) {
        this.gaul = gaul;
    }

    public String getGeoname_ID() {
        return this.geoname_ID;
    }

    public void setGeoname_ID(String geoname_ID) {
        this.geoname_ID = geoname_ID;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIoc() {
        return this.ioc;
    }

    public void setIoc(String ioc) {
        this.ioc = ioc;
    }

    public String getIs_independent() {
        return this.is_independent;
    }

    public void setIs_independent(String is_independent) {
        this.is_independent = is_independent;
    }

    public String getISO3166_1_Alpha_2() {
        return this.ISO3166_1_Alpha_2;
    }

    public void setISO3166_1_Alpha_2(String ISO3166_1_Alpha_2) {
        this.ISO3166_1_Alpha_2 = ISO3166_1_Alpha_2;
    }

    public String getISO3166_1_Alpha_3() {
        return this.ISO3166_1_Alpha_3;
    }

    public void setISO3166_1_Alpha_3(String ISO3166_1_Alpha_3) {
        this.ISO3166_1_Alpha_3 = ISO3166_1_Alpha_3;
    }

    public String getISO4217_currency_alphabetic_code() {
        return this.ISO4217_currency_alphabetic_code;
    }

    public void setISO4217_currency_alphabetic_code(String ISO4217_currency_alphabetic_code) {
        this.ISO4217_currency_alphabetic_code = ISO4217_currency_alphabetic_code;
    }

    public String getISO4217_currency_country_name() {
        return this.ISO4217_currency_country_name;
    }

    public void setISO4217_currency_country_name(String ISO4217_currency_country_name) {
        this.ISO4217_currency_country_name = ISO4217_currency_country_name;
    }

    public String getISO4217_currency_minor_unit() {
        return this.ISO4217_currency_minor_unit;
    }

    public void setISO4217_currency_minor_unit(String ISO4217_currency_minor_unit) {
        this.ISO4217_currency_minor_unit = ISO4217_currency_minor_unit;
    }

    public String getISO4217_currency_name() {
        return this.ISO4217_currency_name;
    }

    public void setISO4217_currency_name(String ISO4217_currency_name) {
        this.ISO4217_currency_name = ISO4217_currency_name;
    }

    public String getISO4217_currency_numeric_code() {
        return this.ISO4217_currency_numeric_code;
    }

    public void setISO4217_currency_numeric_code(String ISO4217_currency_numeric_code) {
        this.ISO4217_currency_numeric_code = ISO4217_currency_numeric_code;
    }

    public String getItu() {
        return this.itu;
    }

    public void setItu(String itu) {
        this.itu = itu;
    }

    public String getLanguages() {
        return this.languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getM49() {
        return this.m49;
    }

    public void setM49(String m49) {
        this.m49 = m49;
    }

    public String getMarc() {
        return this.marc;
    }

    public void setMarc(String marc) {
        this.marc = marc;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficial_name_en() {
        return this.official_name_en;
    }

    public void setOfficial_name_en(String official_name_en) {
        this.official_name_en = official_name_en;
    }

    public String getOfficial_name_fr() {
        return this.official_name_fr;
    }

    public void setOfficial_name_fr(String official_name_fr) {
        this.official_name_fr = official_name_fr;
    }

    public String getTld() {
        return this.tld;
    }

    public void setTld(String tld) {
        this.tld = tld;
    }

    public String getWikidataId() {
        return this.wikidataId;
    }

    public void setWikidataId(String wikidataId) {
        this.wikidataId = wikidataId;
    }

    public String getWmo() {
        return this.wmo;
    }

    public void setWmo(String wmo) {
        this.wmo = wmo;
    }

}