package com.hillspet.wearables.common.services.core.validators;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.validation.ValidationResult;

/**
 * Validate the given field is blank or not.
 * 
 * @author dharisis
 *
 */
@Component
public class RequiredFieldValidator<T> {

	private static final Logger LOGGER = LogManager.getLogger(RequiredFieldValidator.class);

	/**
	 * 
	 * @param data
	 * @param errorCode
	 * @param result
	 * @return
	 */
	public void validate(T data, ErrorDetailInterface errorCode, ValidationResult result) {
		// Cannot log any data as this validator will be used for validating sensitive
		// fields
		if (data == null || StringUtils.isBlank(data.toString())) {
			result.addError(new WearablesError(errorCode, data));
			result.setStatusOnError(errorCode.getStatus());
			LOGGER.debug("Data passed failed the required field validation");
		}
	}

}
