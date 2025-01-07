package com.dionext.hiki.controllers;

import com.dionext.hiki.services.*;
import com.dionext.site.controllers.BaseSiteController;
import com.dionext.utils.HtmlUtils;
import com.dionext.utils.exceptions.DioRuntimeException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@Slf4j
@Tag(name = "HikingLand Site Controller", description = "HikingLand Site Controller")
@RequestMapping(value = {"/en", "/ru"})
public class HikingLandSiteController extends BaseSiteController {
    ImageGalleryCreatorService imageGalleryCreatorService;
    MediaGalleryCreatorService mediaGalleryCreatorService;
    PlaceCreatorService placeCreatorService;
    HikingLandPageCreatorService hikingLandPageElementService;

    private HikingLandPageParserService hikingLandPageParserService;
    @Autowired
    public void setHikingLandPageParserService(HikingLandPageParserService hikingLandPageParserService) {
        this.hikingLandPageParserService = hikingLandPageParserService;
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
                createSimpleSitePage(hikingLandPageParserService, hikingLandPageElementService));
    }

    @GetMapping(value = {"/gallery/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processImageGallery() {
        //fix old error
        String imageGalleryName = this.pageInfo.getRelativePath();
        int doubleDotIndex = imageGalleryName.lastIndexOf("..htm");
        if (doubleDotIndex > -1) {
            imageGalleryName = imageGalleryName.substring(0, doubleDotIndex) + ".htm";
            String redirectedUrl =
                    pageInfo.getOffsetStringToContextLevel() +
                            (pageInfo.isSiteLangInPath()?(pageInfo.getLocaleLang() + "/"):"")  +imageGalleryName;
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(
                    URI.create(redirectedUrl )).build();
        }
        //
        return sendOk(imageGalleryCreatorService.createImageGalleryPage());
    }

    @GetMapping(value = {"/media/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processMediaGallery() {
        return sendOk(mediaGalleryCreatorService.createMediaGalleryPage(
                hikingLandPageParserService.getSimpleSitePageSource("_movies.htm")));
    }

    @GetMapping(value = {"/places/**"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processPlaces() {
        //fix old error
        String relativePath = this.pageInfo.getRelativePath();
        if (relativePath != null && relativePath.endsWith("/D")){
            String redirectedUrl =
                    pageInfo.getOffsetStringToContextLevel() +
                            (pageInfo.isSiteLangInPath()?(pageInfo.getLocaleLang() + "/"):"")  +"places";
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(
                    URI.create(redirectedUrl )).build();

        }
        int doubleDotIndex = relativePath.lastIndexOf("rel=");
        if (doubleDotIndex > -1) {
            relativePath = relativePath.substring(0, doubleDotIndex);
            String redirectedUrl =
                    pageInfo.getOffsetStringToContextLevel() +
                            (pageInfo.isSiteLangInPath()?(pageInfo.getLocaleLang() + "/"):"")  +relativePath;
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(
                    URI.create(redirectedUrl )).build();
        }
        int slashIndex = relativePath.indexOf("/");
        if (slashIndex > -1) {
            int secondSlashIndex = relativePath.indexOf("/", slashIndex + 1);
            if (secondSlashIndex > -1) {
                String redirectedUrl =
                    pageInfo.getOffsetStringToContextLevel() +
                        (pageInfo.isSiteLangInPath() ? (pageInfo.getLocaleLang() + "/") : "")
                            + relativePath.substring(0, secondSlashIndex);
                return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(
                        URI.create(redirectedUrl)).build();
            }
        }
        //
        return sendOk(placeCreatorService.createPlacesPage(
                hikingLandPageParserService.getSimpleSitePageSource("_places.htm")));
    }
    @GetMapping(value = {"/testerror"}, produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> processError() {
        throw new DioRuntimeException("Test error");
    }


}
