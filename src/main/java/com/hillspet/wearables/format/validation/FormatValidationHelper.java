package com.hillspet.wearables.format.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.services.core.validators.FieldValidator;
import com.hillspet.wearables.common.services.core.validators.RequiredFieldValidator;
import com.hillspet.wearables.common.utils.WearablesUtils;
import com.hillspet.wearables.common.validation.ValidationResult;
import com.hillspet.wearables.format.validation.ValidationDataHolder.FormatValType;

@Component
@Scope("prototype")
/**
 * This class executes format validations for fields sequentially
 * 
 * @author hrangwal
 *
 */
public class FormatValidationHelper {
	protected final Logger LOGGER = LogManager.getLogger(FormatValidationHelper.class);

	private List<ValidationAttribute> attributes = new ArrayList<>();

	@Autowired
	protected FieldValidator fieldValidator;

	@Autowired
	private ApplicationContext appContext;

	/**
	 * Do not proceed to next validation in case of an error
	 */
	private boolean proceedOnError = Boolean.FALSE;

	/**
	 * Utility method for adding a validation data holder to the Collection
	 * 
	 * @param valDataHolder
	 */
	public <T> FormatValidationHelper add(ValidationAttribute attribute) {
		if (attribute != null) {
			attributes.add(attribute);
		}
		return this;
	}

	/**
	 * Default is false i.e does not proceed to next validation if an error is
	 * encountered
	 * 
	 * @param proceedOnError
	 */
	public void setProceedOnError(boolean proceedOnError) {
		this.proceedOnError = proceedOnError;
	}

	public boolean isProceedOnError() {
		return proceedOnError;
	}

	private void executeValidation(ValidationResult validationResult,
			ValidationDataHolder<? extends Object> valDataHolder) {
		FormatValType formatValType = valDataHolder.getFormatValType();
		switch (formatValType) {
		case REQUIRED:
			RequiredValDataHolder<? extends Object> requiredValDataHolder = (RequiredValDataHolder<? extends Object>) valDataHolder;
			validationResult = requiredFieldValidation.apply(requiredValDataHolder, validationResult);
			break;
		case LENGTH:
			MaxLengthValDataHolder maxLengthValHolder = (MaxLengthValDataHolder) valDataHolder;
			validationResult = maxLengthFieldValidation.apply(maxLengthValHolder, validationResult);
			break;
		case PATTERN_MATCH:
			PatternMatchValDataHolder patternMatchValHolder = (PatternMatchValDataHolder) valDataHolder;
			validationResult = patternMatchFieldValidation.apply(patternMatchValHolder, validationResult);
			break;
		case EQUALS:
			EqualsValDataHolder<? extends Object> equalsValHolder = (EqualsValDataHolder<? extends Object>) valDataHolder;
			validationResult = equalsFieldValidation.apply(equalsValHolder, validationResult);
			break;
		case CONTAINS:
			ContainsValDataHolder<? extends Object> containsValHolder = (ContainsValDataHolder<? extends Object>) valDataHolder;
			validationResult = containsFieldValidation.apply(containsValHolder, validationResult);
			break;
		default:
			break;
		}

	}

