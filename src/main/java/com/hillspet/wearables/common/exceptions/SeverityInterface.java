package com.hillspet.wearables.common.exceptions;

import com.hillspet.wearables.common.alerting.SeverityHandler;

/**
 * 
 * @author hrangwal
 *
 */
public interface SeverityInterface {

	/**
	 * Define severity for this error so that the gravity of the situation could be
	 * understood and handled if necessary
	 * 
	 * @return
	 */
	public Integer getSeverity();

	/**
	 * A handler to attempt to handle the error
	 * 
	 * @return
	 */
	public SeverityHandler getHandler();
}
