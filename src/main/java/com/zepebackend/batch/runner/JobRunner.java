//BY MOMATH NDIAYE

package com.zepebackend.batch.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobRunner {
    private static final Logger logger = LoggerFactory.getLogger(JobRunner.class);
    private final JobLauncher jobLauncher;
    private final Job job;

    public JobRunner(JobLauncher jobLauncher, Job job) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    public BatchStatus load() {
        Map<String, JobParameter> parametersMap = new HashMap<>();
        parametersMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(parametersMap);
        JobExecution jobExecution = null;
        try {
            jobExecution = jobLauncher.run(job, jobParameters);
            while (jobExecution.isRunning()) {
                logger.info("......");
            }
            return jobExecution.getStatus();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return BatchStatus.FAILED;

    }
}
