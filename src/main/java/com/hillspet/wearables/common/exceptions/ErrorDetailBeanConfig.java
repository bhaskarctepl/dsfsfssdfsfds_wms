package com.hillspet.wearables.common.exceptions;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hillspet.wearables.common.constants.WearablesErrorCode;

/**
 * This class will be responsible to add the ErrorDetail i.e
 * ErrorCodes/WarningCodes used by the application to the framework so that it
 * can be used at runtime to add detailed information in case an error is
 * encountered
 * 
 * @author vvodyaram
 *
 */
@Configuration
public class ErrorDetailBeanConfig {

	private static final Logger LOGGER = LogManager.getLogger(ErrorDetailBeanConfig.class);

	/**
	 * Add the below bean definition whenever the app throws a
	 * ConstraintViolationException and the error details are needed at runtime
	 * 
	 * The method name are consciously kept in the pattern AppId + ErrorDetailBean
	 * as at runtime the beans will be loaded by looking up the app Id
	 * 
	 * @return
	 */
	@Bean
	public ErrorDetailBean WearablesErrorDetailBean() {
		ErrorDetailBean errorDetailBean = new ErrorDetailBean();
		List<ErrorDetailInterface> errorDetails = Arrays.asList(WearablesErrorCode.values());
		errorDetailBean.setErrorDetails(errorDetails);
		errorDetailBean.setAppId("Wearables");
		LOGGER.debug("Adding Wearables to the ErrorDetailBean {}", errorDetailBean);
		return errorDetailBean;
	}

}