	private void executeConditionalValidation(ValidationResult validationResult, ValidationAttribute attribute,
			List<List<ValidationDataHolder<? extends Object>>> conditionalValidators) {
		// Lets check if there are any conditional validators set for this attribute
		if (isSafeToProceed(validationResult, proceedOnError)) {
			if (CollectionUtils.isNotEmpty(conditionalValidators)) {
				LOGGER.debug("Executing conditional validations for attribute = {}", attribute.getName());

				/*
				 * Do not execute any subsequent conditional validation if one of the condition
				 * fails
				 */
				boolean proceedIfConditionFails = Boolean.FALSE;

				/*
				 * Flag to determine whether it is safe to proceed to validate next condition
				 */
				boolean isSafeToProceedToNextCondition = Boolean.TRUE;

				for (List<ValidationDataHolder<? extends Object>> validators : conditionalValidators) {
					/*
					 * Flag to determine if the matching condition is successful, if matching
					 * condition validation fails then it is fine to proceed to validate next
					 * condition
					 */
					boolean isMatchingConditionSuccess = false;
					for (int i = 0; i < validators.size(); i++) {
						ValidationDataHolder<? extends Object> condition = validators.get(i);
						if (i == 0) {
							/*
							 * we create a new validationresult object during the matching condition as we
							 * do not care about the error generated by the matching condition
							 */
							ValidationResult tempValidationResult = new ValidationResult();
							executeValidation(tempValidationResult, condition);
							/*
							 * first validator is the MATCHING condition - for e.g if the condition is
							 * defined as if x == this then validate y == that In the above scenario, the
							 * first condition also called as the MATCHING condition is to first validate
							 * that x == this
							 */
							isMatchingConditionSuccess = !tempValidationResult.hasErrors();

							if (isMatchingConditionSuccess) {
								LOGGER.debug(
										"Matching condition is successful for attribute = {}, condition = {}, it is safe to proceed to validate the next condition",
										attribute.getName(), condition);
							} else {
								LOGGER.debug(
										"Matching condition has failed for attribute = {}, condition = {}, we will not proceed to validate the next condition",
										attribute.getName(), condition);
								validationResult.addErrors(tempValidationResult.getErrorList());
								break; // stop & move to next condition
							}
						} else {
							executeValidation(validationResult, condition);
							isMatchingConditionSuccess = true; // if we came here means the matching condition was
																// successful
							isSafeToProceedToNextCondition = isSafeToProceed(validationResult, proceedIfConditionFails);

							if (isSafeToProceedToNextCondition) {
								LOGGER.debug(
										"Conditional Validation successful for attribute = {}, condition = {}, it is safe to proceed to validate the next condition",
										attribute.getName(), condition);
							} else {
								LOGGER.debug(
										"Conditional Validation has failed for attribute = {}, condition = {}, we will not proceed to validate the next condition",
										attribute.getName(), condition);
								break; // stop & move to next condition
							}
						}
					}
					if (!isSafeToProceedToNextCondition)
						break; // stop all validations
				}
			} else {
				LOGGER.debug("No conditional validations defined for attribute = {}", attribute.getName());
			}
		} else {
			LOGGER.debug(
					"There are field level validation failures for attribute = {}, cannot proceed with conditional level validations. proceedOnError = {}",
					attribute.getName(), proceedOnError);
		}

	}

	private void executeFieldValidations(ValidationResult validationResult, ValidationAttribute attribute,
			PriorityQueue<ValidationDataHolder<? extends Object>> validationDataHolders) {
		if (CollectionUtils.isNotEmpty(validationDataHolders)) {
			for (ValidationDataHolder<? extends Object> valDataHolder : validationDataHolders) {
				/**
				 * Below flag will store the state of validation for each validator for a field
				 * This will be helpful to determine if we need to proceed to next validator for
				 * a field or not. Requirement is that for a given field once a validation
				 * fails, subsequent validations for the same field must not be performed
				 */
				int numOfErrors = validationResult.getErrorList() != null ? validationResult.getErrorList().size() : 0;
				executeValidation(validationResult, valDataHolder);
				boolean hasValidationFailed = validationResult.getErrorList().size() > numOfErrors ? true : false;
				if (hasValidationFailed) {
					LOGGER.debug(
							"There are validation errors for attribute = {}, validation input {}. It does not make any sense to perform subsequent validation(s) for this attribute. {} ",
							attribute.getName(), valDataHolder, validationResult);
					break;
				} else {
					LOGGER.debug("Validation successful for attribute = {} for validation input {}, {}",
							attribute.getName(), valDataHolder, validationResult);
				}
			}
		} else {
			LOGGER.debug("No field validators configured for attribute = {}", attribute.getName());
		}

	}

	/**
	 * A template method that initiate validations for the attribute as requested
	 * 
	 * @param validationResult
	 */
	public void execute(ValidationResult validationResult) {
		if (validationResult == null) {
			validationResult = new ValidationResult();
		}
		for (ValidationAttribute attribute : attributes) {
			/**
			 * Step 1: run through all validations for an attribute every time checking if
			 * the previous validation was successful or not Step 2: run through all the
			 * conditional validators as well
			 */

			LOGGER.debug("Will now execute validations for attribute = {}", attribute.getName(), validationResult);
			PriorityQueue<ValidationDataHolder<? extends Object>> fieldValidators = attribute.getValidators();
			List<List<ValidationDataHolder<? extends Object>>> conditionalValidators = attribute
					.getConditionalValidators();

			executeFieldValidations(validationResult, attribute, fieldValidators);
			executeConditionalValidation(validationResult, attribute, conditionalValidators);

			if (!isSafeToProceed(validationResult, proceedOnError)) {
				LOGGER.debug("One or more validation(s) have failed for attribute = {}, {}", attribute.getName(),
						validationResult);
				break;
			} else {
				LOGGER.debug("Validations successful for attribute = {}, {}", attribute.getName(), validationResult);
			}
		}
	}

