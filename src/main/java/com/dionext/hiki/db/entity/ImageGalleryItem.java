package com.dionext.hiki.db.entity;

import jakarta.persistence.*;

import java.io.Serializable;


/**
 * The persistent class for the ImageGalleryItems database table.
 */
@Entity
@Table(name = "imagegalleryitems")
@NamedQuery(name = "ImageGalleryItem.findAll", query = "SELECT i FROM ImageGalleryItem i")
public class ImageGalleryItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column()
    private int imageGalleryItemId;

    @Column(columnDefinition="TEXT")
    private String caption;


    @Column(columnDefinition="TEXT")
    private String fullSizePath;


    @Column(columnDefinition="TEXT")
    private String title;


    @Column(columnDefinition="TEXT")
    private String tumbPath;


    public int getImageGalleryItemId() {
        return this.imageGalleryItemId;
    }

    public void setImageGalleryItemId(int imageGalleryItemId) {
        this.imageGalleryItemId = imageGalleryItemId;
    }

    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFullSizePath() {
        return this.fullSizePath;
    }

    public void setFullSizePath(String fullSizePath) {
        this.fullSizePath = fullSizePath;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTumbPath() {
        return this.tumbPath;
    }

    public void setTumbPath(String tumbPath) {
        this.tumbPath = tumbPath;
    }

}