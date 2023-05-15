package com.hillspet.wearables.format.validation;

import java.util.Arrays;
import java.util.List;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;

public class EqualsValDataHolder<T> extends ValidationDataHolder<T> {
	private List<T> allowedValues;
	

	public EqualsValDataHolder(T data, T allowedValue, ErrorDetailInterface errorCode) {
		super(data, errorCode, FormatValType.EQUALS);
		this.allowedValues = Arrays.asList(allowedValue);
	}
	
	public EqualsValDataHolder(T data, List<T> allowedValues, ErrorDetailInterface errorCode) {
		super(data, errorCode, FormatValType.EQUALS);
		this.allowedValues = allowedValues;
	}

	public EqualsValDataHolder(T data, T allowedValue) {
		super(data, FormatValType.EQUALS);
		this.allowedValues = Arrays.asList(allowedValue);
	}

	public EqualsValDataHolder(T data, List<T> allowedValues) {
		super(data, FormatValType.EQUALS);
		this.allowedValues = allowedValues;
	}

	public List<T> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(List<T> allowedValues) {
		this.allowedValues = allowedValues;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();		
		builder.append("EqualsValDataHolder [allowedValues=");
		builder.append(allowedValues);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
