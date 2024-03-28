package com.dionext.hiki.db.repositories;

import com.dionext.hiki.db.entity.JCountry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JCountryRepository extends JpaRepository<JCountry, String> {
}