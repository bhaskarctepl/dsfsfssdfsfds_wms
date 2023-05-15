package com.hillspet.wearables.common.services.core.validators;

import java.util.Base64;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.utils.WearablesUtils;
import com.hillspet.wearables.common.validation.ValidationResult;

/**
 * Validates field length. It will expect non null values
 * 
 * @author dharisis
 *
 */
@Component
public class FieldValidator {

	private static final Logger LOGGER = LogManager.getLogger(FieldValidator.class);

	/**
	 * 
	 * @param data
	 * @param errorCode
	 * @param result
	 * @return
	 */
	public void validate(String data, Pattern pattern, ErrorDetailInterface errorCode, ValidationResult result,
			Object... dataMembers) {
		// Cannot log any data as this validator will be used for validating sensitive
		// fields
		if (!WearablesUtils.isValidString(pattern, data)) {
			WearablesUtils.addError(result, errorCode, data, dataMembers);
			LOGGER.debug("Data passed is failed in the pattern validation");
		}
	}

	/**
	 * Validates length of given string. If validation fails, then it will add given
	 * error code to ValidationResult object.
	 * 
	 * @param data
	 * @param maxLength
	 * @param errorCode
	 * @param result
	 * @return
	 */
	public void validateLength(String data, Integer minLength, Integer maxLength, ErrorDetailInterface errorCode,
			ValidationResult result) {
		if (StringUtils.length(data) > maxLength || StringUtils.length(data) < minLength) {
			WearablesUtils.addError(result, errorCode, data, minLength, maxLength);
		}
	}

	/**
	 * Validates max length of given string. If validation fails, then it will add
	 * given error code to ValidationResult object.
	 * 
	 * @param data
	 * @param maxLength
	 * @param errorCode
	 * @param result
	 * @return
	 */
	public void validateMaxLength(String data, Integer maxLength, ErrorDetailInterface errorCode,
			ValidationResult result) {
		if (StringUtils.length(data) > maxLength) {
			WearablesUtils.addError(result, errorCode, data, maxLength);
		}
	}

	/**
	 * Validates min length of given string. If validation fails, then it will add
	 * given error code to ValidationResult object.
	 * 
	 * @param data
	 * @param minLength
	 * @param errorCode
	 * @param result
	 * @return
	 */
	public void validateMinLength(String data, Integer minLength, ErrorDetailInterface errorCode,
			ValidationResult result) {
		if (StringUtils.length(data) < minLength) {
			WearablesUtils.addError(result, errorCode, data, minLength);
		}
	}

	/**
	 * Validates Base64 encoded password. If validation fails, then it will add
	 * given error code to ValidationResult object.
	 * 
	 * @param data
	 * @param errorCode
	 * @param result
	 * @return
	 */
	public void validateBase64EncodedPassword(String data, ErrorDetailInterface errorCode, ValidationResult result) {
		try {
			Base64.getDecoder().decode(data);
		} catch (IllegalArgumentException e) {
			LOGGER.debug("Password not base64 encoded properly", e);
			WearablesUtils.addError(result, errorCode, data);
		}
	}

	/**
	 * Validates lower and upper limit of numeric value. If validation fails, then
	 * it will add given error code to ValidationResult object.
	 * 
	 * @param data
	 * @param minValue
	 * @param maxValue
	 * @param errorCode
	 * @param result
	 * @return
	 */
	public void validateNumericRange(Integer data, Integer minValue, Integer maxValue, ErrorDetailInterface errorCode,
			ValidationResult result) {
		if (data > maxValue || data < minValue) {
			WearablesUtils.addError(result, errorCode, data, minValue, maxValue);
		}
	}

}
