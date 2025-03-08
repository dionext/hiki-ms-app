package com.dionext.hiki.controllers;


import com.dionext.hiki.db.entity.JGeoWikidata;
import com.dionext.hiki.services.WikiService;
import com.dionext.site.controllers.BaseSiteController;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Wiki Controller", description = "Wiki Controller")
@RequestMapping(value = {"/api"})
public class WikiController extends BaseSiteController {

    private WikiService wikiService;

    @Autowired
    public void setWikiService(WikiService wikiService) {
        this.wikiService = wikiService;
    }

    @GetMapping("load-wiki")
    public ResponseEntity<JGeoWikidata> load(@RequestParam(value = "id", defaultValue = "Q40") String id) {
        return new ResponseEntity<>(wikiService.loadWiki(id), HttpStatus.OK);
    }
    @GetMapping("load-wiki-from-db")
    public ResponseEntity<JGeoWikidata> loadFromDb(@RequestParam(value = "id", defaultValue = "Q40") String id) {
        return new ResponseEntity<>(wikiService.loadWikiFromDb(id), HttpStatus.OK);
    }
    @GetMapping("search-qid")
    public ResponseEntity<String> searchQid(@RequestParam(value = "title", defaultValue = "Austria") String title) {
        return new ResponseEntity<>(wikiService.searchQid(title), HttpStatus.OK);
    }

    //ai
    /*
    @GetMapping("list-of-tours")
    public ResponseEntity<List<Tour>> loadListOfTours(@RequestParam(value = "id", defaultValue = "Q40") String id) {
        JGeoWikidata jGeoWikidata = jGeoWikidataRepository.findById(id).orElse(null);
        return new ResponseEntity<List<Tour>>(aiService.listOfTours(jGeoWikidata, Long.valueOf(1), null), HttpStatus.OK);
    }
     */

}
