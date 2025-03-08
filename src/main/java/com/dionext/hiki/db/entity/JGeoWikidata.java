package com.dionext.hiki.db.entity;

import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the JGeoWikidatas database table.
 */
//ALTER TABLE `hiking`.`JGeoWikidatas`
//ADD COLUMN `OfficialLangs` LONGTEXT NULL DEFAULT NULL AFTER `ChildrenCount`;
@Entity
@Table(name = "JGeoWikidatas")
@NamedQuery(name = "JGeoWikidata.findAll", query = "SELECT j FROM JGeoWikidata j")
@SuppressWarnings({"java:S116", "java:S117", "java:S100", "java:S3776"})
public class JGeoWikidata implements Serializable {

    public static final String PLACE = "place";
    public static final String PLACE_INFO = "place-info";
    public static final String HIKI = "hiki";

    private static final long serialVersionUID = 1L;

    @Id
    private String JGeoWikidataId;

    @Column(name = "AboveSeaLevel")
    private String aboveSeaLevel;

    @Column(name = "Area")
    private String area;

    @Column(name = "Capital")
    private String capital;

    @Column(name = "CoatOfArmsImage", columnDefinition="TEXT")
    private String coatOfArmsImage;

    @Column(name = "CommonsCategory", columnDefinition="TEXT")
    private String commonsCategory;

    @Column(name = "CommonsGallery", columnDefinition="TEXT")
    private String commonsGallery;

    @Column(name = "CommonsMapsCategory", columnDefinition="TEXT")
    private String commonsMapsCategory;

    @Column(name = "Contains", columnDefinition="TEXT")
    private String contains;

    @Column(name = "Country")
    private String country;

    @Column(name = "DescriptionEn", columnDefinition="TEXT")
    private String descriptionEn;

    @Column(name = "DescriptionNative", columnDefinition="TEXT")
    private String descriptionNative;

    @Column(name = "DescriptionRu", columnDefinition="TEXT")
    private String descriptionRu;

    @Column(name = "DetailMapImage", columnDefinition="TEXT")
    private String detailMapImage;

    @Column(name = "EnwikiLink", columnDefinition="TEXT")
    private String enwikiLink;

    @Column(name = "EnWikivoyage", columnDefinition="TEXT")
    private String enWikivoyage;

    @Column(name = "ExtIds", columnDefinition="TEXT")
    private String extIds;

    @Column(name = "FlagImage", columnDefinition="TEXT")
    private String flagImage;

    @Column(name = "HighestPointPlace")
    private String highestPointPlace;

    @Column(name = "Image", columnDefinition="TEXT")
    private String image;

    @Column(name = "ImageFirstChild", columnDefinition="TEXT")
    private String imageFirstChild;

    @Column(name = "IsAlps")
    private byte isAlps;

    @Column(name = "IsCAlps")
    private byte isCAlps;

    @Column(name = "IsML")
    private byte isML;

    private String JCountryId;

    @Column(name = "LabelEn", columnDefinition="TEXT")
    private String labelEn;

    @Column(name = "LabelNative", columnDefinition="TEXT")
    private String labelNative;

    @Column(name = "LabelRu", columnDefinition="TEXT")
    private String labelRu;

    @Column(name = "Lang")
    private String lang;

    @Column(name = "OfficialLangs", columnDefinition="TEXT")
    private String officialLangs;


    @Column(name = "Latitude")
    private String latitude;

    @Column(name = "Level")
    private int level;

    @Column(name = "LicencePlateCode", columnDefinition="TEXT")
    private String licencePlateCode;

    @Column(name = "LocalDialingCode", columnDefinition="TEXT")
    private String localDialingCode;

    @Column(name = "LocationMap", columnDefinition="TEXT")
    private String locationMap;

    @Column(name = "LocatorMapImage", columnDefinition="TEXT")
    private String locatorMapImage;

    @Column(name = "Longitude")
    private String longitude;

    @Column(name = "LowestPointPlace")
    private String lowestPointPlace;

    @Column(name = "Name", columnDefinition="TEXT")
    private String name;

    @Column(name = "NativewikiLink", columnDefinition="TEXT")
    private String nativewikiLink;

    @Column(name = "NativeWikivoyage", columnDefinition="TEXT")
    private String nativeWikivoyage;

    @Column(name = "PageBanner", columnDefinition="TEXT")
    private String pageBanner;

    @Column(name = "Parent")
    private String parent;

    @Column(name = "Population")
    private String population;

    @Column(name = "PostalCode", columnDefinition="TEXT")
    private String postalCode;

