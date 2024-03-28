package com.dionext.hiki.db.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the JMedias database table.
 */
@Entity
@Table(name = "JMedias")
@NamedQuery(name = "JMedia.findAll", query = "SELECT j FROM JMedia j")
@SuppressWarnings({"java:S116", "java:S117", "java:S100", "java:S3776"})
public class JMedia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer JMediaId;

    @Lob
    @Column(name = "BackdropPath")
    private String backdropPath;

    @Column(name = "Budget")
    private BigInteger budget;

    @Lob
    @Column(name = "CastEn")
    private String castEn;

    @Lob
    @Column(name = "CastRu")
    private String castRu;

    @Lob
    @Column(name = "DescriptionEn")
    private String descriptionEn;

    @Lob
    @Column(name = "DescriptionRu")
    private String descriptionRu;

    @Lob
    @Column(name = "DirectorEn")
    private String directorEn;

    @Lob
    @Column(name = "DirectorRu")
    private String directorRu;

    @Lob
    @Column(name = "GenresEn")
    private String genresEn;

    @Lob
    @Column(name = "GenresRu")
    private String genresRu;

    @Lob
    @Column(name = "ImdbId")
    private String imdbId;

    @Lob
    @Column(name = "LabelEn")
    private String labelEn;

    @Lob
    @Column(name = "LabelRu")
    private String labelRu;

    @Lob
    @Column(name = "OverviewEn")
    private String overviewEn;

    @Lob
    @Column(name = "OverviewRu")
    private String overviewRu;

    @Lob
    @Column(name = "PosterPath")
    private String posterPath;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ReleaseDate")
    private Date releaseDate;

    @Column(name = "Runtime")
    private Integer runtime;

    @Lob
    @Column(name = "SafeName")
    private String safeName;

    @Column(name = "SortOrder")
    private Integer sortOrder;

    @Lob
    @Column(name = "TaglineEn")
    private String taglineEn;

    @Lob
    @Column(name = "TaglineRu")
    private String taglineRu;

    @Lob
    private String TMDbId;

    public int getJMediaId() {
        return this.JMediaId;
    }

    public void setJMediaId(int JMediaId) {
        this.JMediaId = JMediaId;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public BigInteger getBudget() {
        return this.budget;
    }

    public void setBudget(BigInteger budget) {
        this.budget = budget;
    }

    public String getCastEn() {
        return this.castEn;
    }

    public void setCastEn(String castEn) {
        this.castEn = castEn;
    }

    public String getCastRu() {
        return this.castRu;
    }

    public void setCastRu(String castRu) {
        this.castRu = castRu;
    }

    public String getDescriptionEn() {
        return this.descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionRu() {
        return this.descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDirectorEn() {
        return this.directorEn;
    }

    public void setDirectorEn(String directorEn) {
        this.directorEn = directorEn;
    }

    public String getDirectorRu() {
        return this.directorRu;
    }

    public void setDirectorRu(String directorRu) {
        this.directorRu = directorRu;
    }

    public String getGenresEn() {
        return this.genresEn;
    }

    public void setGenresEn(String genresEn) {
        this.genresEn = genresEn;
    }

    public String getGenresRu() {
        return this.genresRu;
    }

    public void setGenresRu(String genresRu) {
        this.genresRu = genresRu;
    }

    public String getImdbId() {
        return this.imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getLabelEn() {
        return this.labelEn;
    }

    public void setLabelEn(String labelEn) {
        this.labelEn = labelEn;
    }

    public String getLabelRu() {
        return this.labelRu;
    }

    public void setLabelRu(String labelRu) {
        this.labelRu = labelRu;
    }

    public String getOverviewEn() {
        return this.overviewEn;
    }

    public void setOverviewEn(String overviewEn) {
        this.overviewEn = overviewEn;
    }

    public String getOverviewRu() {
        return this.overviewRu;
    }

    public void setOverviewRu(String overviewRu) {
        this.overviewRu = overviewRu;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getSafeName() {
        return this.safeName;
    }

    public void setSafeName(String safeName) {
        this.safeName = safeName;
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getTaglineEn() {
        return this.taglineEn;
    }

    public void setTaglineEn(String taglineEn) {
        this.taglineEn = taglineEn;
    }

    public String getTaglineRu() {
        return this.taglineRu;
    }

    public void setTaglineRu(String taglineRu) {
        this.taglineRu = taglineRu;
    }

    public String getTMDbId() {
        return this.TMDbId;
    }

    public void setTMDbId(String TMDbId) {
        this.TMDbId = TMDbId;
    }

    public final String GetName(boolean ru) {
        if (ru && labelRu != null) {
            return labelRu;
        } else {
            return labelEn;
        }
    }

    public final String GetDescription(boolean ru) {
        if (ru && descriptionRu != null) {
            return descriptionRu;
        } else {
            return descriptionEn;
        }
    }

    public final String GetOverview(boolean ru) {
        if (ru && overviewRu != null) {
            return overviewRu;
        } else {
            return overviewEn;
        }
    }

    public final String LocalFileName() {
        return safeName;
    }

    public final String GetLocalUrl() {
        return safeName;
    }

    public final String GetCast(boolean ru) {
        if (ru && castRu != null) {
            return castRu;
        } else {
            return castEn;
        }
    }

    public final String GetDirector(boolean ru) {
        if (ru && directorRu != null) {
            return directorRu;
        } else {
            return directorEn;
        }
    }

    public final String GetGenres(boolean ru) {
        if (ru && this.genresRu != null) {
            return genresRu;
        } else {
            return genresEn;
        }
    }

    public final String GetTagline(boolean ru) {
        if (ru && this.taglineRu != null) {
            return taglineRu;
        } else {
            return taglineEn;
        }
    }

}