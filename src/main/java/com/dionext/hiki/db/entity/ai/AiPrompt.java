package com.dionext.hiki.db.entity.ai;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AiPrompt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aiModelId;

    @Column(columnDefinition="TEXT")
    private String systemPrompt;
    @Column(columnDefinition="TEXT")
    private String userPrompt;
}
