package com.hillspet.wearables.jaxrs.resource.impl;

import java.io.IOException;
import java.util.Arrays;

import javax.validation.Valid;
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
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;
import com.hillspet.wearables.dto.PetObservation;
import com.hillspet.wearables.dto.PetWeightDTO;
import com.hillspet.wearables.jaxrs.resource.PetResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AddPetWeight;
import com.hillspet.wearables.request.PetAddFeedingPreferences;
import com.hillspet.wearables.request.PetAddImageScoring;
import com.hillspet.wearables.request.UpdatePet;
import com.hillspet.wearables.request.UpdatePetWeight;
import com.hillspet.wearables.response.EatingEnthusiasmScaleResponse;
import com.hillspet.wearables.response.ImageScoringScalesResponse;
import com.hillspet.wearables.response.PetBehaviorsResponse;
import com.hillspet.wearables.response.PetBreedResponse;
import com.hillspet.wearables.response.PetFeedingEnthusiasmScaleResponse;
import com.hillspet.wearables.response.PetFeedingPreferenceResponse;
import com.hillspet.wearables.response.PetFeedingTimeResponse;
import com.hillspet.wearables.response.PetMobileAppConfigResponse;
import com.hillspet.wearables.response.PetObservationResponse;
import com.hillspet.wearables.response.PetObservationsResponse;
import com.hillspet.wearables.response.PetSpeciesResponse;
import com.hillspet.wearables.response.PetWeightHistoryResponse;
import com.hillspet.wearables.response.PetWeightResponse;
import com.hillspet.wearables.service.pet.PetService;
import com.hillspet.wearables.service.questionnaire.MobileAppService;

/**
 * This class providing Pet details.
 * 
 * @author sgorle
 * @since w2.0
 * @version w2.0
 * @version Dec 8, 2020
 */
@Service
public class PetResourceImpl implements PetResource {

	private static final Logger LOGGER = LogManager.getLogger(PetResourceImpl.class);

	@Autowired
	private PetService petService;

	@Autowired
	private MobileAppService mobileAppService;

	@Autowired
	private JaxrsJsonResponseBuilder responseBuilder;

