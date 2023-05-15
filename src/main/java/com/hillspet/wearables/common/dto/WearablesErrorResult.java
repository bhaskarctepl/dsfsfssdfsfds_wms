package com.hillspet.wearables.common.dto;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.CollectionUtils;

public class WearablesErrorResult {

	// Default status code for http errors
	// TODO: This should not be used as this makes it very http specific
	private Integer statusOnError = Status.INTERNAL_SERVER_ERROR.getStatusCode();

	private List<WearablesError> errorList = new ArrayList<>();

	// Default constructor in case of using bean mapper;
	public WearablesErrorResult() {

	}

	public WearablesErrorResult(Integer statusOnError, List<WearablesError> errors) {
		this.statusOnError = statusOnError;
		this.errorList = errors;
	}

	/**
	 * Additional constructors in case if the error is not associated with HTTP
	 */
	public WearablesErrorResult(Integer httpStatus, WearablesError error) {
		this.statusOnError = httpStatus;
		this.errorList.add(error);
	}

	public WearablesErrorResult(WearablesError error) {
		if (null != error) {
			this.statusOnError = error.getErrorDetail().getStatus();
			this.errorList.add(error);
		}
	}

	/**
	 * Constructors to be used when the error result is being set as part of http
	 * request-response communication
	 */
	public WearablesErrorResult(Status httpStatus, List<WearablesError> errors) {
		this.statusOnError = httpStatus.getStatusCode();
		this.errorList = errors;
	}

	public WearablesErrorResult(Status httpStatus, WearablesError error) {
		this.statusOnError = httpStatus.getStatusCode();
		this.errorList.add(error);
	}

	public Integer getStatusOnError() {
		return statusOnError;
	}

	public void setStatusOnError(Integer statusOnError) {
		this.statusOnError = statusOnError;
	}

	public List<WearablesError> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<WearablesError> errors) {
		this.errorList = errors;
	}

	public void addError(WearablesError error) {
		this.errorList.add(error);
	}

	public void addErrors(List<WearablesError> errorList) {
		if (errorList != null) {
			this.errorList.addAll(errorList);
		}
	}

	public Boolean hasErrors() {
		return CollectionUtils.isNotEmpty(this.errorList);
	}

}
