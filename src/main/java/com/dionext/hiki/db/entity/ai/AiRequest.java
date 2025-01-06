package com.dionext.hiki.db.entity.ai;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class AiRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aiPromptId;

    private String externalId;
    private String externalType;
    private String externalVariant;

    @Column(columnDefinition="TEXT")
    private String result;

    private Long duration; //ms
    private LocalDateTime dateTime;
    private Long promptTokens;
    private Long generationTokens;
    @Column(precision = 40, scale = 20)
    private BigDecimal cost;
}
