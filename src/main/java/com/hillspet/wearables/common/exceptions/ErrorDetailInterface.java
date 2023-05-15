package com.hillspet.wearables.common.exceptions;

import com.hillspet.wearables.common.constants.MessageKeyInterface;

/**
 * 
 * @author hrangwal
 *
 */
public interface ErrorDetailInterface {

	/**
	 * Application specific error codes i.e WEARABLES_APP_001
	 * 
	 * @return
	 */
	public String getErrorCode();

	/**
	 * A user friendly error key representing the error scenario
	 * 
	 * @return
	 */
	public MessageKeyInterface getKey();

	/**
	 * Severity of the error that happened with a Severity Handler to handle it
	 * 
	 * @return
	 */
	public SeverityInterface getSeverity();

	/**
	 * A status value to represent the state. In case of a web request this could be
	 * the Http Status Code to be returned to the caller
	 * 
	 * @return
	 */
	public Integer getStatus();

	public String getErrorMessage();

}
