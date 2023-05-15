package com.hillspet.wearables.format.validation;

import java.util.Collection;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;

public class ContainsValDataHolder<T> extends ValidationDataHolder<T> {
	protected T allowedValue;
	
	protected Collection<T> allowedValues;
	
	protected Collection<T> inputData;

	public ContainsValDataHolder(Collection<T> inputData, T allowedValue, ErrorDetailInterface errorCode) {
		super(null, errorCode, FormatValType.CONTAINS);
		this.inputData = inputData;
		this.allowedValue = allowedValue;
	}
	
	public ContainsValDataHolder(Collection<T> inputData, Collection<T> allowedValues, ErrorDetailInterface errorCode) {
		super(null, errorCode, FormatValType.CONTAINS);
		this.inputData = inputData;
		this.allowedValues = allowedValues;
	}
	
	

	public T getAllowedValue() {
		return allowedValue;
	}

	public void setAllowedValue(T allowedValue) {
		this.allowedValue = allowedValue;
	}

	public Collection<T> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(Collection<T> allowedValues) {
		this.allowedValues = allowedValues;
	}

	public Collection<T> getInputData() {
		return inputData;
	}

	public void setData(Collection<T> inputData) {
		this.inputData = inputData;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ContainsValDataHolder [allowedValue=");
		builder.append(allowedValue);
		builder.append(", allowedValues=");
		builder.append(allowedValues);
		builder.append(", inputData=");
		builder.append(inputData);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
	
}
