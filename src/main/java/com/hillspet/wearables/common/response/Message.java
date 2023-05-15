package com.hillspet.wearables.common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * This API contains key information that can be used to determine the service
 * outcome
 * 
 * @author vvodyaram
 *
 */
@ApiModel(description = "Details of the response from WEARABLES")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

	/**
	 * It holds the error code
	 */
	@ApiModelProperty(value = "A distinct code defined by application per error condition", required = true, example = "WEARABLES_REQ_001")
	@JsonProperty("code")
	private String code;

	/**
	 * It holds the description for the error code
	 */
	@ApiModelProperty(value = "Internationalized message providing a brief description of the error condition", required = true, example = "WEARABLES_APP_ID is invalid")
	@JsonProperty("message")
	private String message;

	/**
	 * a key that would be used by other systems to map the response to the
	 * corresponding handler/component
	 */

	@ApiModelProperty(value = "A distinct key that can be used by consuming application for mapping the WEARABLES error code to client side", required = true, example = "request.header.WEARABLES_APP_ID.invalid")
	@JsonProperty("key")
	private String key;

	public Message() {

	}

	public Message(String code, String message, String key) {
		this.code = code;
		this.message = message;
		this.key = key;
	}

	// setters and getters

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
