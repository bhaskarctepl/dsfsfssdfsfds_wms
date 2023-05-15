package com.hillspet.wearables.exception.mappers;

import java.util.Arrays;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
import com.hillspet.wearables.helpers.MessageHelper;

@Provider
@Component
@Produces({ MediaType.APPLICATION_JSON })
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException>{
    
    @Autowired
    private JaxrsJsonResponseBuilder responseBuilder;
    
    @Autowired
    private MessageHelper messageHelper;
    
    @Autowired
    private ExceptionHandler exceptionHandler;
    
    private static final Logger LOGGER = LogManager.getLogger(WebApplicationExceptionMapper.class);
    
    @Override
    public Response toResponse(WebApplicationException exception) {
		LOGGER.error("Webapplication exception occured while processing the request.", exception);
        String msg = exception.getMessage(); //gets the error code
        WearablesError error = new WearablesError(WearablesErrorCode.WEARABLES_WEB_APP_EXP, msg);
        exceptionHandler.handleException(exception, Arrays.asList(error));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(messageHelper.buildErrorMessages(Arrays.asList(error)));
        errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, exception.getResponse().getStatus()));
        return responseBuilder.buildResponse(errorResponse);
    }
}
