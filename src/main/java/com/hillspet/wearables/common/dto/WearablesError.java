package com.hillspet.wearables.common.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;

/**
 * This class should be used whenever any abnormal condition is encountered
 * within the application & the cause for the issue needs to be communicated
 * back to the caller.
 * 
 * Object of this class can be instantiated in error, warning & exception
 * conditions as long as the application defined ErrorCode (required) and the
 * invalid values (optional) that resulted in the error is provided.
 * 
 * 
 * @author vvodyaram
 *
 */
public class WearablesError {

	private ErrorDetailInterface errorDetail;

	private List<Object> values;

	public WearablesError(ErrorDetailInterface code, Object... values) {
		super();
		this.errorDetail = code;
		this.values = (values != null ? Arrays.asList(values) : new ArrayList<>());
	}

	public ErrorDetailInterface getErrorDetail() {
		return errorDetail;
	}

	public void setErrorCode(ErrorDetailInterface code) {
		this.errorDetail = code;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Error [errorDetail=" + errorDetail + ", values=" + values + "]";
	}

}
