package com.hillspet.wearables.common.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* As the name implies this class contains the attributes part of the JSON response that will be returned back to the caller
*
* @author vvodyaram
*  
*/
public abstract class BaseJsonResponse {

	@JsonProperty("status")
    public ResponseStatus status;

    @JsonInclude(value = Include.NON_EMPTY)
    private List<Message> warnings = new ArrayList<Message>();

    
	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public List<Message> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<Message> warnings) {
		this.warnings = warnings;
	}
	

	public void addWarningMessage(Message message) {
		warnings.add(message);
	}

	@Override
	public String toString() {
		return "BaseJsonResponse [status=" + status + ", warnings=" + warnings + "]";
	}
    
}
