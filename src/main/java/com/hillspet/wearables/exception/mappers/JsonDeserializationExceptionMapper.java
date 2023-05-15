package com.hillspet.wearables.exception.mappers;

import java.util.Arrays;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.handler.ExceptionHandler;
import com.hillspet.wearables.common.response.ErrorResponse;
import com.hillspet.wearables.common.response.ResponseStatus;
import com.hillspet.wearables.helpers.MessageHelper;

@Provider
@Component
@Produces({MediaType.APPLICATION_JSON})
public class JsonDeserializationExceptionMapper implements ExceptionMapper<JsonMappingException> {

    @Autowired
    private JaxrsJsonResponseBuilder responseBuilder;
    
    @Autowired
    private MessageHelper messageHelper;
    
    @Autowired
    private ExceptionHandler exceptionHandler;
    
    private static final Logger LOGGER = LogManager.getLogger(JsonDeserializationExceptionMapper.class);
    
    @Override
    public Response toResponse(JsonMappingException exception) {
        LOGGER.error("JsonProcessing exception occured while processing the request.", exception);
        String msg = exception.getMessage(); //gets the error code
        // it will be easier if we debug the exact location of the request where it's failing
        // so we will check if cause is of type JsonParseException
        JsonLocation location = exception.getLocation();
        
        if(location == null && exception.getCause() instanceof JsonParseException ) {
            location = ((JsonParseException) exception.getCause()).getLocation();
            location = location == null ? JsonLocation.NA : location;
        }
        WearablesError error = new WearablesError(WearablesErrorCode.WEARABLES_INVALID_JSON_INPUT, msg, location.getLineNr(), location.getColumnNr());
        exceptionHandler.handleException(exception, Arrays.asList(error));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(messageHelper.buildErrorMessages(Arrays.asList(error)));
        errorResponse.setStatus(new ResponseStatus(Boolean.FALSE, Status.BAD_REQUEST.getStatusCode()));
        return responseBuilder.buildResponse(errorResponse);
    }
}