	/**
	 * Convenience method to check if it is safe to proceed
	 * 
	 * @param valResult
	 * @param isProceedOnError
	 * @return
	 */
	private boolean isSafeToProceed(ValidationResult valResult, boolean isProceedOnError) {
		if (valResult.hasErrors() && !isProceedOnError) {
			return false;
		}
		return true;
	}

	BiFunction<RequiredValDataHolder<? extends Object>, ValidationResult, ValidationResult> requiredFieldValidation = this::validateRequiredField;
	BiFunction<MaxLengthValDataHolder, ValidationResult, ValidationResult> maxLengthFieldValidation = this::validateLength;
	BiFunction<PatternMatchValDataHolder, ValidationResult, ValidationResult> patternMatchFieldValidation = this::validatePattern;
	BiFunction<EqualsValDataHolder<? extends Object>, ValidationResult, ValidationResult> equalsFieldValidation = this::validateEquals;
	BiFunction<ContainsValDataHolder<? extends Object>, ValidationResult, ValidationResult> containsFieldValidation = this::validateContains;

	/**
	 * Convenience method to perform required field validation
	 * 
	 * @param requiredFieldValHolder
	 * @param validationResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> ValidationResult validateRequiredField(RequiredValDataHolder<T> requiredFieldValHolder,
			ValidationResult validationResult) {
		RequiredFieldValidator<T> reqFieldVal = (RequiredFieldValidator<T>) appContext
				.getBean(RequiredFieldValidator.class);
		reqFieldVal.validate(requiredFieldValHolder.getData(), requiredFieldValHolder.getErrorCode(), validationResult);
		return validationResult;
	}

	/**
	 * Convenience method to perform max length field validation
	 * 
	 * @param maxLengthValHolder
	 * @param validationResult
	 * @return
	 */
	private <T> ValidationResult validateLength(MaxLengthValDataHolder maxLengthValHolder,
			ValidationResult validationResult) {
		if (StringUtils.isNotEmpty(maxLengthValHolder.getData())) {
			if (maxLengthValHolder.getMinLength() != null && maxLengthValHolder.getMaxLength() != null) {
				fieldValidator.validateLength(maxLengthValHolder.getData(), maxLengthValHolder.getMinLength(),
						maxLengthValHolder.getMaxLength(), maxLengthValHolder.getErrorCode(), validationResult);
			} else if (maxLengthValHolder.getMaxLength() != null) {
				fieldValidator.validateMaxLength(maxLengthValHolder.getData(), maxLengthValHolder.getMaxLength(),
						maxLengthValHolder.getErrorCode(), validationResult);
			} else if (maxLengthValHolder.getMinLength() != null) {
				fieldValidator.validateMinLength(maxLengthValHolder.getData(), maxLengthValHolder.getMinLength(),
						maxLengthValHolder.getErrorCode(), validationResult);
			} else {
				LOGGER.debug("Cannot validate maxLength as both the maxLength and minLength is null for field {}");
			}
		} else {
			LOGGER.debug(
					"Cannot validate maxLength as data is null or empty for field {}, if this is a required field then add a required validator to the chain ");
		}

		return validationResult;
	}

	/**
	 * Convenience method to perform pattern match field validation
	 * 
	 * @param patternValHolder
	 * @param validationResult
	 * @return
	 */
	private <T> ValidationResult validatePattern(PatternMatchValDataHolder patternValHolder,
			ValidationResult validationResult) {
		if (StringUtils.isNotEmpty(patternValHolder.getData())) {
			Pattern pattern = Pattern.compile(patternValHolder.getRegex());
			fieldValidator.validate(patternValHolder.getData(), pattern, patternValHolder.getErrorCode(),
					validationResult);
		} else {
			LOGGER.debug(
					"Cannot validate pattern as data is null or empty for field {}, if this is a required field then add a required validator to the chain ");
		}

		return validationResult;
	}

