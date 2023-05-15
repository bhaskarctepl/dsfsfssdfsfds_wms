package com.hillspet.wearables.format.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import org.apache.commons.collections4.CollectionUtils;

import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.exceptions.InvalidConfigException;

public class ValidationAttribute {

	private String name;

	/**
	 * Reason to use PriorityQueue is to make sure that the priority of validator's
	 * are maintained. for e.g required validation is executed before max length or
	 * any other validation
	 * 
	 */
	private PriorityQueue<ValidationDataHolder<? extends Object>> validators = new PriorityQueue<>(
			Comparator.comparing(ValidationDataHolder::getPriority));

	/**
	 * Validation outcome is SUCCESS if all validations are successful for a
	 * condition (logical AND) If validation fails for one condition then no further
	 * validation for other conditions will be executed
	 * 
	 */
	private List<List<ValidationDataHolder<? extends Object>>> conditionalValidators = new ArrayList<>();

	public ValidationAttribute(String name) {
		super();
		this.name = name;
	}

	public RequiredValDataHolder<String> required(String data, ErrorDetailInterface errorCode) {
		RequiredValDataHolder<String> requiredValidator = new RequiredValDataHolder<String>(data, errorCode);
		return requiredValidator;
	}

	public ValidationAttribute addRequired(String data, ErrorDetailInterface errorCode) {
		RequiredValDataHolder<String> requiredValidator = required(data, errorCode);
		validators.add(requiredValidator);
		return this;
	}

	public RequiredValDataHolder<Integer> required(Integer data, ErrorDetailInterface errorCode) {
		RequiredValDataHolder<Integer> requiredValidator = new RequiredValDataHolder<Integer>(data, errorCode);
		return requiredValidator;
	}

	public ValidationAttribute addRequired(Integer data, ErrorDetailInterface errorCode) {
		RequiredValDataHolder<Integer> requiredValidator = required(data, errorCode);
		validators.add(requiredValidator);
		return this;
	}

	public RequiredValDataHolder<Boolean> required(Boolean data, ErrorDetailInterface errorCode) {
		RequiredValDataHolder<Boolean> requiredValidator = new RequiredValDataHolder<Boolean>(data, errorCode);
		return requiredValidator;
	}

	public ValidationAttribute addRequired(Boolean data, ErrorDetailInterface errorCode) {
		RequiredValDataHolder<Boolean> requiredValidator = required(data, errorCode);
		validators.add(requiredValidator);
		return this;
	}

	public MaxLengthValDataHolder maxLength(String data, Integer maxLength, ErrorDetailInterface errorCode) {
		MaxLengthValDataHolder maxLengthValidator = new MaxLengthValDataHolder(data, maxLength, errorCode);
		return maxLengthValidator;
	}

	public ValidationAttribute addMaxLength(String data, Integer maxLength, ErrorDetailInterface errorCode) {
		MaxLengthValDataHolder maxLengthValidator = maxLength(data, maxLength, errorCode);
		validators.add(maxLengthValidator);
		return this;
	}

	public MaxLengthValDataHolder length(String data, Integer minLength, Integer maxLength,
			ErrorDetailInterface errorCode) {
		MaxLengthValDataHolder maxLengthValidator = new MaxLengthValDataHolder(data, minLength, maxLength, errorCode);
		return maxLengthValidator;
	}

	public ValidationAttribute addLength(String data, Integer minLength, Integer maxLength,
			ErrorDetailInterface errorCode) {
		MaxLengthValDataHolder maxLengthValidator = length(data, minLength, maxLength, errorCode);
		validators.add(maxLengthValidator);
		return this;
	}

	public PatternMatchValDataHolder patternMatch(String data, String regex, ErrorDetailInterface errorCode) {
		PatternMatchValDataHolder patternMatchValidator = new PatternMatchValDataHolder(data, regex, errorCode);
		return patternMatchValidator;
	}

	public ValidationAttribute addPatternMatch(String data, String regex, ErrorDetailInterface errorCode) {
		PatternMatchValDataHolder patternMatchValidator = patternMatch(data, regex, errorCode);
		validators.add(patternMatchValidator);
		return this;
	}

