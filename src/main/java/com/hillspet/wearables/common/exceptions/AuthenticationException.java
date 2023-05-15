package com.hillspet.wearables.common.exceptions;

import com.hillspet.wearables.common.dto.WearablesError;

public class AuthenticationException extends AbstractBaseWearablesRuntimeException {

	private static final long serialVersionUID = -8236730671186209390L;

	public AuthenticationException(WearablesError... errors) {
		super(errors);
	}
}
