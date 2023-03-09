//BY MOMATH NDIAYE

package com.zepebackend.batch.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facture")
public class FactureRestController {
	private static final Logger logger = LoggerFactory.getLogger(FactureRestController.class);

	private final JobLauncher jobLauncher;
	private final Job job;

	public FactureRestController(JobLauncher jobLauncher, Job job) {
		this.job = job;
		this.jobLauncher = jobLauncher;
	}

	@GetMapping("/loadData")
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
