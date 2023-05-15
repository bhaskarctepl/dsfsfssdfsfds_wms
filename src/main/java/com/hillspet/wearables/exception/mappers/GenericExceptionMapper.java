package com.hillspet.wearables.exception.mappers;

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
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.handler.ExceptionHandler;
import com.hillspet.wearables.common.response.ErrorResponse;
import com.hillspet.wearables.common.response.ResponseStatus;
import com.hillspet.wearables.helpers.HttpStatusHelper;
import com.hillspet.wearables.helpers.MessageHelper;

@Provider
@Component
@Produces({ MediaType.APPLICATION_JSON })
public  class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;
	
	@Autowired
	private MessageHelper messageHelper;
	
	@Autowired
	private ExceptionHandler exceptionHandler;
	
	private static final Logger LOGGER = LogManager.getLogger(GenericExceptionMapper.class);
	
	@Override
	public Response toResponse(Throwable exception) {		
		WearablesError undefinedError = new WearablesError(WearablesErrorCode.WEARABLES_SYSTEM_ERROR);
        LOGGER.error("An Exception has occurred with the error {} ", exception, undefinedError);
		exceptionHandler.handleException(exception, undefinedError);		
		
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.addErrorMessage(messageHelper.buildErrorMessage(undefinedError));
		errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, HttpStatusHelper.getDefaultServerError().getStatusCode()));
		return responseBuilder.buildResponse(errorResponse);
	}
	
}
