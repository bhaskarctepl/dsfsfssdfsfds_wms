package com.hillspet.wearables.format.validation;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;

public class PatternMatchValDataHolder extends ValidationDataHolder<String> {
		
	private String regex;			

	public PatternMatchValDataHolder(String data, String regex, ErrorDetailInterface errorCode) {
		super(data, errorCode, FormatValType.PATTERN_MATCH);
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PatternMatchValDataHolder [regex=");
		builder.append(regex);
		builder.append(super.toString());
		builder.append("]");
		return builder.toString();
	}
}
