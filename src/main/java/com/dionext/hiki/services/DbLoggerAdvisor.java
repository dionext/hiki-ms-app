package com.dionext.hiki.services;

import com.dionext.hiki.db.entity.ai.AiLogInfo;
import com.dionext.hiki.db.entity.ai.AiPrompt;
import com.dionext.hiki.db.entity.ai.AiRequest;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public class DbLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {

    private AiPrompt aiPrompt = null;
    private AiRequest aiRequest = null;

    private long startTime = 0;
    public DbLoggerAdvisor(AiLogInfo aiLogInfo){
        this.aiPrompt = aiLogInfo.aiPrompt();
        this.aiRequest = aiLogInfo.aiRequest();
    }

    private int order;

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public int getOrder() {
        return this.order;
    }


    private AdvisedRequest before(AdvisedRequest request) {
        aiPrompt.setUserPrompt(request.userText());

        StringBuilder systemPrompt = new StringBuilder();
        for (var e : request.adviseContext().entrySet()){
            systemPrompt.append(e.getValue());
        }
        aiPrompt.setSystemPrompt(systemPrompt.toString());
        startTime = System.currentTimeMillis();
        return request;
    }


    private void observeAfter(AdvisedResponse advisedResponse) {
        ChatResponse chatResponse = advisedResponse.response();
        aiRequest.setResult(chatResponse.getResult().getOutput().getContent());
        aiRequest.setDateTime(LocalDateTime.now());
        aiRequest.setDuration(System.currentTimeMillis() - startTime);
        aiRequest.setPromptTokens(chatResponse.getMetadata().getUsage().getPromptTokens());
        aiRequest.setGenerationTokens(chatResponse.getMetadata().getUsage().getGenerationTokens());
    }

    public String toString() {
        return SimpleLoggerAdvisor.class.getSimpleName();
    }

    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        this.observeAfter(advisedResponse);
        return advisedResponse;
    }

    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponses, this::observeAfter);
    }
}
