
/**
 * Created Date: 08-Dec-2020
 * Class Name  : CustomerSupportImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Dec-2020        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.jaxrs.resource.impl;

import java.util.Arrays;
import java.util.List;

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
import com.hillspet.wearables.dto.Campaign;
import com.hillspet.wearables.dto.LeaderBoard;
import com.hillspet.wearables.dto.MobileAppFeedback;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetRedemptionHistoryDTO;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.jaxrs.resource.MobileAppResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AssignSensorRequest;
import com.hillspet.wearables.request.QuestionAnswerRequest;
import com.hillspet.wearables.response.CampaignListResponse;
import com.hillspet.wearables.response.LeaderBoardResponse;
import com.hillspet.wearables.response.MobileAppFeedbackResponse;
import com.hillspet.wearables.response.PetCampaignListResponse;
import com.hillspet.wearables.response.PetCampaignResponse;
import com.hillspet.wearables.response.PetDevicesResponse;
import com.hillspet.wearables.response.PetRedemptionHistoryResponse;
import com.hillspet.wearables.response.QuestionnaireListResponse;
import com.hillspet.wearables.service.questionnaire.MobileAppService;

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
 * @author vvodyaram
 * @version Wearables Portal Relase Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Service
public class MobileAppResourceImpl implements MobileAppResource {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppResourceImpl.class);

	@Autowired
	private MobileAppService mobileAppService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response getFeedbackQuestionnaireByPetId(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getQuestionnaireByPetId service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		List<Questionnaire> questionnaireList = mobileAppService.getFeedbackQuestionnaireByPetId(petId);
		QuestionnaireListResponse response = new QuestionnaireListResponse();
		response.setQuestionnaireList(questionnaireList);
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireByPetId(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getQuestionnaireByPetId service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		List<Questionnaire> questionnaireList = mobileAppService.getQuestionnaireByPetId(petId);
		QuestionnaireListResponse response = new QuestionnaireListResponse();
		response.setQuestionnaireList(questionnaireList);
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getQuestionnaireAnswers(int petId, int questionnaireId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getQuestionnaireAnswers service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		List<Questionnaire> questionnaireList = mobileAppService.getQuestionnaireAnswers(petId, questionnaireId);
		QuestionnaireListResponse response = new QuestionnaireListResponse();
		response.setQuestionnaireList(questionnaireList);
		SuccessResponse<QuestionnaireListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response saveQuestionAnswers(QuestionAnswerRequest questionAnswerRequest, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("saveQuestionAnswers service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		mobileAppService.saveQuestionAnswers(questionAnswerRequest);
		CommonResponse response = new CommonResponse();
		response.setMessage("Your answers has been saved successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetCampaignPoints(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetCampaignPoints service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		PetCampaignPointsDTO petCampaignDTO = mobileAppService.getPetCampaignPoints(petId);
		PetCampaignResponse response = new PetCampaignResponse();
		response.setPetCampaign(petCampaignDTO);
		SuccessResponse<PetCampaignResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetCampaignPointsList(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetCampaignPointsList service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		PetCampaignListResponse response = mobileAppService.getPetCampaignPointsList(petId);
		SuccessResponse<PetCampaignListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getCampaignListByPet(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getCampaignListByPet service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		List<Campaign> campaigns = mobileAppService.getCampaignListByPet(petId);
		CampaignListResponse response = new CampaignListResponse();
		response.setCampaigns(campaigns);
		SuccessResponse<CampaignListResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getLeaderBoardByCampaignId(int campaignId, int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getLeaderBoardByCampaignId service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		List<LeaderBoard> leaderBoards = mobileAppService.getLeaderBoardByCampaignId(campaignId);
		LeaderBoardResponse response = new LeaderBoardResponse();

		response.setCurrentPet(
				leaderBoards.stream().filter(obj -> obj.getPetId().equals(petId)).findFirst().orElse(null));

		response.setLeaderBoards(leaderBoards);
		SuccessResponse<LeaderBoardResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetRedemptionHistory(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetRedemptionHistory service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		List<PetRedemptionHistoryDTO> redemptionHistoryDTOs = mobileAppService.getPetRedemptionHistory(petId);
		PetRedemptionHistoryResponse response = new PetRedemptionHistoryResponse();
		response.setRedemptionHistoryList(redemptionHistoryDTOs);

		SuccessResponse<PetRedemptionHistoryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response assignSensorToPet(AssignSensorRequest assignSensorRequest, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("assignSensorToPet service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}
		mobileAppService.assignSensorToPet(assignSensorRequest);
		CommonResponse response = new CommonResponse();
		response.setMessage("Sensor has been assigned successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetDevicesByPetParent(int petParentId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetRedemptionHistory service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		List<PetDTO> petDevices = mobileAppService.getPetDevicesByPetParent(petParentId);
		PetDevicesResponse response = new PetDevicesResponse();
		response.setPetDevices(petDevices);

		SuccessResponse<PetDevicesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getFeedbackByPetParent(int petParentId, String token) {
		LOGGER.debug("getFeedbackByPetParent called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetRedemptionHistory service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		List<MobileAppFeedback> feedbacks = mobileAppService.getFeedbackByPetParent(petParentId);
		MobileAppFeedbackResponse response = new MobileAppFeedbackResponse();
		response.setMobileAppFeeback(feedbacks);

		SuccessResponse<MobileAppFeedbackResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceTypes(String token) {
		LOGGER.debug("getDeviceType called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetRedemptionHistory service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		List<String> deviceTypeList = mobileAppService.getDeviceTypes();
		SuccessResponse<List<String>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(deviceTypeList);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getDeviceModels(String deviceType, String token) {
		LOGGER.debug("getDeviceModel called");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetRedemptionHistory service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		List<String> deviceModelList = mobileAppService.getDeviceModels(deviceType);
		SuccessResponse<List<String>> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(deviceModelList);
		return responseBuilder.buildResponse(successResponse);
	}
}
