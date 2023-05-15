package com.hillspet.wearables.common.constants;

/**
 * This enum contains all flag strings which can be used across the application
 * 
 * @author vvodyaram
 *
 */
public enum Flags implements EnumInterface<String> {
	YES("YES"),
	NO("NO"),
	Y("Y"),
	N("N"),
	TRUE("TRUE"),
	FALSE("FALSE"),
	T("T"),
	F("F");
	private String flag;

	private Flags(String flag) {
		this.flag = flag;
	}

	@Override
	public String getCode() {
		return this.flag;
	}
}
