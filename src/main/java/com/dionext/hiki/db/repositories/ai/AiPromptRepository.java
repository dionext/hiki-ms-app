package com.dionext.hiki.db.repositories.ai;

import com.dionext.hiki.db.entity.ai.AiPrompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiPromptRepository extends JpaRepository<AiPrompt, Long> {
}