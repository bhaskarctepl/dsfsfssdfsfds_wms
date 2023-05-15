package com.hillspet.wearables.format.validation;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;

public class RequiredValDataHolder<T> extends ValidationDataHolder<T> {

	public RequiredValDataHolder(T data, ErrorDetailInterface errorCode) {
		super(data, errorCode, FormatValType.REQUIRED);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequiredValDataHolder [");
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}

}
