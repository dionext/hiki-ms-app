package com.dionext.hiki.controllers;

import com.dionext.hiki.services.HikingLandSitemapService;
import com.dionext.site.controllers.BaseSiteController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Map;

@RestController
@Slf4j
@Tag(name = "Sitemap Controller", description = "Sitemap Controller")
@RequestMapping(value = {"/hiking"})
public class SitemapController extends BaseSiteController {

    HikingLandSitemapService hikingLandSitemapService;

    @Autowired
    public void setSitemapService(HikingLandSitemapService hikingLandSitemapService) {
        this.hikingLandSitemapService = hikingLandSitemapService;
    }

    @GetMapping(value = {"/sitemap.xml"}, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> processSitemap() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE + "; charset=utf-8");
        return new ResponseEntity(hikingLandSitemapService.createSitemap(false), responseHeaders, HttpStatus.OK);
    }
    @GetMapping("/robots.txt")
    public ResponseEntity<String> robots(@RequestParam Map<String,String> params) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + "; charset=utf-8");
        return new ResponseEntity(MessageFormat.format("""
                User-agent: *
                Disallow: /admin/
                Disallow: /api/
                Sitemap: {0}/sitemap.xml
                """, pageInfo.getDomainUrl()), responseHeaders, HttpStatus.OK);
    }

    /**
     *  We need this for auto redirect to lang page
     * @param params
     * @return
     */
    @GetMapping("/**")
    public ResponseEntity<String> all(@RequestParam Map<String,String> params) {
        return sendOk("");
    }

}
