
/**
 * Created Date: 08-Jan-2021
 * Class Name  : QuestionnaireServiceImpl.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          08-Jan-2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.service.questionnaire.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.questionnaire.MobileAppDao;
import com.hillspet.wearables.dto.Campaign;
import com.hillspet.wearables.dto.LeaderBoard;
import com.hillspet.wearables.dto.MobileAppFeedback;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;
import com.hillspet.wearables.dto.PetCampaignPointsListDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetRedemptionHistoryDTO;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.request.AssignSensorRequest;
import com.hillspet.wearables.request.QuestionAnswerRequest;
import com.hillspet.wearables.response.PetCampaignListResponse;
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
 * @version Wearables Portal Release Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Service
public class MobileAppServiceImpl implements MobileAppService {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppServiceImpl.class);

	@Autowired
	private MobileAppDao mobileAppDao;

	@Override
	public List<Questionnaire> getFeedbackQuestionnaireByPetId(int petId) throws ServiceExecutionException {
		LOGGER.debug("getFeedbackQuestionnaireByPetId called");
		List<Questionnaire> questionnaireList = mobileAppDao.getFeedbackQuestionnaireByPetId(petId);
		LOGGER.debug("getFeedbackQuestionnaireByPetId completed successfully");
		return questionnaireList;
	}
	
	@Override
	public List<Questionnaire> getQuestionnaireByPetId(int petId) throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireByPetId called");
		List<Questionnaire> questionnaireList = mobileAppDao.getQuestionnaireByPetId(petId);
		LOGGER.debug("getQuestionnaireByPetId completed successfully");
		return questionnaireList;
	}

	@Override
	public List<Questionnaire> getQuestionnaireAnswers(int petId, int questionnaireId)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireByPetId called");
		List<Questionnaire> questionnaireList = mobileAppDao.getQuestionnaireAnswers(petId, questionnaireId);
		LOGGER.debug("getQuestionnaireByPetId completed successfully");
		return questionnaireList;
	}

	@Override
	public void saveQuestionAnswers(QuestionAnswerRequest questionAnswerRequest) throws ServiceExecutionException {
		LOGGER.debug("saveQuestionAnswers called");
		mobileAppDao.saveQuestionAnswers(questionAnswerRequest);
		LOGGER.debug("saveQuestionAnswers completed successfully");
	}

	@Override
	public PetCampaignPointsDTO getPetCampaignPoints(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetCampaignPoints called");
		PetCampaignPointsDTO petDTO = mobileAppDao.getPetCampaignPoints(petId);
		LOGGER.debug("getPetCampaignPoints completed successfully");
		return petDTO;
	}

	@Override
	public PetCampaignListResponse getPetCampaignPointsList(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetCampaignList called");
		List<PetCampaignPointsListDTO> campaignList = mobileAppDao.getPetCampaignPointsList(petId);
		PetCampaignListResponse response = new PetCampaignListResponse();
		response.setPetCampaignList(campaignList);
		LOGGER.debug("getPetCampaignList end");
		return response;
	}

	@Override
	public int getPetParentByPetParentKey(String petParentKey) throws ServiceExecutionException {
		LOGGER.debug("getPetParentByPetParentKey called");
		int petParentKeyId = mobileAppDao.getPetParentByPetParentKey(petParentKey);
		LOGGER.debug("getPetParentByPetParentKey end");
		return petParentKeyId;
	}

	@Override
	public List<Campaign> getCampaignListByPet(int petId) throws ServiceExecutionException {
		LOGGER.debug("getCampaignListByPet called");
		List<Campaign> campaigns = mobileAppDao.getCampaignListByPet(petId);
		LOGGER.debug("getCampaignListByPet end");
		return campaigns;
	}

	@Override
	public List<LeaderBoard> getLeaderBoardByCampaignId(int campaignId) throws ServiceExecutionException {
		LOGGER.debug("getLeaderBoardByCampaignId called");
		List<LeaderBoard> leaderBoards = mobileAppDao.getLeaderBoardByCampaignId(campaignId);
		LOGGER.debug("getLeaderBoardByCampaignId end");
		return leaderBoards;
	}

	@Override
	public List<PetRedemptionHistoryDTO> getPetRedemptionHistory(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetRedemptionHistory called");
		List<PetRedemptionHistoryDTO> pRedemptionHistoryDTOs = mobileAppDao.getPetRedemptionHistory(petId);
		LOGGER.debug("getPetRedemptionHistory end");
		return pRedemptionHistoryDTOs;
	}

	@Override
	public void assignSensorToPet(AssignSensorRequest assignSensorRequest) throws ServiceExecutionException {
		LOGGER.debug("assignSensorToPet called");
		mobileAppDao.assignSensorToPet(assignSensorRequest);
		LOGGER.debug("assignSensorToPet completed successfully");
	}
	
	@Override
	public List<PetDTO> getPetDevicesByPetParent(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("getPetDevicesByPetParent called");
		List<PetDTO> petDevices = mobileAppDao.getPetDevicesByPetParent(petParentId);
		LOGGER.debug("getPetDevicesByPetParent end");
		return petDevices;
	}

	@Override
	public List<MobileAppFeedback> getFeedbackByPetParent(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("getFeedbackByPetParent called");
		List<MobileAppFeedback> petDevices = mobileAppDao.getFeedbackByPetParent(petParentId);
		LOGGER.debug("getFeedbackByPetParent end");
		return petDevices;
	}

	@Override
	public List<String> getDeviceTypes() throws ServiceExecutionException {
		LOGGER.debug("getDeviceType called");
		List<String> deviceTypeList = mobileAppDao.getDeviceTypes();
		LOGGER.debug("getDeviceType list", deviceTypeList.size());
		return deviceTypeList;
	}

	@Override
	public List<String> getDeviceModels(String deviceType) throws ServiceExecutionException {
		LOGGER.debug("getDeviceModel called");
		List<String> deviceTypeList = mobileAppDao.getDeviceModels(deviceType);
		LOGGER.debug("getDeviceModel list", deviceTypeList.size());
		return deviceTypeList;
	}
	
}
