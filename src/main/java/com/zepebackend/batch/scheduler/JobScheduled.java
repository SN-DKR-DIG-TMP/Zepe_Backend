// BY MOMATH NDIAYE

package com.zepebackend.batch.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import com.zepebackend.batch.runner.JobRunner;
import com.zepebackend.utils.ZepeConstants;
import com.zepebackend.utils.ZepeUtils;

@Configuration
public class JobScheduled {
	private static final Logger logger = LoggerFactory.getLogger(JobScheduled.class);
	private final JobRunner jobRunner;

	public JobScheduled(JobRunner jobRunner) {
		this.jobRunner = jobRunner;
	}

	@Scheduled(cron = "0 00 01 01 * ?") // Execution à 01h 00 les premiers de
	// cron = "0 00 01 01 * ?") // Execution à 01h 00 les premiers de chaque mois
	// cron = "0 */01 * * * ?" Chaque minute 
	// @Scheduled(cron = "0 0/1 * 1/1 * ?")
	public void jobScheduled() {
		logger.info("Job lancé à " + new Date() + "  " + ZepeUtils.debutFinDuMois().get(ZepeConstants.START) + " "
				+ ZepeUtils.debutFinDuMois().get(ZepeConstants.END));
		jobRunner.load();
	}
}
