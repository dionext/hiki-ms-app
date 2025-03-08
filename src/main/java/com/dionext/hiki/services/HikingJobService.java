package com.dionext.hiki.services;

import com.dionext.job.*;
import com.dionext.job.entity.JobInstance;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HikingJobService extends BaseJobService implements JobService {

    @Autowired
    private HikingAIService hikingAIService;

    @PostConstruct
    void postConstruct() {
        jobManager.addJobType("placeInfo", "Place information");
        jobManager.addJobType("listOfTours", "List of tours");
    }

    public String createRunJobParameters(String jobTypeId, JobInstance jobInstance, boolean readOnly) {
        if (jobTypeId == null && jobInstance != null) jobTypeId = jobInstance.getJobTypeId();
        StringBuilder str = new StringBuilder();
        str.append(hikingAIService.createRunJobParameters(jobTypeId, jobInstance, readOnly));
        return str.toString();
    }


    public JobRunner createJob(JobInstance _jobInstance) throws Exception {
        String jobTypeId = _jobInstance.getJobTypeId();
        JobRunner jobRunner = hikingAIService.createJob(jobTypeId, _jobInstance);
        return jobRunner;
    }

}