	public Optional<EqualsValDataHolder<String>> equalsIgnoreCase(String data, String allowedValue,
			ErrorDetailInterface errorCode) {
		EqualsValDataHolder<String> equalsValidator = null;
		if (allowedValue != null) {
			equalsValidator = new EqualsValDataHolder<String>(data, Arrays.asList(allowedValue), errorCode);
		}
		return Optional.ofNullable(equalsValidator);
	}

	public ValidationAttribute addEqualsIgnoreCase(String data, String allowedValue, ErrorDetailInterface errorCode) {
		Optional<EqualsValDataHolder<String>> equalsValidatorOpt = equalsIgnoreCase(data, allowedValue, errorCode);
		if (equalsValidatorOpt.isPresent()) {
			EqualsValDataHolder<String> equalsValidator = equalsValidatorOpt.get();
			validators.add(equalsValidator);
		}
		return this;
	}

	public Optional<EqualsValDataHolder<Boolean>> equalsIgnoreCase(Boolean data, Boolean allowedValue,
			ErrorDetailInterface errorCode) {
		EqualsValDataHolder<Boolean> equalsValidator = null;
		if (allowedValue != null) {
			equalsValidator = new EqualsValDataHolder<Boolean>(data, Arrays.asList(allowedValue), errorCode);
		}
		return Optional.ofNullable(equalsValidator);
	}

	public ValidationAttribute addEqualsIgnoreCase(Boolean data, Boolean allowedValue, ErrorDetailInterface errorCode) {
		Optional<EqualsValDataHolder<Boolean>> equalsValidatorOpt = equalsIgnoreCase(data, allowedValue, errorCode);
		if (equalsValidatorOpt.isPresent()) {
			EqualsValDataHolder<Boolean> equalsValidator = equalsValidatorOpt.get();
			validators.add(equalsValidator);
		}
		return this;
	}

	public Optional<EqualsValDataHolder<String>> equalsIgnoreCase(String data, List<String> allowedValues,
			ErrorDetailInterface errorCode) {
		EqualsValDataHolder<String> equalsValidator = null;
		if (CollectionUtils.isNotEmpty(allowedValues)) {
			equalsValidator = new EqualsValDataHolder<String>(data, allowedValues, errorCode);
		}
		return Optional.ofNullable(equalsValidator);
	}

	public ValidationAttribute addEqualsIgnoreCase(String data, List<String> allowedValues,
			ErrorDetailInterface errorCode) {
		Optional<EqualsValDataHolder<String>> equalsValidatorOpt = equalsIgnoreCase(data, allowedValues, errorCode);
		if (equalsValidatorOpt.isPresent()) {
			EqualsValDataHolder<String> equalsValidator = equalsValidatorOpt.get();
			validators.add(equalsValidator);
		}
		return this;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ValidationAttribute addCondition(ValidationDataHolder... validations) {
		if (validations != null && validations.length > 1) {
			conditionalValidators.add(Arrays.asList(validations));
		} else {
			throw new InvalidConfigException(
					"Invalid conditions configured. 1 matching condition & atleast 1 follow on condition is needed",
					new WearablesError(WearablesErrorCode.WEARABLES_INVALID_CONDITION));
		}
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PriorityQueue<ValidationDataHolder<? extends Object>> getValidators() {
		return validators;
	}

	public void setFieldValidators(PriorityQueue<ValidationDataHolder<? extends Object>> validators) {
		this.validators = validators;
	}

	public List<List<ValidationDataHolder<? extends Object>>> getConditionalValidators() {
		return conditionalValidators;
	}

	public void setConditionalValidators(List<List<ValidationDataHolder<? extends Object>>> conditionalValidators) {
		this.conditionalValidators = conditionalValidators;
	}

	@Override
	public String toString() {
		return "Attribute [name=" + name + ", validators=" + validators + ", conditionalValidators="
				+ conditionalValidators + "]";
	}

}
