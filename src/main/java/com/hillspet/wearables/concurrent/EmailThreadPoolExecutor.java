package com.hillspet.wearables.concurrent;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.hillspet.wearables.common.constants.Constants;

public class EmailThreadPoolExecutor {
	private EmailThreadPoolExecutor() {

	}

	private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Constants.APP_INDEX_ONE,
			Constants.APP_INDEX_FIVE, 5000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(),
			new ThreadPoolExecutor.CallerRunsPolicy());

	public static ThreadPoolExecutor getEmailThreadPoolExecutor() {
		return threadPoolExecutor;
	}
}