package com.hillspet.wearables.common.exceptions;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.dto.WearablesErrorResult;

public class ServiceExecutionException extends AbstractBaseWearablesRuntimeException {
	/*
	 * For a failure at Service level, we need -
	 * 
	 * 1. HTTP Status code (optional) 2. Error(s) object (optional) 3. WEARABLES Error
	 * Code - Invalid/Erroneous values 4. Error Message String (optional) 5.
	 * Throwable (optional) - The actual exception wrapped in this
	 * 
	 */

	private static final long serialVersionUID = 6891668548766139141L;

	public ServiceExecutionException(String message) {
		super(message);
	}

	public ServiceExecutionException(String message, Throwable cause) {
		super(message, cause);

	}

	public ServiceExecutionException(String message, Integer statusCode, Throwable cause) {
		super(message, cause);
		Status status = Status.fromStatusCode(statusCode);
		if (status != null) {
			this.setStatus(status);
		}

	}

	public ServiceExecutionException(String message, Status status, Throwable cause) {
		super(message, cause);
		this.setStatus(status);

	}

	public ServiceExecutionException(String message, Status status, List<WearablesError> errors) {
		super(message);
		this.setErrors(errors);
		this.setStatus(status);
	}

	public ServiceExecutionException(String message, Integer status, List<WearablesError> errors) {
		super(message);
		this.setErrors(errors);
		this.setStatus(status);
	}

	public ServiceExecutionException(String message, WearablesError error) {
		super(message);
		this.setStatus(error.getErrorDetail().getStatus());
		this.errors = Arrays.asList(error);
	}

	public ServiceExecutionException(String message, WearablesErrorResult error) {
		super(message);
		this.setStatus(error.getStatusOnError());
		this.errors = error.getErrorList();
	}

}
