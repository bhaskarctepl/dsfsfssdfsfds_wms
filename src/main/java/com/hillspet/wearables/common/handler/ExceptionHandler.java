package com.hillspet.wearables.common.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.alerting.SeverityHandler;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.exceptions.InvalidConfigException;
import com.hillspet.wearables.common.exceptions.SeverityInterface;

/**
 * A central place in the application to handle all exceptions that are
 * encountered This will be called by the appropriate ExceptionMapper class
 * refer {@link com.hillspet.wearables.exception.mappers}
 * 
 * @author vvodyaram
 *
 */
@Component
public class ExceptionHandler {

	/**
	 * Method that handles the exception by taking appropriate action on each error
	 * encountered
	 * 
	 * @param t
	 * @param errors
	 */
	public void handleException(Throwable t, List<WearablesError> errors) {
		// for each error, take an action based on its severity
		errors.forEach(error -> {
			this.handleException(t, error);
		});
	}

	/**
	 * Calls the Severity Handler to handle the error as per the severity defined
	 * 
	 * @param t
	 * @param error
	 */
	public void handleException(Throwable t, WearablesError error) {
		ErrorDetailInterface errorCodeObj = error.getErrorDetail();
		SeverityInterface severityObj = error.getErrorDetail().getSeverity();
		if (severityObj == null) {
			handleSystemError("SeverityInterface is null, check the ErrorCode definition for " + errorCodeObj
					+ ", errorCode = " + errorCodeObj.getErrorCode() + "& make sure a Severity is defined");
		}
		SeverityHandler severityHandler = severityObj.getHandler();
		if (severityHandler == null) {
			handleSystemError("SeverityHandler is null, check the ErrorCode definition for " + errorCodeObj
					+ ", errorCode = " + errorCodeObj.getErrorCode() + "& make sure a SeverityHandler is defined");
		}
		severityHandler.handle(t, error);
	}

	private void handleSystemError(String message) {
		throw new InvalidConfigException(message, new WearablesError(WearablesErrorCode.WEARABLES_SYSTEM_ERROR));
	}

}
