package com.dionext.hiki.db.repositories;

import com.dionext.hiki.db.entity.JMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JMediaRepository extends JpaRepository<JMedia, Integer> {
    // @Query("SELECT u FROM JMedia u WHERE u.safeName = '0'")
    List<JMedia> findBySafeName(String safeName);

}