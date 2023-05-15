package com.hillspet.wearables.common.alerting;

import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.exceptions.InvalidConfigException;
import com.hillspet.wearables.common.exceptions.SeverityInterface;

/**
 * A handler that will handle the severity encountered in the application
 * 
 * @author vvodyaram
 *
 */
public class SeverityHandler {

	protected static final Logger LOGGER = LogManager.getLogger(SeverityHandler.class);

	/**
	 * To handle any error/exceptions that happened in the system based on the
	 * severity defined
	 * 
	 * @param t     - The exception
	 * @param error - Error which has the details
	 */
	public void handle(Throwable t, @NotNull WearablesError error) {
		ErrorDetailInterface errorCodeObj = error.getErrorDetail();
		if (errorCodeObj != null) {
			SeverityInterface severityObj = error.getErrorDetail().getSeverity();
			if (severityObj != null) {
				Integer severity = severityObj.getSeverity();
				if (t == null) {
					handleError(severity, error);
				} else {
					handleError(severity, error, t);
				}
			} else {
				handleSystemError("SeverityInterface is null, check the ErrorCode definition for " + errorCodeObj
						+ ", errorCode = " + errorCodeObj.getErrorCode() + "& make sure a Severity is defined");
			}
		} else {
			handleSystemError("ErrorCodeInterface is null, make sure an ErrorCode is defined for error " + error);
		}
	}

	/**
	 * Overriding classes can override the below method to provide specific
	 * implementation to handle error based on severity
	 * 
	 * @param severity
	 * @param error
	 * @param t
	 */
	public void handleError(Integer severity, WearablesError error, Throwable t) {
		LOGGER.error(
				"A Severity {} error has occurred, error code is {}, invalid values are {}, exception stacktrace is {} ",
				severity, error.getErrorDetail(), error.getValues(), t.getMessage());
	}

	/**
	 * Overriding classes can override the below method to provide specific
	 * implementation to handle error based on severity
	 * 
	 * @param severity
	 * @param error
	 */
	public void handleError(Integer severity, WearablesError error) {
		LOGGER.error("A Severity {} error has occurred, error code is {}, invalid values are {}", severity,
				error.getErrorDetail(), error.getValues());
	}

	private void handleSystemError(String message) {
		throw new InvalidConfigException(message, new WearablesError(WearablesErrorCode.WEARABLES_SYSTEM_ERROR));
	}

}
