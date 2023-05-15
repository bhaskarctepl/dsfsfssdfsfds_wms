package com.hillspet.wearables.common.response;

import org.apache.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Class that contains attributes for a successful response
 * @author vvodyaram
 *
 */
@ApiModel(description = "Properties part of successful response returned from the service", value = "successResponse")
public class SuccessResponse<T> extends BaseJsonResponse {
	@ApiModelProperty(value = "Determines if the operation resulted in success or failure", required = true, allowableValues = "true, false")
	private Boolean success = Boolean.TRUE;
	
	@ApiModelProperty(value = "HTTP Status Code", required = true)
	private Integer httpStatus = HttpStatus.SC_OK;
	
	private T response;

	/**
	 * @return response
	 */
	@JsonInclude(value = Include.NON_EMPTY)
	@JsonProperty("response")
	public T getServiceResponse() {
		return response;
	}

	/**
	 * @param serviceResponse
	 */
	public void setServiceResponse(T serviceResponse) {
		this.response = serviceResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.hillspet.wearables.response.BaseJsonResponse#getOrderStatus()
	 */
	@Override
	public ResponseStatus getStatus() {
		if(this.status == null) {
			this.status = new ResponseStatus(success, httpStatus);
		}		
		return this.status;
	}
	
}
