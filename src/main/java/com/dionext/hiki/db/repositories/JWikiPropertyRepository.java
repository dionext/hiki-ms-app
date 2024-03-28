package com.dionext.hiki.db.repositories;

import com.dionext.hiki.db.entity.JWikiProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JWikiPropertyRepository extends JpaRepository<JWikiProperty, String> {
}