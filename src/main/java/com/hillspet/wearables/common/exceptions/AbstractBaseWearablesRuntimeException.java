package com.hillspet.wearables.common.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.helpers.HttpStatusHelper;

/**
 * Application specific unchecked exceptions that the application might want to
 * throw must extend from this class {@code BaseWEARABLESRuntimeException} This class
 * is a form of {@code Throwable} that extends {@code RuntimeException} &
 * provides additional methods to get the application defined error code's
 * 
 * @author vvodyaram
 *
 */

public abstract class AbstractBaseWearablesRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	protected List<WearablesError> errors = new ArrayList<>();

	protected Integer status;

	/**
	 * No parameterized constructor. It will be useful to create object by the
	 * spring container.
	 */
	public AbstractBaseWearablesRuntimeException() {
		super("");
	}

	/**
	 * @param errors - One or many Error's associated with the encountered issue
	 */
	public AbstractBaseWearablesRuntimeException(WearablesError... errors) {
		this.errors = Arrays.asList(errors);
	}

	/**
	 * @param message - An exception message or stacktrace for the given error
	 *                thrown by the application
	 * @param errors  - One or many Error's associated with the encountered issue
	 */
	public AbstractBaseWearablesRuntimeException(String message, WearablesError... errors) {
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
	public AbstractBaseWearablesRuntimeException(String message, Status httpStatus, WearablesError... errors) {
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
	public AbstractBaseWearablesRuntimeException(String message, Integer status, WearablesError... errors) {
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
	public AbstractBaseWearablesRuntimeException(String message, Throwable cause, WearablesError... errors) {
		super(message, cause);
		this.errors = Arrays.asList(errors);
	}

	public AbstractBaseWearablesRuntimeException(String message, Throwable cause, Status httpStatus, WearablesError... errors) {
		super(message, cause);
		this.errors = Arrays.asList(errors);
		this.status = httpStatus.getStatusCode();
	}

	public AbstractBaseWearablesRuntimeException(String message, Throwable cause, Integer status, WearablesError... errors) {
		super(message, cause);
		this.errors = Arrays.asList(errors);
		this.status = status;
	}

	public AbstractBaseWearablesRuntimeException(Throwable cause, WearablesError... errors) {
		super(cause);
		this.errors = Arrays.asList(errors);
	}

	public AbstractBaseWearablesRuntimeException(Throwable cause, Status httpStatus, WearablesError... errors) {
		super(cause);
		this.errors = Arrays.asList(errors);
		this.status = httpStatus.getStatusCode();
	}

	public AbstractBaseWearablesRuntimeException(Throwable cause, Integer status, WearablesError... errors) {
		super(cause);
		this.errors = Arrays.asList(errors);
		this.status = status;
	}

	public void setErrors(List<WearablesError> errors) {
		this.errors = errors;
	}

	public void addError(WearablesError error) {
		errors.add(error);
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
		return "BaseWEARABLESRuntimeException [errors=" + errors + ", status=" + status + "]";
	}

	/**
	 * Returns count of errors in list
	 * 
	 * @return
	 */
	public int getErrorCount() {
		return this.errors.size();
	}

}