	@Override
	public Response addWeight(AddPetWeight addPetWeight, String token) {
		LOGGER.debug("addWeight called in PetResourceImpl");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("addWeight service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		// addPetWeight.setUserId(petParentKeyId);
		PetWeightDTO petWeightDTO = petService.addPetWeight(addPetWeight);
		PetWeightResponse response = new PetWeightResponse();
		response.setPetWeightDTO(petWeightDTO);

		// Build a successful response
		SuccessResponse<PetWeightResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updateWeight(UpdatePetWeight updatePetWeight, String token) {
		LOGGER.debug("updateWeight called in PetResourceImpl");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("updateWeight service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		// addPetWeight.setUserId(petParentKeyId);
		PetWeightDTO petWeightDTO = petService.updateWeight(updatePetWeight);
		PetWeightResponse response = new PetWeightResponse();
		response.setPetWeightDTO(petWeightDTO);

		// Build a successful response
		SuccessResponse<PetWeightResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetWeightHistory(int petId, String token) throws IOException {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getPetWeightHistory service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetWeightHistoryResponse response = petService.getPetWeightHistory(petId);
		SuccessResponse<PetWeightHistoryResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetSpecies(String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getPetSpecies service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetSpeciesResponse petSpeciesResponse = petService.getPetSpecies();
		SuccessResponse<PetSpeciesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(petSpeciesResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetBreeds(int speciesId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getPetWeightHistory service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetBreedResponse response = petService.getPetBreeds(speciesId);
		SuccessResponse<PetBreedResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetBehaviors(int speciesId, String token, int behaviorTypeId) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getPetBehaviors service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetBehaviorsResponse response = petService.getPetBehaviors(speciesId, behaviorTypeId);
		SuccessResponse<PetBehaviorsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetEatingEnthusiasmScale(String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetEatingEnthusiasmScale service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		EatingEnthusiasmScaleResponse eatingEnthusiasmScaleResponse = petService.getPetEatingEnthusiasmScale();
		SuccessResponse<EatingEnthusiasmScaleResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(eatingEnthusiasmScaleResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetFeedingTime(String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getPetFeedingTime service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetFeedingTimeResponse petFeedingTimeResponse = petService.getPetFeedingTime();
		SuccessResponse<PetFeedingTimeResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(petFeedingTimeResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getMobileAppConfigs(int petId, String token) throws IOException {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getMobileAppConfigs service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetMobileAppConfigResponse response = petService.getMobileAppConfigs(petId);
		SuccessResponse<PetMobileAppConfigResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addPetFeedingTime(PetFeedingEnthusiasmScale petFeedingEnthusiasmScale, String token) {
		LOGGER.debug("addPetFeedingTime called in PetResourceImpl");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("addPetFeedingTime service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		petFeedingEnthusiasmScale = petService.addPetFeedingTime(petFeedingEnthusiasmScale);
		PetFeedingEnthusiasmScaleResponse response = new PetFeedingEnthusiasmScaleResponse();
		response.setPetFeedingEnthusiasmScale(petFeedingEnthusiasmScale);

		// Build a successful response
		SuccessResponse<PetFeedingEnthusiasmScaleResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
	

	@Override
	public Response getPetObservationsByPetId(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getPetObservationsByPetId service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetObservationsResponse response = petService.getPetObservationsByPetId(petId);
		SuccessResponse<PetObservationsResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response savePetObservation(PetObservation addPetObservation, String token) {
		LOGGER.debug("savePetObservation called in PetResourceImpl");
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("savePetObservation service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetObservation petObservation = petService.savePetObservation(addPetObservation);
		PetObservationResponse response = new PetObservationResponse();
		response.setPetObservation(petObservation);

		// Build a successful response
		SuccessResponse<PetObservationResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response deletePetObservation(int observationId, int petId, int petParentId, String token) {
		LOGGER.debug("deletePetObservation called in PetResourceImpl");

		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"deletePetObservation service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		petService.deletePetObservation(observationId, petId, petParentId);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Pet observation has been deleted successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetImageScoringScales(int petId, String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("getPetImageScoring service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		ImageScoringScalesResponse response = petService.getPetImageScoringScales(petId);
		SuccessResponse<ImageScoringScalesResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addPetImageScoring(PetAddImageScoring addPetImageScorings, String token) {
		LOGGER.debug("addPetImageScoring called in PetResourceImpl");

		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException("addPetImageScoring service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		petService.addPetImageScoring(addPetImageScorings);

		// Step 5: build a successful response
		CommonResponse response = new CommonResponse();
		response.setMessage("Pet Image Scoring has been added successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response getPetFeedingPreferences(String token) {
		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"getPetFeedingPreferences service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		PetFeedingPreferenceResponse petFeedingPreferencesResponse = petService.getPetFeedingPreferences();
		SuccessResponse<PetFeedingPreferenceResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(petFeedingPreferencesResponse);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response addPetFeedingPreferences(PetAddFeedingPreferences petAddFeedingPreferences, String token) {
		LOGGER.debug("addPetFeedingPreferences called in PetResourceImpl");

		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"addPetFeedingPreferences service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		petService.addPetFeedingPreferences(petAddFeedingPreferences);

		CommonResponse response = new CommonResponse();
		response.setMessage("Pet Feeding Preferences has been added successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}

	@Override
	public Response updatePet(UpdatePet updatePet, String token) {
		LOGGER.debug("updatePet called in PetResourceImpl");

		int petParentKeyId = mobileAppService.getPetParentByPetParentKey(token);
		if (petParentKeyId == 0) {
			throw new ServiceValidationException(
					"updatePet service validation failed cannot proceed further",
					Arrays.asList(new WearablesError(WearablesErrorCode.AUTHORIZATION_FAILED)));
		}

		petService.updatePet(updatePet);

		CommonResponse response = new CommonResponse();
		response.setMessage("Pet has been updated successfully");
		SuccessResponse<CommonResponse> successResponse = new SuccessResponse<>();
		successResponse.setServiceResponse(response);
		return responseBuilder.buildResponse(successResponse);
	}
}
