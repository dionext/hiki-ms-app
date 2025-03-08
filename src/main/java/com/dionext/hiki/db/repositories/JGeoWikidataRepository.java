package com.dionext.hiki.db.repositories;

import com.dionext.configuration.CacheConfiguration;
import com.dionext.hiki.db.entity.JGeoWikidata;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JGeoWikidataRepository extends JpaRepository<JGeoWikidata, String> {

    @Cacheable(CacheConfiguration.CACHE_PLACES)
    Optional<JGeoWikidata> findById(String id);

    List<JGeoWikidata> findByName(String name);

    List<JGeoWikidata> findByEnwikiLink(String enwikiLink);

    List<JGeoWikidata> findByNativewikiLink(String nativewikiLink);

    List<JGeoWikidata> findByLabelEn(String labelEn);

    List<JGeoWikidata> findByLabelNative(String labelNative);

    List<JGeoWikidata> findByParent(String parent);

    List<JGeoWikidata> findByJGeoWikidataIdIn(List<String> ids);

    @Cacheable(CacheConfiguration.CACHE_PLACES)
    @Query("SELECT u FROM JGeoWikidata u WHERE u.childrenCount <> 0")
    List<JGeoWikidata> findAllHaveChildren();
}