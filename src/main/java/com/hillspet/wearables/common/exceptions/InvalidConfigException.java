package com.hillspet.wearables.common.exceptions;

import javax.ws.rs.core.Response.Status;

import com.hillspet.wearables.common.dto.WearablesError;

public class InvalidConfigException extends AbstractBaseWearablesRuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidConfigException(String message, WearablesError... errors) {
		super(message, errors);
	}

	public InvalidConfigException(String message, Status httpStatus, WearablesError... errors) {
		super(message, httpStatus, errors);
	}

	public InvalidConfigException(String message, Throwable cause, WearablesError... errors) {
		super(message, cause, errors);
	}
}
