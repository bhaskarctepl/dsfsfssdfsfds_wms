package com.hillspet.wearables.objects.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "common response for the resources created in application")
public class CommonResponse {
	private String message;

	@ApiModelProperty(value = "Gives the response message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
