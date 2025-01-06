package com.dionext.hiki.services;

import com.dionext.hiki.db.entity.JGeoWikidata;
import com.dionext.hiki.db.entity.ai.*;
import com.dionext.hiki.db.repositories.ai.AiModelRepository;
import com.dionext.hiki.db.repositories.ai.AiPromptRepository;
import com.dionext.hiki.db.repositories.ai.AiRequestRepository;
import com.dionext.utils.OUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.metadata.PromptMetadata;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AIService {
    @Autowired
    private OpenAiChatModel chatModel;
    //private OllamaChatModel chatModel;

    @Autowired
    private AiModelRepository aiModelRepository;
    @Autowired
    private AiPromptRepository aiPromptRepository;
    @Autowired
    private AiRequestRepository aiRequestRepository;

    private ChatClient chatClient;

    //public AIService(OpenAiChatModel chatModel) {
      //  this.chatModel = chatModel;
    public AIService() {
    }
    @PostConstruct
    void postConstruct(){
        ChatClient.Builder builder = ChatClient.builder(chatModel);
        //https://docs.spring.io/spring-ai/reference/api/chatclient.html
        chatClient = builder
                //.defaultAdvisors(new SimpleLoggerAdvisor())
                .build();

        List<AiModel> aiModels = aiModelRepository.findAll();
        if (aiModels.size() == 0){
            AiModel aiModel = new AiModel();
            aiModel.setProvider("Perplexity");
            aiModel.setModel("llama-3.1-sonar-small-128k-online");
            aiModel.setPricePerRequest(BigDecimal.valueOf(0.005));
            aiModel.setPricePer1MAll(BigDecimal.valueOf(0.2));
            aiModelRepository.save(aiModel);
        }
    }
    public String placeHikingInfo(JGeoWikidata place, Long aiModelId, Long aiPromptId){
        AiLogInfo aiLogInfo = prepareAiLogInfo(
                aiModelId,
                aiPromptId,
                place.getJGeoWikidataId(),
                "JGeoWikidata",
                "hiking-info");

        String template = """
                Tell me about hiking in {place}.
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        promptTemplate.add("place", place.getName());
        Prompt prompt = promptTemplate.create();

        String resultItems = chatClient
                .prompt(prompt)
                .advisors(new SimpleLoggerAdvisor(
                        request -> "Custom request: " + request,
                        response -> "Custom response: " + response.getResult(),
                        1
                ), new DbLoggerAdvisor(aiLogInfo))
                .call()
                .content();
        postProccessAiLogInfo(aiLogInfo);
        return resultItems;
    }

    public List<Tour> listOfTours(JGeoWikidata place, Long aiModelId, Long aiPromptId){
        AiLogInfo aiLogInfo = prepareAiLogInfo(
                aiModelId,
                aiPromptId,
                place.getJGeoWikidataId(),
                "JGeoWikidata",
                "list-of-tours");

        String template = """
                Give me the list of hiking tours in {place}.
                """;// Use json format for output.

        PromptTemplate promptTemplate = new PromptTemplate(template);
        promptTemplate.add("place", place.getName());
        Prompt prompt = promptTemplate.create();

        List<Tour> resultItems = chatClient
                .prompt(prompt)
                .advisors(new SimpleLoggerAdvisor(
                        request -> "Custom request: " + request,
                        response -> "Custom response: " + response.getResult(),
                        1
                ), new DbLoggerAdvisor(aiLogInfo))
                .call()
                .entity(new ParameterizedTypeReference<List<Tour>>() {
                });
        postProccessAiLogInfo(aiLogInfo);
        return resultItems;
    }
    private AiLogInfo prepareAiLogInfo(Long aiModelId, Long aiPromptId, String externalId, String externalType, String externalVariant){
        AiPrompt aiPrompt = aiPromptId != null ? aiPromptRepository.findById(aiPromptId).orElse(null) : new AiPrompt();
        AiModel aiModel = null;
        if (aiPrompt.getId() == null) {
            if (aiModelId == null) throw new RuntimeException("aiModelId can not be null when aiPromptId is null");
            aiModel = aiModelRepository.findById(aiModelId).orElse(null);
            aiPrompt.setAiModelId(aiModel.getId());
        }
        else{
            aiModel = aiModelRepository.findById(aiPrompt.getAiModelId()).orElse(null);
        }
        AiRequest aiRequest = new AiRequest();
        aiRequest.setExternalId(externalId);
        aiRequest.setExternalType(externalType);
        aiRequest.setExternalVariant(externalVariant);
        return new AiLogInfo(aiModel, aiPrompt, aiRequest);
    }

    private void postProccessAiLogInfo(AiLogInfo aiLogInfo) {
        AiModel aiModel =  aiLogInfo.aiModel();
        AiRequest aiRequest = aiLogInfo.aiRequest();
        AiPrompt aiPrompt = aiLogInfo.aiPrompt();
        double cost = 0.0;
        if ( aiModel.getPricePer1MAll().doubleValue() != 0.0){
            cost += (aiRequest.getPromptTokens().doubleValue() + aiRequest.getGenerationTokens().doubleValue())
                    * aiModel.getPricePer1MAll().doubleValue()/1000000.0;
        }
        else {
            cost += aiRequest.getPromptTokens().doubleValue() * aiModel.getPricePer1MInput().doubleValue()/1000000.0;
            cost += aiRequest.getGenerationTokens().doubleValue() * aiModel.getPricePer1MOutput().doubleValue()/1000000.0;
        }
        cost += (1.0 * aiModel.getPricePerRequest().doubleValue());
        aiRequest.setCost(BigDecimal.valueOf(cost));
        aiPromptRepository.save(aiPrompt);//todo
        aiRequestRepository.save(aiRequest);
    }

}
