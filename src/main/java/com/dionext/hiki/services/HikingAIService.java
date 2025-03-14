package com.dionext.hiki.services;

import com.dionext.ai.entity.AiLogInfo;
import com.dionext.ai.entity.AiPrompt;
import com.dionext.ai.entity.AiRequest;
import com.dionext.ai.repositories.AiPromptRepository;
import com.dionext.ai.services.AIRequestService;
import com.dionext.ai.utils.DbLoggerAdvisor;
import com.dionext.hiki.db.entity.JGeoWikidata;
import com.dionext.hiki.db.entity.JGeoWikidataInfo;
import com.dionext.hiki.db.entity.ai.Tour;
import com.dionext.hiki.db.repositories.JGeoWikidataInfoRepository;
import com.dionext.hiki.db.repositories.JGeoWikidataRepository;
import com.dionext.hiki.utils.AIPlaceInfoMarkdownRenderer;
import com.dionext.job.*;
import com.dionext.job.entity.JobInstance;
import com.dionext.utils.CmMarkdownUtils;
import com.google.common.base.Strings;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HikingAIService {
    @Autowired
    protected JobManager jobManager;
    @Autowired
    private AIRequestService aiRequestService;
    @Autowired
    private AiPromptRepository aiPromptRepository;
    @Autowired
    private JGeoWikidataRepository jGeoWikidataRepository;
    @Autowired
    private JGeoWikidataInfoRepository jGeoWikidataInfoRepository;

    @PostConstruct
    void postConstruct() {
        List<AiPrompt> aiPrompts = aiPromptRepository.findAll();
        if (aiPrompts.size() == 0) {
            //1
            AiPrompt aiPrompt = new AiPrompt();
            aiPrompt.setId(1L);
            aiPrompt.setName("Place information");
            aiPrompt.setSystemPromptTempl("SYSTEM: You are a hiking instructor");
            aiPrompt.setUserPromptTempl("Tell me about hiking in {place}");
            aiPromptRepository.save(aiPrompt);

            //2
            aiPrompt = new AiPrompt();
            aiPrompt.setId(2L);
            aiPrompt.setName("List of tours in place");
            aiPrompt.setSystemPromptTempl("SYSTEM: You are a hiking instructor");
            aiPrompt.setUserPromptTempl("Give me the list of hiking tours in {place}. Use json format for output");
            aiPromptRepository.save(aiPrompt);

        }

    }


    public String createRunJobParameters(String jobTypeId, JobInstance jobInstance, boolean readOnly) {

        if (jobTypeId == null && jobInstance != null) jobTypeId = jobInstance.getJobTypeId();

        StringBuilder str = new StringBuilder();

        if ("listOfTours".equals(jobTypeId) || "placeInfo".equals(jobTypeId)) {
            str.append(
                    JobView.createRunJobParameter("countType", "Тип количества позиций",
                            jobInstance != null ? jobInstance.getParameter("countType") : CountType.ALL.name(),
                            readOnly));
            str.append(
                    JobView.createRunJobParameter("topId", "Корневой идентификатор",
                            jobInstance != null ? jobInstance.getParameter("topId") : "",
                            readOnly));
            str.append(
                    JobView.createRunJobParameter("isCAlp", "Только входящие в зону Альп и их предки",
                            jobInstance != null ? jobInstance.getParameter("isCAlp") : "false",
                            readOnly));
            str.append(
                    JobView.createRunJobParameter("aiModelId", "Id model",
                            jobInstance != null ? jobInstance.getParameter("aiModelId") : "4",
                            readOnly));
            str.append(
                    JobView.createRunJobParameter("isOverrideRequest", "Перезаписывать ранее сохраненные запросы",
                            jobInstance != null ? jobInstance.getParameter("isOverrideRequest") : "false",
                            readOnly));
            str.append(
                    JobView.createRunJobParameter("aiPromptId", "Id Prompt",
                            jobInstance != null ? jobInstance.getParameter("aiPromptId") : "1",
                            readOnly));
        }
        else if ("placeInfoCopy".equals(jobTypeId)) {
            str.append(
                    JobView.createRunJobParameter("countType", "Тип количества позиций",
                            jobInstance != null ? jobInstance.getParameter("countType") : CountType.ALL.name(),
                            readOnly));
            str.append(
                    JobView.createRunJobParameter("isOverrideRequest", "Перезаписывать ранее сохраненные запросы",
                            jobInstance != null ? jobInstance.getParameter("isOverrideRequest") : "false",
                            readOnly));

        }

        return str.toString();
    }

    public JobBatchRunner createJob(String jobTypeId, JobInstance _jobInstance) {
        JobBatchRunner jobBatchRunner = new BaseJobBatchRunner();
        if ("listOfTours".equals(jobTypeId) || "placeInfo".equals(jobTypeId)) {
            setupJobBatchRunnerForPlaceProcessing(jobBatchRunner);

            jobBatchRunner.setJobBatchItemProcessor((jobInstance, item) -> {
                Long aiModelId = Long.valueOf(jobInstance.getParameter("aiModelId"));
                Long aiPromptId = Long.valueOf(jobInstance.getParameter("aiPromptId"));
                boolean isOverrideRequest = Boolean.parseBoolean(jobInstance.getParameter("isOverrideRequest"));

                JGeoWikidata place = (JGeoWikidata) item;
                Optional<AiRequest> aiRequest = aiRequestService.findByExternalDomainAndExternalEntityAndExternalVariantAndExternalIdAndAiModelIdAndAiPromptId(
                        JGeoWikidata.HIKI, JGeoWikidata.PLACE, JGeoWikidata.PLACE_INFO, place.getJGeoWikidataId(), aiModelId, aiPromptId).stream().findAny();
                if (aiRequest.isEmpty() || isOverrideRequest) {
                    AiLogInfo aiLogInfo = aiRequestService.createAiLogInfo(aiModelId, aiPromptId,
                            aiRequest.orElse(new AiRequest()));
                    if ("placeInfo".equals(jobTypeId)) placeInfo(place, aiLogInfo);
                    else if ("listOfTours".equals(jobTypeId)) listOfTours(place, aiLogInfo);
                }
            });
        }
        else if ("placeInfoCopy".equals(jobTypeId)) {
            jobBatchRunner.setJobBatchListMaker((jobInstance) -> {
                CountType countType = CountType.valueOf(jobInstance.getParameter("countType"));
                Collection<AiRequest> items = aiRequestService
                        .findByExternalDomainAndExternalEntityAndExternalVariant(
                                JGeoWikidata.HIKI, JGeoWikidata.PLACE, JGeoWikidata.PLACE_INFO);

                if (countType == CountType.ONE)
                    return items.stream().limit(1).collect(Collectors.toList());
                else if (countType == CountType.TEN)
                    return items.stream().limit(10).collect(Collectors.toList());
                else //if (countType == CountType.ALL)
                    return items;

            });
            jobBatchRunner.setJobBatchIdExtractor((_, item) -> ((AiRequest) item).getExternalId());

            jobBatchRunner.setJobBatchItemProcessor((jobInstance, item) -> {
                boolean isOverrideRequest = Boolean.parseBoolean(jobInstance.getParameter("isOverrideRequest"));
                AiRequest aiRequest = (AiRequest) item;
                JGeoWikidataInfo jGeoWikidataInfo = jGeoWikidataInfoRepository.findById(aiRequest.getExternalId()).orElse(null);
                if (jGeoWikidataInfo == null) {
                    jGeoWikidataInfo = new JGeoWikidataInfo();
                    jGeoWikidataInfo.setJGeoWikidataId(aiRequest.getExternalId());
                    jGeoWikidataInfo.setInfoEn(CmMarkdownUtils.markdownToHtml(aiRequest.getResult(), AIPlaceInfoMarkdownRenderer.class));

                    jGeoWikidataInfoRepository.save(jGeoWikidataInfo);
                } else if (isOverrideRequest) {
                    jGeoWikidataInfo.setInfoEn(CmMarkdownUtils.markdownToHtml(aiRequest.getResult(), AIPlaceInfoMarkdownRenderer.class));
                    jGeoWikidataInfoRepository.save(jGeoWikidataInfo);
                }
            });
        }
        else {
            throw new RuntimeException("Unsupported job Type = " + jobTypeId);
        }

        return jobBatchRunner;
    }
    private Collection<JGeoWikidata> findAllChildRecursive(String itemId) {
        JGeoWikidata parent = jGeoWikidataRepository.findById(itemId).orElse(null);
        if (parent == null) throw new RuntimeException("Item not found for id = " + itemId);
        List<JGeoWikidata> result = new ArrayList<>();
        Set<String> checkDoubleSet = new HashSet<>();
        findAllChildRecursive(parent, result, checkDoubleSet);
        return result;
    }

    private void findAllChildRecursive(JGeoWikidata parent, List<JGeoWikidata> result, Set<String> checkDoubleSet) {

        if (!checkDoubleSet.contains(parent.getJGeoWikidataId())){
            result.add(parent);
            checkDoubleSet.add(parent.getJGeoWikidataId());
        }
        List<JGeoWikidata> items = jGeoWikidataRepository.findByParent(parent.getJGeoWikidataId());
        for (var item : items) {
            if (checkDoubleSet.contains(item.getJGeoWikidataId())) {
                log.warn("!!!!! Item " + item.getJGeoWikidataId() + " allready in child set");
                continue;
            } else {
                checkDoubleSet.add(item.getJGeoWikidataId());
                result.add(item);
            }
            //if (item.getLevel() >= MAX_LEVEL_FOR_SINGLE_PAGE) {
            findAllChildRecursive(item, result, checkDoubleSet);
            //}
        }
    }
    private void setupJobBatchRunnerForPlaceProcessing(JobBatchRunner jobBatchRunner) {
        jobBatchRunner.setJobBatchListMaker((jobInstance) -> {
            CountType countType = CountType.valueOf(jobInstance.getParameter("countType"));
            boolean isCAlp = Boolean.parseBoolean(jobInstance.getParameter("isCAlp"));
            String topId = jobInstance.getParameter("topId");

            Collection<JGeoWikidata> allitems;
            if (!Strings.isNullOrEmpty(topId)) {
                allitems = findAllChildRecursive(topId);
            }
            else
                allitems = jGeoWikidataRepository.findAll();

            Collection<JGeoWikidata> items = new ArrayList<>();
            for(var item : allitems){
                if (!isCAlp || item.getIsCAlps() == 1){
                    items.add(item);
                }
            }


            if (countType == CountType.ONE)
                return items.stream().limit(1).collect(Collectors.toList());
            else if (countType == CountType.TEN)
                return items.stream().limit(10).collect(Collectors.toList());
            else //if (countType == CountType.ALL)
                return items;

        });
        jobBatchRunner.setJobBatchIdExtractor((_, item) -> ((JGeoWikidata) item).getJGeoWikidataId());
    }


    public String placeInfo(JGeoWikidata place, AiLogInfo aiLogInfo) {

        Map<String, Object> userTemplateMap = Map.of("place", place.getName());

        ChatClient.CallResponseSpec callResponseSpec = makeApiCall(aiLogInfo,
                aiLogInfo.aiPrompt().getSystemPromptTempl(),
                aiLogInfo.aiPrompt().getUserPromptTempl(),
                userTemplateMap,
                place.getJGeoWikidataId());
        String result = callResponseSpec.content();
        aiRequestService.postProccessAiLogInfo(aiLogInfo);
        return result;
    }

    public List<Tour> listOfTours(JGeoWikidata place, AiLogInfo aiLogInfo) {

        Map<String, Object> userTemplateMap = Map.of("place", place.getName());

        ChatClient.CallResponseSpec callResponseSpec = makeApiCall(aiLogInfo,
                aiLogInfo.aiPrompt().getSystemPromptTempl(),
                aiLogInfo.aiPrompt().getUserPromptTempl(),
                userTemplateMap,
                place.getJGeoWikidataId());

        List<Tour> result = callResponseSpec.entity(new ParameterizedTypeReference<List<Tour>>() {});
        aiRequestService.postProccessAiLogInfo(aiLogInfo);
        return result;
        //String content = chatResponse.getResult().getOutput().getContent();
        //return OUtils.readList(content, Tour.class);
    }
    private ChatClient.CallResponseSpec makeApiCall(AiLogInfo aiLogInfo,
                                                    String systemTemplate, String userTemplate,  Map<String, Object> userTemplateMap,
                                                    String itemId){
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemTemplate);
        Message systemMessage = systemPromptTemplate.createMessage();
        PromptTemplate promptTemplate = new PromptTemplate(userTemplate);
        Message userMessage = promptTemplate.createMessage(userTemplateMap);

        aiRequestService.prepareAiLogInfo(
                aiLogInfo,
                JGeoWikidata.HIKI,
                JGeoWikidata.PLACE,
                JGeoWikidata.PLACE_INFO,
                itemId
        );

        ChatClient chatClient = aiRequestService.getChatClient(aiLogInfo.aiModel());

        ChatOptions.Builder builder = ChatOptions.builder();
        builder = builder.model(aiLogInfo.aiModel().getModel());
        //builder = builder.temperature(0.8);
        ChatOptions chatOptions = builder.build();

        //Prompt prompt = new Prompt(List.of(userMessage), chatOptions);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage), chatOptions);
        //Prompt prompt = promptTemplate.create();

        ChatClient.CallResponseSpec callResponseSpec = chatClient
                .prompt(prompt)
                //.system(systemTemplate)
                .advisors(new SimpleLoggerAdvisor(
                        request -> "Custom request: " + request,
                        response -> "Custom response: " + response.getResult(),
                        2
                ), new DbLoggerAdvisor(aiLogInfo))
                .call();
        return callResponseSpec;
    }


}
