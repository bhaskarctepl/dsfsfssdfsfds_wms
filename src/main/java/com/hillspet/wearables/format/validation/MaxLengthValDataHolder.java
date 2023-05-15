package com.hillspet.wearables.format.validation;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;

public class MaxLengthValDataHolder extends ValidationDataHolder<String> {
		
	private Integer maxLength;
	
	private Integer minLength;
	
	public MaxLengthValDataHolder(String data, Integer maxLength, ErrorDetailInterface errorCode) {
		super(data, errorCode, FormatValType.LENGTH);
		this.maxLength = maxLength;
	}
	
	public MaxLengthValDataHolder(String data, Integer minLength, Integer maxLength, ErrorDetailInterface errorCode) {
		super(data, errorCode, FormatValType.LENGTH);
		this.maxLength = maxLength;
		this.minLength = minLength;
	}
	
	

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MaxLengthValDataHolder [maxLength=");
		builder.append(maxLength);
		builder.append(", minLength=");
		builder.append(minLength);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
