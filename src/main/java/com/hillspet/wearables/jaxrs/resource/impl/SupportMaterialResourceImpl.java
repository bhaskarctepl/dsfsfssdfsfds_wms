package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.Arrays;

import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.builders.JaxrsJsonResponseBuilder;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.jaxrs.resource.SupportMaterialResource;
import com.hillspet.wearables.response.MaterialCategoriesResponse;
import com.hillspet.wearables.response.MaterialTypesResponse;
import com.hillspet.wearables.response.SupportMaterialsResponse;
import com.hillspet.wearables.service.questionnaire.MobileAppService;
import com.hillspet.wearables.service.supportmaterial.SupportMaterialService;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author sgorle
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */

@Service
public class SupportMaterialResourceImpl implements SupportMaterialResource {

	private static final Logger LOGGER = LogManager.getLogger(SupportMaterialResourceImpl.class);

	@Autowired
	private MobileAppService mobileAppService;

	@Autowired
	private SupportMaterialService supportMaterialService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getMaterialTypeList(String token) {
		LOGGER.debug("getMaterialTypeList called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getMaterialTypeList service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		MaterialTypesResponse materialTypeList = supportMaterialService.getMaterialTypeList();
		SuccessResponse<MaterialTypesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(materialTypeList);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getMaterialCategoryList(int categoryId, String token) {
		LOGGER.debug("getMaterialCategoryList called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getMaterialCategoryList service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		MaterialCategoriesResponse deviceCategoryList = supportMaterialService.getMaterialCategoryList(categoryId);
		SuccessResponse<MaterialCategoriesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(deviceCategoryList);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceSupportDocs(String deviceType, String deviceModel, String token) {
		LOGGER.debug("getDeviceSupportDocs called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getDeviceSupportDocs service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		SupportMaterialsResponse deviceSupportDocsList = supportMaterialService.getSupportMaterials(1, deviceType,
				deviceModel, 0, 0);
		SuccessResponse<SupportMaterialsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(deviceSupportDocsList);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getAppSupportDocs(int subCategoryType, String token) {
		LOGGER.debug("getAppSupportDocs called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getAppSupportDocs service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		SupportMaterialsResponse appSupportDocsList = supportMaterialService.getSupportMaterials(1, null, null,
				subCategoryType, 0);
		SuccessResponse<SupportMaterialsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(appSupportDocsList);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getSupportDocs(String token) {
		LOGGER.debug("getSupportDocs called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getSupportDocs service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		SupportMaterialsResponse supportDocsList = supportMaterialService.getSupportMaterials(0, null, null, 0, 0);
		SuccessResponse<SupportMaterialsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(supportDocsList);
		return responseBuilder.buildResponse(successResponse);
	}

}
