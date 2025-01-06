package com.dionext.hiki.services;

import com.dionext.configuration.CacheConfiguration;
import com.dionext.site.dto.PageUrl;
import com.dionext.site.dto.PageUrlAlt;
import com.dionext.site.services.SitemapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class HikingLandSitemapService {
    ImageGalleryCreatorService imageGalleryCreatorService;
    MediaGalleryCreatorService mediaGalleryCreatorService;
    PlaceCreatorService placeCreatorService;
    HikingLandPageCreatorService hikingLandPageElementService;
    SitemapService sitemapService;

    @Autowired
    public void setSitemapService(SitemapService sitemapService) {
        this.sitemapService = sitemapService;
    }

    @Autowired
    public void setImageGalleryCreatorService(ImageGalleryCreatorService imageGalleryCreatorService) {
        this.imageGalleryCreatorService = imageGalleryCreatorService;
    }

    @Autowired
    public void setMediaGalleryCreatorService(MediaGalleryCreatorService mediaGalleryCreatorService) {
        this.mediaGalleryCreatorService = mediaGalleryCreatorService;
    }

    @Autowired
    public void setPlaceCreatorService(PlaceCreatorService placeCreatorService) {
        this.placeCreatorService = placeCreatorService;
    }

    @Autowired
    public void setHikingLandPageElementService(HikingLandPageCreatorService hikingLandPageElementService) {
        this.hikingLandPageElementService = hikingLandPageElementService;
    }

    @Cacheable(CacheConfiguration.CACHE_COMMON)
    public String createSitemap(boolean langSupport) {
        log.debug("creating sitemap");
        List<PageUrl> pages = new ArrayList<>();
        pages.addAll(hikingLandPageElementService.findAllPages());
        pages.addAll(placeCreatorService.findAllPlacesPages());
        return sitemapService.createSitemap(pages, hikingLandPageElementService, false);
    }


}
