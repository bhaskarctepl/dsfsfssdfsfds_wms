package com.hillspet.wearables.exception.mappers;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.AbstractBaseWearablesRuntimeException;
import com.hillspet.wearables.common.handler.ExceptionHandler;
import com.hillspet.wearables.common.response.ErrorResponse;
import com.hillspet.wearables.common.response.ResponseStatus;
import com.hillspet.wearables.helpers.MessageHelper;

/**
 * This class will be called by the JAXRS runtime whenever an exception of type
 * {@code BaseWEARABLESRuntimeException} is thrown by the application This provides an
 * abstraction & a way to extract necessary details from the Exception object &
 * construct a suitable response to be sent to the caller.
 * 
 * @author vvodyaram
 *
 */

@Provider
@Component
@Produces({ MediaType.APPLICATION_JSON })
public class BaseWearablesRuntimeExceptionMapper implements ExceptionMapper<AbstractBaseWearablesRuntimeException> {

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private MessageHelper messageHelper;

	@Autowired
	private ExceptionHandler exceptionHandler;

	private static final Logger LOGGER = LogManager.getLogger(BaseWearablesRuntimeExceptionMapper.class);

	@Override
	public Response toResponse(AbstractBaseWearablesRuntimeException exception) {
		final List<WearablesError> errors = exception.getErrors();
		LOGGER.error("A type of BaseWEARABLESRuntimeException has occurred with {} status, having error values of {}",
				exception, exception.getStatus(), errors);

		exceptionHandler.handleException(exception, errors);

		// No need to log the response here as it will be handled by the log out
		// interceptor
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(messageHelper.buildErrorMessages(errors));
		errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, exception.getStatus()));
		Response response = responseBuilder.buildResponse(errorResponse);
		return response;
	}

}
