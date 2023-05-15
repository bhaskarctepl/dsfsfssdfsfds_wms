package com.hillspet.wearables.common.constants;

public enum WearablesMessageKey implements MessageKeyInterface {

	SERVICE_RESP_INVALID("wearables.service.response.invalid"),
	INTERNAL_SERVER_ERROR("wearables.internal.server.error"),
	WEARABLES_WEB_APP_EXCEPTION("wearables.service.web.application.unexpected"),
	INVALID_JSON_INPUT("wearables.service.request.json.invalid"),
	READ_TIME_OUT("integration.service.read.timeout"),
	CONNECTION_TIME_OUT("integration.service.connection.timeout"),
	
	ORDER_NUMBER_REQUIRED("service.customer.orderNumber.required"),
	FIRST_NAME_REQUIRED("service.customer.firstName.required"),
	PASSWORD_REQUIRED("service.customer.password.required"),
	NEW_PASSWORD_REQUIRED("service.customer.newPassword.required"),
	CONFIRM_PASSWORD_REQUIRED("service.customer.confirmPassword.required"),
	EMAIL_REQUIRED("service.customer.email.required"),
	MOBILE_NUMBER_REQUIRED("service.customer.phoneNumber.required"),
	USER_ID_REQUIRED("service.customer.userId.required"),
	
	VERIFICATION_CODE_REQUIRED("service.customer.verification.code.required"),
	CLIENT_ID_REQUIERD("service.client.id.required"),
	LAST_NAME_REQUIRED("service.client.last.name.required"),
	PHONE_NUMBER_REQUIRED("service.phone.no.required"),	
	PET_ID_REQUIRED("service.pet.id.required"),
	CATEGORY_REQUIRED("service.category.required"),
	DEVICE_NUMBER_REQUIRED("service.device.number.required"),
	DURATION_REQUIRED("service.duration.required"),
	TIMER_DATE_REQUIRED("service.timer.date.required"),
	PAGE_NAME_REQUIRED("service.page.name.required"),
	DEVICE_TYPE_REQUIRED("service.device.type.required"),
	FEEDBACK_TEXT_REQUIRED("service.feedback.text.required"),
	
	SETUP_STATUS_REQUIRED("service.setup.status.required"),
	SSID_REQUIRED("service.ssid.required"),
	NOTIFICATION_TYPE_REQUIRED("service.notification.type.required"),
	PET_NAME_REQUIRED("service.pet.name.required"),
	PET_GENDER_REQUIRED("service.pet.gender.required"),
	IS_NEUTERED_REQUIRED("service.is.neutered.required"),
	WEIGHT_REQUIRED("service.weight.required"),
	WEIGHT_UNIT_REQUIRED("service.weight.unit.required"),
	IS_MIXED_REQUIRED("service.is.mixed.required"),
	BREED_ID_REQUIRED("service.breed.id.required"),
	DEVICE_ADD_DATE_REQUIRED("service.device.add.date.required"),
	
	
	INVALID_EMAIL("service.customer.invalid.email"),
	INVALID_PASSWORD("service.customer.invalid.password"),
	INVALID_PASSWORD_LENGTH("service.customer.invalid.password.length"),
	INVALID_NEW_PASSWORD("service.customer.invalid.newPassword"),
	INVALID_NEW_PASSWORD_LENGTH("service.customer.invalid.newPassword.length"),
	INVALID_CONFIRM_PASSWORD("service.customer.invalid.confirmPassword"),
	INVALID_CONFIRM_PASSWORD_LENGTH("service.customer.invalid.confirmPassword.length"),
	INVALID_PASSWORD_NEW_PASSWORD("service.customer.old.new.password.invalid"),
	INVALID_NEW_CONFIRM_PASSWORD("service.customer.new.confirm.password.invalid"),
	INCORRECT_PASSWORD("service.customer.incorrect.password"),
	
	INVALID_DEVICE_NUMBER("service.asset.invalid.deviceNumber"),
	INVALID_STUDY_VIRTUAL_STUDY_ALREADY_MAPPED("service.asset.invalid.study.virtual.study.already.mapped"),
	
	USERNAME_NOT_FOUND("service.customer.email.notFound"),
	
	APP_ID_NOT_FOUND("request.header.WEARABLES_APP_ID.notFound"),
	APP_ID_NOT_VALID("request.header.WEARABLES_APP_ID.invalid"),
	APP_ID_BLANK("request.header.WEARABLES_APP_ID.required"),
	APP_ENV_NOT_VALID("request.header.WEARABLES_APP_ENV.invalid"),
	APP_ENV_BLANK("request.header.WEARABLES_APP_ENV.required"),
	CONTENT_TYPE_INVALID("request.header.Content-Type.invalid"),
	APP_VERSION_INVALID("request.header.App_Version.invalid"),
	APP_VERSION_INVALID_LENGTH("request.header.APP_VERSION.length"),
	APP_VERSION_BLANK("request.header.App_Version.required"),
	USERID_INVALID_LENGTH("request.header.WEARABLES_USR_ID.length"),
	USERID_INVALID("request.header.WEARABLES_USR_ID.invalid"),
	USR_ID_REQUIRED("request.header.WEARABLES_USR_ID.required"),
	AUTHENTICATION_FAILED("request.service.authentication.failed"),
	AUTHORIZATION_FAILED("request.service.authorization.failed"),
	WEARABLES_VAL_ERROR("wearables.conditional.validation.failure"),
	CACHE_REGION_REQUIRED("cache.region.required"),
	CACHE_REGION_INVALID("cache.region.invalid"),
	CACHE_REGION_KEY_INVALID("cache.region.key.invalid"),

	EXEC_SVC_INPROGRESS("executor.service.inprogress");

	private String value;

	private WearablesMessageKey(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}
}
