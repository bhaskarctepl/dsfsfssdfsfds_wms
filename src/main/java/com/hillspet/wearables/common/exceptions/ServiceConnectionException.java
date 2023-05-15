package com.hillspet.wearables.common.exceptions;

import java.util.Arrays;

import com.hillspet.wearables.common.dto.WearablesError;

/**
 * Any exceptions related to Connection issues between services must be handled
 * here
 * 
 * @author vvodyaram
 *
 */
public class ServiceConnectionException extends AbstractBaseWearablesRuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceConnectionException(String message, WearablesError error) {
		super(message);
		this.setErrors(Arrays.asList(error));
		this.setStatus(error.getErrorDetail().getStatus());
	}

}
