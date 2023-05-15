package com.hillspet.wearables.format.validation;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;

public abstract class ValidationDataHolder<T> {
	protected T data;
	
	protected ErrorDetailInterface errorCode;
	
	// Making the default to be false as per requirement, this is so that we stop at the very first error
	protected boolean isProceedOnError = false;
	
	protected FormatValType formatValType;
	
	public enum FormatValType {
		REQUIRED(1),
		LENGTH(2),
		PATTERN_MATCH(3),
		EQUALS(4),
		CONTAINS(5);
		
		public int priority = 0;
		
		private FormatValType(int priority) {
			this.priority = priority;
		}
		
		public int getPriority() {
			return this.priority;
		}
	}
		
	public ValidationDataHolder(T data, ErrorDetailInterface errorCode, boolean isProceedOnError, FormatValType formatValType) {
		super();
		this.data = data;
		this.errorCode = errorCode;
		this.isProceedOnError = isProceedOnError;
		this.formatValType = formatValType;
	}
	
	public ValidationDataHolder(T data, ErrorDetailInterface errorCode, FormatValType formatValType) {
		super();
		this.data = data;
		this.errorCode = errorCode;
		this.formatValType = formatValType;
	}


	public ValidationDataHolder(T data, FormatValType formatValType) {
		super();
		this.data = data;
		this.formatValType = formatValType;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public ErrorDetailInterface getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorDetailInterface errorCode) {
		this.errorCode = errorCode;
	}


	public FormatValType getFormatValType() {
		return formatValType;
	}

	public void setFormatValType(FormatValType formatValType) {
		this.formatValType = formatValType;
	}
	
	public int getPriority() {
		return getFormatValType().getPriority();
	}

	@Override
	public String toString() {
		return "ValidationDataHolder [data=" + data + ", errorCode=" + errorCode + ", isProceedOnError="
				+ isProceedOnError + ", formatValType=" + formatValType + "]";
	}

}
