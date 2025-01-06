package com.dionext.hiki.db.repositories.ai;

import com.dionext.hiki.db.entity.ai.AiRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRequestRepository extends JpaRepository<AiRequest, Long> {
}