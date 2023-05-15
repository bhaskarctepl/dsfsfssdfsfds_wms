package com.hillspet.wearables.common.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A container to hold the collection of the ErrorDetail that can be used by
 * application & fetch the ErrorDetail based on the error code.
 * 
 * This is not decorated as a Spring component but consuming application must
 * define a Spring bean to populate this container with ErrorCodes that
 * implement the ErrorDetailInterface
 * 
 * @author hrangwal
 *
 */
public class ErrorDetailBean {

	private String appId;

	private List<ErrorDetailInterface> errorDetails = new ArrayList<>();

	private Map<String, ErrorDetailInterface> errorDetailsMap = new HashMap<>();

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public List<ErrorDetailInterface> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<ErrorDetailInterface> errorDetails) {
		errorDetails.forEach(errorDetail -> {
			this.errorDetailsMap.put(errorDetail.getErrorCode(), errorDetail);
		});
		this.errorDetails.addAll(errorDetails);

	}

	public void addErrorDetail(ErrorDetailInterface errorDetail) {
		this.errorDetails.add(errorDetail);
		this.errorDetailsMap.put(errorDetail.getErrorCode(), errorDetail);
	}

	public Optional<ErrorDetailInterface> getErrorDetail(final String errorCode) {
		ErrorDetailInterface errorDetail = this.errorDetailsMap.get(errorCode);
		return Optional.ofNullable(errorDetail);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorDetailBean [appId=");
		builder.append(appId);
		builder.append(", errorDetailsMap=");
		builder.append(errorDetailsMap);
		builder.append("]");
		return builder.toString();
	}

}