	/**
	 * Convenience method to perform string equals
	 * 
	 * @param patternValHolder
	 * @param validationResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> ValidationResult validateEquals(EqualsValDataHolder<T> equalsValDataHolder,
			ValidationResult validationResult) {
		final T arg0 = equalsValDataHolder.getData();
		final List<T> arg1 = equalsValDataHolder.getAllowedValues();
		Optional<?> optMatch = Optional.empty();
		if (arg0 != null && arg1 != null) {
			// Its important to find the type of T
			if (arg0 instanceof String) {
				String allowedValue = (String) arg0;
				if (StringUtils.isNotEmpty(allowedValue)) {
					/*
					 * We dont want to compare the allowed set of values with empty string - however
					 * if the input value contains spaces then we would consider that as a valid
					 * value and compare it with allowed values.
					 */
					List<String> allowedValues = (List<String>) arg1;
					optMatch = allowedValues.stream().filter(val -> val.equalsIgnoreCase(allowedValue)).findFirst();
				} else {
					LOGGER.debug(
							"Cannot validate as data is empty for field {}, if this is a required field then add a required validator to the chain ");
				}

			} else if (arg0 instanceof Boolean) {
				Boolean allowedValue = (Boolean) arg0;
				List<Boolean> allowedValues = (List<Boolean>) arg1;
				optMatch = allowedValues.stream().filter(val -> val.equals(allowedValue)).findFirst();
			} else if (arg0 instanceof Integer) {
				Integer allowedValue = (Integer) arg0;
				List<Integer> allowedValues = (List<Integer>) arg1;
				optMatch = allowedValues.stream().filter(val -> val.equals(allowedValue)).findFirst();
			} else {
				LOGGER.debug(
						"Input Data type did not match any concrete data types & is not String, Integer or Boolean, input = {}, allowedValues = {} ",
						arg0, arg1);
				Object allowedValue = (Object) arg0;
				List<Object> allowedValues = (List<Object>) arg1;
				optMatch = allowedValues.stream().filter(val -> val.equals(allowedValue)).findFirst();
			}
			if (!optMatch.isPresent()) {
				WearablesUtils.addError(validationResult, equalsValDataHolder.getErrorCode(), arg0, arg1);
				LOGGER.debug("equalsIgnoreCase validation failed for input = {}, allowedValues = {} ", arg0, arg1);
			}
		} else {
			LOGGER.debug(
					"Cannot validate as data is null for field {}, if this is a required field then add a required validator to the chain ");
		}

		return validationResult;
	}

	private <T> ValidationResult validateContains(ContainsValDataHolder<T> containsValDataHolder,
			ValidationResult validationResult) {
		Collection<T> inputData = containsValDataHolder.getInputData();
		T allowedValue = containsValDataHolder.getAllowedValue();
		Collection<T> allowedValues = containsValDataHolder.getAllowedValues();
		boolean isContains = false;

		if (CollectionUtils.isNotEmpty(inputData)) {
			if (allowedValue != null) {
				if (allowedValue instanceof String && StringUtils.isEmpty((String) allowedValue)) {
					LOGGER.debug("allowedValue is empty, allowedValue = {}, allowedValues = {}", allowedValue,
							allowedValues);
				} else {
					isContains = inputData.contains(allowedValue);
					if (!isContains) {
						LOGGER.debug("inputData {} does not contain the allowedValue {}", inputData, allowedValue);
						WearablesUtils.addError(validationResult, containsValDataHolder.getErrorCode(), inputData,
								allowedValue);
					}
				}
			} else if (CollectionUtils.isNotEmpty(allowedValues)) {
				isContains = inputData.containsAll(allowedValues);
				if (!isContains) {
					LOGGER.debug("inputData {} does not contain the allowedValues {}", inputData, allowedValues);
					WearablesUtils.addError(validationResult, containsValDataHolder.getErrorCode(), inputData,
							allowedValues);
				}

			} else {
				LOGGER.debug("Both allowedValue & allowedValues is null/empty for inputData = {}", inputData);
				WearablesUtils.addError(validationResult, containsValDataHolder.getErrorCode(), inputData, allowedValue,
						allowedValues);
			}
		} else {
			LOGGER.debug("inputData is null/empty, allowedValue = {}, allowedValues = {}", allowedValue, allowedValues);
		}

		return validationResult;
	}

}
