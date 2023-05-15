package com.hillspet.wearables.common.validation;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.CollectionUtils;

import com.hillspet.wearables.common.dto.Warning;
import com.hillspet.wearables.common.dto.WearablesErrorResult;

public class ValidationResult extends WearablesErrorResult {

	private Boolean proceed = Boolean.TRUE;

	public ValidationResult() {
		this.setStatusOnError(Status.BAD_REQUEST.getStatusCode());
	}

	/**
	 * Holds warnings information
	 */
	private List<Warning> warningList = new ArrayList<Warning>();

	public List<Warning> getWarningList() {
		return warningList;
	}

	public Boolean hasNoErrors() {
		return !hasErrors();
	}

	public Boolean hasWarnings() {
		return CollectionUtils.isNotEmpty(this.getWarningList());
	}

	public void addWarning(Warning warning) {
		this.warningList.add(warning);
	}

	public Boolean isProceed() {
		return proceed;
	}

	public void setProceed(Boolean proceed) {
		this.proceed = proceed;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValidationResult [hasErrors=");
		builder.append(hasErrors());
		builder.append(", hasWarnings=");
		builder.append(hasWarnings());
		builder.append(", proceed=");
		builder.append(proceed);
		builder.append(", httpStatusOnError=");
		builder.append(getStatusOnError());
		builder.append(", errors =");
		builder.append(getErrorList());
		builder.append(", warningList=");
		builder.append(warningList);
		builder.append("]");
		return builder.toString();
	}

}
