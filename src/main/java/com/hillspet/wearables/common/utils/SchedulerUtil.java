package com.hillspet.wearables.common.utils;

import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerUtil {

	private static final Logger LOGGER = LogManager.getLogger(SchedulerUtil.class);

	@Scheduled(cron = "0 0 0/6 1/1 * ?")
	public void exampleJob() {
		long currentTimeMillis = System.currentTimeMillis();
		LOGGER.info("exampleJob started");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		long timeTaken = System.currentTimeMillis() - currentTimeMillis;
		LOGGER.info("exampleJob completed");
		LOGGER.info("Time taken to exceute exampleJob is {} milliSeconds", timeTaken);
	}

}
