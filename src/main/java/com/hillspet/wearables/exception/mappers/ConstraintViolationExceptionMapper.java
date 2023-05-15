package com.hillspet.wearables.exception.mappers;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.StringLiterals;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ErrorDetailBean;
import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.handler.ExceptionHandler;
import com.hillspet.wearables.common.response.ErrorResponse;
import com.hillspet.wearables.common.response.ResponseStatus;
import com.hillspet.wearables.helpers.HttpStatusHelper;
import com.hillspet.wearables.helpers.MessageHelper;


/**
 * This class will be called by the JAXRS runtime whenever an exception of type
 * ConstraintViolation is thrown by the application This provides an abstraction
 * & a way to extract necessary details from the Exception object & construct a
 * suitable response to be sent to the caller.
 * 
 * @author vvodyaram
 *
 */
@Provider
@Component
@Produces({ MediaType.APPLICATION_JSON })
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Autowired
	private MessageHelper messageHelper;

	@Value("${wearables.maxPrintableCharLength}")
	private int maxPrintableCharLength;

	@Autowired
	private ExceptionHandler exceptionHandler;

	/*@Autowired
	private ClientContextService clientContextService;*/

	@Autowired
	private ApplicationContext applicationContext;

	private static final Logger LOGGER = LogManager.getLogger(ConstraintViolationExceptionMapper.class);

	/**
	 * Creates a JAXRS response from a ConstraintViolation
	 */
	@Override
	public Response toResponse(ConstraintViolationException constraintViolationException) {
		List<WearablesError> errors = constraintViolationException.getConstraintViolations().stream().map(errorFunction)
				.collect(Collectors.toList());
		LOGGER.error("ConstraintViolationException has occurred with the errors {} ", constraintViolationException,
				errors);

		exceptionHandler.handleException(constraintViolationException, errors);

		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrors(messageHelper.buildErrorMessages(errors));
		errorResponse.setStatus(new ResponseStatus(Boolean.FALSE,
				HttpStatusHelper.getConstraintViolationErrorStatus().getStatusCode()));
		return responseBuilder.buildResponse(errorResponse);

	}

	private Function<ConstraintViolation<? extends Object>, WearablesError> errorFunction = this::createError;

	/**
	 * A utility method to construct the error object from the constraint violation
	 * 
	 * @param violation
	 * @return
	 */
	private WearablesError createError(ConstraintViolation<? extends Object> violation) {
		//ClientContext clientContext = clientContextService.getClientContext();
		String appId = "Wearables";
		String constraintCode = violation.getMessage();
		ErrorDetailInterface errorCode = WearablesErrorCode.WEARABLES_CONSTRAINT_VIOLATION_EXP;

		/*
		 * Bean must have been defined with naming convention AppId + ErrorDetailBean
		 */
		if (StringUtils.isNotBlank(appId)) {
			final String beanName = appId + Constants.ERROR_DETAIL_BEAN;
			ErrorDetailBean appIdErrorDetailBean = (ErrorDetailBean) applicationContext.getBean(beanName);
			if (appIdErrorDetailBean != null) {
				Optional<ErrorDetailInterface> errorCodeOpt = appIdErrorDetailBean.getErrorDetail(constraintCode);
				if (errorCodeOpt.isPresent()) {
					errorCode = errorCodeOpt.get();
				} else {
					LOGGER.info(
							"AppId {}, an error code is not present for constraintCode {}, we will use the default error code",
							appId, constraintCode, errorCode);
				}
			} else {
				LOGGER.info(
						"No ErrorDetailBean defined, please check if an ErrorDetailBeanConfig is defined and make sure the bean name matches with {}",
						beanName);
			}
		} else {
			LOGGER.info("AppId = {} is either blank/null in ClientContext, we will use the default error code {}",
					appId, errorCode);
		}

		String location = extractLocation(violation);
		WearablesError error = new WearablesError(errorCode, violation.getInvalidValue(), location);
		LOGGER.debug("constraintCode encountered {}, errorCode {}, location {}", constraintCode, errorCode, location);
		return error;
	}

	private String extractLocation(ConstraintViolation<? extends Object> violation) {
		int pathMaxLength = maxPrintableCharLength - 3;
		String location = violation.getPropertyPath().toString();

		// If the location path is more than max printable characters failing property
		// will not
		// display properly in error message. We will be displaying property path from
		// end
		if (StringUtils.isNotBlank(location) && StringUtils.length(location) > pathMaxLength) {
			String[] splitLocation = StringUtils.split(location, StringLiterals.DOT.getCode());
			String temp = "";
			for (int i = (splitLocation.length); i > 0; i--) {
				if ((splitLocation[i - 1] + temp).length() > pathMaxLength) {
					break;
				}
				temp = StringLiterals.DOT.getCode() + splitLocation[i - 1] + temp;
			}
			location = StringUtils.removeStart(temp, StringLiterals.DOT.getCode());
		}
		return location;
	}
}
