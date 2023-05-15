package com.hillspet.wearables.common.dto;

/**
 * Contains the information regarding the Auth token set in the request header
 * @author hrangwal
 *
 */
public class AuthToken{
	/**
	 * Raw JWT as passed by the gateway component
	 */
	private String encodedJwt;
	
	private TokenHeader header;
	
	private TokenPayload payload;

	
	public AuthToken(TokenHeader header, TokenPayload payload) {
		super();
		this.header = header;
		this.payload = payload;
	}
	
	public AuthToken(String encodedJwt) {
		super();
		this.encodedJwt = encodedJwt;
	}

	
	public TokenHeader getHeader() {
		return header;
	}

	public void setHeader(TokenHeader header) {
		this.header = header;
	}

	public TokenPayload getPayload() {
		return payload;
	}

	public void setPayload(TokenPayload payload) {
		this.payload = payload;
	}
	
	public String getEncodedJwt() {
		return encodedJwt;
	}


	public void setEncodedJwt(String encodedJwt) {
		this.encodedJwt = encodedJwt;
	}


	@Override
	public String toString() {
		return "AuthToken [header=" + header + ", payload=" + payload + "]";
	}
	
}
