package com.hillspet.wearables.common.response;

/**
 * This holds the information that will specify the status of the service call i.e Success or failure & also the
 * HTTP status code that can be used within the calling application to determine the state of the response.
 * 
 * @author vvodyaram
 *
 */
public class ResponseStatus {

    /**
     * Success will hold the service response status. <br/>
     * Ex: TRUE or FALSE
     */

    private Boolean success;

    /**
     * Holds service status code. <br/>
     * Ex: 200 or 500 or 400
     */
    private Integer httpStatus;
    
    public ResponseStatus(Boolean success, Integer httpStatus) {
		super();
		this.success = success;
		this.httpStatus = httpStatus;
	}
    
	public ResponseStatus() {
		super();
	}

	public Boolean getSuccess() {
		return success;
	}

	

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}
}
