package com.hillspet.wearables.common.constants;

/**
 * Constants that represents the different states in which a request can be based 
 * on the authorization token supplied in the incoming request header.
 * @author vvodyaram
 *
 */
public enum AuthenticationStatus implements EnumInterface<String>{

	GRANTED_AUTHENTICATED("GRANTED_AUTHENTICATED"),
	GRANTED_AUTH_NOT_REQD("GRANTED_AUTHENTICATION_NOT_REQUIRED"),
	REJECTED_ACCESS_DENIED("REJECTED_ACCESS_DENIED"),
	NO_AUTH("NO_AUTHENTICATION_PERFORMED");
	
	private String code;

	private AuthenticationStatus(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}
}
