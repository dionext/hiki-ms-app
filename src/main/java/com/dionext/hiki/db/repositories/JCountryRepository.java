package com.dionext.hiki.db.repositories;

import com.dionext.hiki.db.entity.JCountry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JCountryRepository extends JpaRepository<JCountry, String> {

    Optional<JCountry> findByWikidataId(String wikidataId);
}