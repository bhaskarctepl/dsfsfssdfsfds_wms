package com.hillspet.wearables.common.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Class that contains the attributes for an Error response
 * @author vvodyaram
 *
 */
@ApiModel(description = "Properties part of error response returned from the service", value = "errorResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse extends BaseJsonResponse{
	@ApiModelProperty(value = "Determines if the operation resulted in success or failure", required = true, allowableValues = "true, false")
	private Boolean success = Boolean.FALSE;
	
	@ApiModelProperty(value = "HTTP Status Code", required = true)
	private Integer httpStatus = HttpStatus.SC_INTERNAL_SERVER_ERROR;

    @ApiModelProperty(value = "List of error's returned by the service", required = false)
	@JsonInclude(value = Include.NON_EMPTY)
    @JsonProperty("errors")
    private List<Message> errors = new ArrayList<Message>();

	public List<Message> getErrors() {
		return errors;
	}

	public void setErrors(List<Message> errors) {
		this.errors = errors;
	}
	
	public void addErrorMessage(Message message) {
		errors.add(message);
	}
	
	@Override
	public ResponseStatus getStatus() {
		if(this.status == null) {
			this.status = new ResponseStatus(success, httpStatus);
		}		
		return this.status;
	}

}
