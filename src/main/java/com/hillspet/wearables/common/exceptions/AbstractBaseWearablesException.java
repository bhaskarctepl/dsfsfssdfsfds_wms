package com.hillspet.wearables.common.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.helpers.HttpStatusHelper;

/**
 * Application specific checked exceptions that the application might want to
 * catch or throw must extend from this class {@code BaseWEARABLESException} This
 * class is a form of {@code Throwable} that extends {@code Exception} &
 * provides additional methods to get the application defined errors
 * 
 * @author vvodyaram
 *
 */

public abstract class AbstractBaseWearablesException extends Exception {

	private static final long serialVersionUID = 1003235922876636916L;

	// We instantiate the error list so that there is never a condition where there
	// is no error set on the exception object
	protected List<WearablesError> errors = new ArrayList<>();

	protected Integer status;

	/**
	 * Providing a no-arg constructor & supplying a default Error object as we
	 * always want to be able to map a error with an exception
	 */
	public AbstractBaseWearablesException() {
		this(new WearablesError(WearablesErrorCode.WEARABLES_SYSTEM_ERROR));
	}

	/**
	 * @param errors - One or many Error's associated with the encountered issue
	 */
	public AbstractBaseWearablesException(WearablesError... errors) {
		this.errors = Arrays.asList(errors);
	}

	/**
	 * @param message - An exception message or stacktrace for the given error
	 *                thrown by the application
	 * @param errors  - One or many Error's associated with the encountered issue
	 */
	public AbstractBaseWearablesException(String message, WearablesError... errors) {
		super(message);
		this.errors = Arrays.asList(errors);
	}

	/**
	 * @param message    - An exception message or stacktrace for the given error
	 *                   thrown by the application
	 * @param httpStatus - The appropriate HTTP status code that needs to be set on
	 *                   the response to communicate the failure
	 * @param errors     - One or many Error's associated with the encountered issue
	 */
	public AbstractBaseWearablesException(String message, Status httpStatus, WearablesError... errors) {
		super(message);
		this.errors = Arrays.asList(errors);
		this.status = httpStatus.getStatusCode();
	}

	/**
	 * @param message    - An exception message or stacktrace for the given error
	 *                   thrown by the application
	 * @param httpStatus - The appropriate status code that needs to be set on the
	 *                   response to communicate the failure
	 * @param errors     - One or many Error's associated with the encountered issue
	 */
	public AbstractBaseWearablesException(String message, Integer status, WearablesError... errors) {
		super(message);
		this.errors = Arrays.asList(errors);
		this.status = status;
	}

	/**
	 * @param message - An exception message or stacktrace for the given error
	 *                thrown by the application
	 * @param cause   - A throwable cause that has more details about the exception
	 *                encountered
	 * @param errors  - One or many Error's associated with the encountered issue
	 */
	public AbstractBaseWearablesException(String message, Throwable cause, WearablesError... errors) {
		super(message, cause);
		this.errors = Arrays.asList(errors);
	}

	public AbstractBaseWearablesException(String message, Throwable cause, Status httpStatus, WearablesError... errors) {
		super(message, cause);
		this.errors = Arrays.asList(errors);
		this.status = httpStatus.getStatusCode();
	}

	public AbstractBaseWearablesException(String message, Throwable cause, Integer status, WearablesError... errors) {
		super(message, cause);
		this.errors = Arrays.asList(errors);
		this.status = status;
	}

	public AbstractBaseWearablesException(Throwable cause, WearablesError... errors) {
		super(cause);
		this.errors = Arrays.asList(errors);
	}

	public AbstractBaseWearablesException(Throwable cause, Status httpStatus, WearablesError... errors) {
		super(cause);
		this.errors = Arrays.asList(errors);
		this.status = httpStatus.getStatusCode();
	}

	public AbstractBaseWearablesException(Throwable cause, Integer status, WearablesError... errors) {
		super(cause);
		this.errors = Arrays.asList(errors);
		this.status = status;
	}

	public void addError(WearablesError error) {
		errors.add(error);
	}

	public void setErrors(List<WearablesError> errors) {
		this.errors = errors;
	}

	/**
	 * Returns the list of Error's, if no Error is present then a default Error is
	 * returned
	 * 
	 * @return
	 */
	public List<WearablesError> getErrors() {
		if (errors.size() == 0) {
			errors.add(new WearablesError(WearablesErrorCode.WEARABLES_SYSTEM_ERROR));
		}
		return errors;
	}

	/**
	 * Returns the HttpStatus set. If no HttoStatus is set then a default status is
	 * returned
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return (status == null ? HttpStatusHelper.getDefaultServerError().getStatusCode() : status);
	}

	public void setStatus(Status httpStatus) {
		this.status = httpStatus.getStatusCode();
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BaseWEARABLESException [errors=" + errors + ", httpStatus=" + status + "]";
	}

}
