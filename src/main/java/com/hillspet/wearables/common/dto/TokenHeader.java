package com.hillspet.wearables.common.dto;

public class TokenHeader {

	private String algorithm;

	private String type;

	public TokenHeader(String algorithm, String type) {
		super();
		this.algorithm = algorithm;
		this.type = type;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "TokenHeader [algorithm=" + algorithm + ", type=" + type + "]";
	}

}
