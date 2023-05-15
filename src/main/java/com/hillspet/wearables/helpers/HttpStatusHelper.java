package com.hillspet.wearables.helpers;

import javax.ws.rs.core.Response.Status;

import org.apache.http.HttpStatus;

/**
 * A helper class to get the correct JAX-RS STatus corresponding to the issue
 * encountered within the application. This class should not be made a spring
 * component as this would be used by Exception classes & it would not make
 * sense to make Exception classes spring enabled
 * 
 * @author vvodyaram
 *
 */
public class HttpStatusHelper {

	public static Status getConstraintViolationErrorStatus() {
		return Status.BAD_REQUEST;
	}

	public static Status getDefaultServerError() {
		return Status.INTERNAL_SERVER_ERROR;
	}

	/**
	 * Return success boolean value. If input status is HttpStatus.SC_OK, then
	 * returns true else false.
	 * 
	 * @param status
	 * @return
	 */
	public static Boolean getSuccessValue(Integer status) {
		return status == HttpStatus.SC_OK ? Boolean.TRUE : Boolean.FALSE;
	}

}