    @Column(name = "RuwikiLink", columnDefinition="TEXT")
    private String ruwikiLink;

    @Column(name = "RuWikivoyage", columnDefinition="TEXT")
    private String ruWikivoyage;

    @Column(name = "SharesBorderWith", columnDefinition="TEXT")
    private String sharesBorderWith;

    @Column(name = "TouristOffice", columnDefinition="TEXT")
    private String touristOffice;

    @Column(name = "WebSite", columnDefinition="TEXT")
    private String webSite;

    @Column(name = "ChildrenCount")
    private int childrenCount;

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    public String getJGeoWikidataId() {
        return this.JGeoWikidataId;
    }

    public void setJGeoWikidataId(String JGeoWikidataId) {
        this.JGeoWikidataId = JGeoWikidataId;
    }

    public String getAboveSeaLevel() {
        return this.aboveSeaLevel;
    }

    public void setAboveSeaLevel(String aboveSeaLevel) {
        this.aboveSeaLevel = aboveSeaLevel;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCapital() {
        return this.capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCoatOfArmsImage() {
        return this.coatOfArmsImage;
    }

    public void setCoatOfArmsImage(String coatOfArmsImage) {
        this.coatOfArmsImage = coatOfArmsImage;
    }

    public String getCommonsCategory() {
        return this.commonsCategory;
    }

    public void setCommonsCategory(String commonsCategory) {
        this.commonsCategory = commonsCategory;
    }

    public String getCommonsGallery() {
        return this.commonsGallery;
    }

    public void setCommonsGallery(String commonsGallery) {
        this.commonsGallery = commonsGallery;
    }

    public String getCommonsMapsCategory() {
        return this.commonsMapsCategory;
    }

    public void setCommonsMapsCategory(String commonsMapsCategory) {
        this.commonsMapsCategory = commonsMapsCategory;
    }

    public String getContains() {
        return this.contains;
    }

    public void setContains(String contains) {
        this.contains = contains;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getDetailMapImage() {
        return this.detailMapImage;
    }

    public void setDetailMapImage(String detailMapImage) {
        this.detailMapImage = detailMapImage;
    }

    public String getEnwikiLink() {
        return this.enwikiLink;
    }

    public void setEnwikiLink(String enwikiLink) {
        this.enwikiLink = enwikiLink;
    }

    public String getEnWikivoyage() {
        return this.enWikivoyage;
    }

    public void setEnWikivoyage(String enWikivoyage) {
        this.enWikivoyage = enWikivoyage;
    }

    public String getExtIds() {
        return this.extIds;
    }

    public void setExtIds(String extIds) {
        this.extIds = extIds;
    }

    public String getFlagImage() {
        return this.flagImage;
    }

    public void setFlagImage(String flagImage) {
        this.flagImage = flagImage;
    }

    public String getHighestPointPlace() {
        return this.highestPointPlace;
    }

    public void setHighestPointPlace(String highestPointPlace) {
        this.highestPointPlace = highestPointPlace;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageFirstChild() {
        return this.imageFirstChild;
    }

    public void setImageFirstChild(String imageFirstChild) {
        this.imageFirstChild = imageFirstChild;
    }

    public byte getIsAlps() {
        return this.isAlps;
    }

    public void setIsAlps(byte isAlps) {
        this.isAlps = isAlps;
    }

    public byte getIsCAlps() {
        return this.isCAlps;
    }

    public void setIsCAlps(byte isCAlps) {
        this.isCAlps = isCAlps;
    }

    public byte getIsML() {
        return this.isML;
    }

    public void setIsML(byte isML) {
        this.isML = isML;
    }

    public String getJCountryId() {
        return this.JCountryId;
    }

    public void setJCountryId(String JCountryId) {
        this.JCountryId = JCountryId;
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

    public String getLang() {
        return this.lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLicencePlateCode() {
        return this.licencePlateCode;
    }

    public void setLicencePlateCode(String licencePlateCode) {
        this.licencePlateCode = licencePlateCode;
    }

    public String getLocalDialingCode() {
        return this.localDialingCode;
    }

    public void setLocalDialingCode(String localDialingCode) {
        this.localDialingCode = localDialingCode;
    }

    public String getLocationMap() {
        return this.locationMap;
    }

    public void setLocationMap(String locationMap) {
        this.locationMap = locationMap;
    }

    public String getLocatorMapImage() {
        return this.locatorMapImage;
    }

    public void setLocatorMapImage(String locatorMapImage) {
        this.locatorMapImage = locatorMapImage;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLowestPointPlace() {
        return this.lowestPointPlace;
    }

    public void setLowestPointPlace(String lowestPointPlace) {
        this.lowestPointPlace = lowestPointPlace;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativewikiLink() {
        return this.nativewikiLink;
    }

    public void setNativewikiLink(String nativewikiLink) {
        this.nativewikiLink = nativewikiLink;
    }

    public String getNativeWikivoyage() {
        return this.nativeWikivoyage;
    }

    public void setNativeWikivoyage(String nativeWikivoyage) {
        this.nativeWikivoyage = nativeWikivoyage;
    }

    public String getPageBanner() {
        return this.pageBanner;
    }

    public void setPageBanner(String pageBanner) {
        this.pageBanner = pageBanner;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPopulation() {
        return this.population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getRuwikiLink() {
        return this.ruwikiLink;
    }

    public void setRuwikiLink(String ruwikiLink) {
        this.ruwikiLink = ruwikiLink;
    }

    public String getRuWikivoyage() {
        return this.ruWikivoyage;
    }

    public void setRuWikivoyage(String ruWikivoyage) {
        this.ruWikivoyage = ruWikivoyage;
    }

    public String getSharesBorderWith() {
        return this.sharesBorderWith;
    }

    public void setSharesBorderWith(String sharesBorderWith) {
        this.sharesBorderWith = sharesBorderWith;
    }

    public String getTouristOffice() {
        return this.touristOffice;
    }

    public void setTouristOffice(String touristOffice) {
        this.touristOffice = touristOffice;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getOfficialLangs() {
        return officialLangs;
    }

    public void setOfficialLangs(String officialLangs) {
        this.officialLangs = officialLangs;
    }

/////////////////////

    public final String LocalFileName() {
        return getName();
    }

    public final String GetLocalFileNameForInit() {

        String txt = null;
        if (getEnwikiLink() != null) {
            txt = getEnwikiLink(); // + ".htm"; //6000 пустых из ~60000
        } else if (getNativewikiLink() != null) {
            txt = getNativewikiLink(); // + ".htm"; //остается 367
        } else if (getLabelEn() != null) {
            txt = getLabelEn(); // + ".htm";//остается 126
        } else if (getLabelNative() != null) {
            txt = getLabelNative();
        } else {
            txt = JGeoWikidataId;
        }
        if (txt.contains("/")) {
            txt = txt.replace("/", "_");
        }
        //for example "Biel/Bienne administrative district" (Q660893)
        //таких страниц немного, и в Википедии слеш не заменяется, но у нас для сохраненяи на диске и для ненарушения глубины директорий лучше заменять
        //https://en.wikipedia.org/wiki/Biel/Bienne_(administrative_district)
        //Twann-Tüscherz https://en.wikipedia.org/wiki/Twann-T%C3%BCscherz
        return txt;
    }

    public final String GetLocalUrl() {
        return getName();
    }

    public final String GetLabel(boolean ru) {
        if (ru && getLabelRu() != null) {
            return getLabelRu();
        } else if (getLabelEn() != null) {
            return getLabelEn();
        } else if (getEnwikiLink() != null) {
            return getEnwikiLink();
        } else if (getLabelNative() != null) {
            return getLabelNative();
        } else {
            return "Wikidata: " + JGeoWikidataId;
        }
    }

    public final String GetFullLabel(boolean ru) {
        if (ru && getLabelRu() != null) {
            if (getLabelEn() != null) {
                if (getLabelNative() != null) {
                    return getLabelRu() + " (" + getLabelEn() + ", " + getLabelNative() + ")";
                }
                return getLabelRu() + " (" + getLabelEn() + ")";
            } else if (getLabelNative() != null) {
                return getLabelRu() + " (" + getLabelNative() + ")";
            } else {
                return getLabelRu();
            }
        } else if (getLabelEn() != null) {
            if (getLabelNative() != null) {
                return getLabelEn() + " (" + getLabelNative() + ")";
            } else {
                return getLabelEn();
            }

        } else if (getEnwikiLink() != null) {
            if (getLabelNative() != null) {
                return getEnwikiLink() + " (" + getLabelNative() + ")";
            } else {
                return getEnwikiLink();
            }
        } else if (getLabelNative() != null) {
            return getLabelNative();
        } else {
            return "Wikidata: " + JGeoWikidataId;
        }
    }

    public String getDescription(boolean ru) {
        if (ru) return descriptionRu;
        else return descriptionEn;
    }

}