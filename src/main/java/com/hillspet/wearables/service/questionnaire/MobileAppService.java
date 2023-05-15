package com.hillspet.wearables.service.questionnaire;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.Campaign;
import com.hillspet.wearables.dto.LeaderBoard;
import com.hillspet.wearables.dto.MobileAppFeedback;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetRedemptionHistoryDTO;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.request.AssignSensorRequest;
import com.hillspet.wearables.request.QuestionAnswerRequest;
import com.hillspet.wearables.response.PetCampaignListResponse;

public interface MobileAppService {

	List<Questionnaire> getFeedbackQuestionnaireByPetId(int petId) throws ServiceExecutionException;

	List<Questionnaire> getQuestionnaireByPetId(int petId) throws ServiceExecutionException;

	List<Questionnaire> getQuestionnaireAnswers(int petId, int questionnaireId) throws ServiceExecutionException;

	void saveQuestionAnswers(QuestionAnswerRequest questionAnswerRequest) throws ServiceExecutionException;

	PetCampaignPointsDTO getPetCampaignPoints(int petId) throws ServiceExecutionException;

	PetCampaignListResponse getPetCampaignPointsList(int petId) throws ServiceExecutionException;

	int getPetParentByPetParentKey(String petParentKey) throws ServiceExecutionException;

	List<Campaign> getCampaignListByPet(int petId) throws ServiceExecutionException;

	List<LeaderBoard> getLeaderBoardByCampaignId(int campaignId) throws ServiceExecutionException;

	List<PetRedemptionHistoryDTO> getPetRedemptionHistory(int petId) throws ServiceExecutionException;

	void assignSensorToPet(AssignSensorRequest assignSensorRequest) throws ServiceExecutionException;

	List<PetDTO> getPetDevicesByPetParent(int petParentId) throws ServiceExecutionException;

	List<MobileAppFeedback> getFeedbackByPetParent(int petParentId) throws ServiceExecutionException;

	List<String> getDeviceTypes() throws ServiceExecutionException;

	List<String> getDeviceModels(String deviceType) throws ServiceExecutionException;

}
