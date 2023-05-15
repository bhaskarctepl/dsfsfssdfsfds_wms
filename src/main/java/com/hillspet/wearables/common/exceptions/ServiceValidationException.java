package com.hillspet.wearables.common.exceptions;

import java.util.List;

import com.hillspet.wearables.common.dto.WearablesError;

public class ServiceValidationException extends AbstractBaseWearablesRuntimeException {

	private static final long serialVersionUID = -8942123395218681557L;

	public ServiceValidationException(String message, List<WearablesError> errors) {
		super(message);
		this.setErrors(errors);
	}

	/**
	 * @param message    - An exception message or stacktrace for the given error
	 *                   thrown by the application
	 * @param statusCode - The appropriate HTTP status code that needs to be set on
	 *                   the response to communicate the failure
	 * @param errors     - One or many Error's associated with the encountered issue
	 */
	public ServiceValidationException(String message, Integer statusCode, List<WearablesError> errors) {
		super(message);
		this.setErrors(errors);
		this.setStatus(statusCode);
	}

}
