package com.dionext.hiki.controllers;

import com.dionext.hiki.services.HikingLandPageCreatorService;
import com.dionext.hiki.services.ImageGalleryCreatorService;
import com.dionext.hiki.services.MediaGalleryCreatorService;
import com.dionext.hiki.services.PlaceCreatorService;
import com.dionext.site.controllers.BaseSiteController;
import com.dionext.site.services.PageParserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "HikingLand Site Controller", description = "HikingLand Site Controller")
@RequestMapping(value = {"/hiking/en", "/hiking/ru"})
public class HikingLandSiteController extends BaseSiteController {
    ImageGalleryCreatorService imageGalleryCreatorService;
    MediaGalleryCreatorService mediaGalleryCreatorService;
    PlaceCreatorService placeCreatorService;
    HikingLandPageCreatorService hikingLandPageElementService;

    private PageParserService pageParserService;
    @Autowired
    public void setPageParserService(PageParserService pageParserService) {
        this.pageParserService = pageParserService;
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

    @GetMapping(value = {"/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processPage() {
        return sendOk(
                createSimpleSitePage(pageParserService, hikingLandPageElementService));
    }

    @GetMapping(value = {"/gallery/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processImageGallery() {
        return sendOk(imageGalleryCreatorService.createImageGalleryPage());
    }

    @GetMapping(value = {"/media/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processMediaGallery() {
        return sendOk(mediaGalleryCreatorService.createMediaGalleryPage(
                pageParserService.getSimpleSitePageSource("_movies.htm")));
    }

    @GetMapping(value = {"/places/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processPlaces() {
        return sendOk(placeCreatorService.createPlacesPage(
                pageParserService.getSimpleSitePageSource("_places.htm")));
    }


}
