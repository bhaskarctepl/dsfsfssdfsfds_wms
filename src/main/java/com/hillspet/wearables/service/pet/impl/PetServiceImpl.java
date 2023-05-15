package com.hillspet.wearables.service.pet.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.pet.PetDao;
import com.hillspet.wearables.dto.EatingEnthusiasmScale;
import com.hillspet.wearables.dto.ImageScoringScale;
import com.hillspet.wearables.dto.PetBehavior;
import com.hillspet.wearables.dto.PetBreed;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;
import com.hillspet.wearables.dto.PetFeedingPreference;
import com.hillspet.wearables.dto.PetFeedingTime;
import com.hillspet.wearables.dto.PetMobileAppConfig;
import com.hillspet.wearables.dto.PetObservation;
import com.hillspet.wearables.dto.PetSpecies;
import com.hillspet.wearables.dto.PetWeightDTO;
import com.hillspet.wearables.dto.PetWeightHistoryDTO;
import com.hillspet.wearables.request.AddPetWeight;
import com.hillspet.wearables.request.PetAddFeedingPreferences;
import com.hillspet.wearables.request.PetAddImageScoring;
import com.hillspet.wearables.request.UpdatePet;
import com.hillspet.wearables.request.UpdatePetWeight;
import com.hillspet.wearables.response.EatingEnthusiasmScaleResponse;
import com.hillspet.wearables.response.ImageScoringScalesResponse;
import com.hillspet.wearables.response.PetBehaviorsResponse;
import com.hillspet.wearables.response.PetBreedResponse;
import com.hillspet.wearables.response.PetFeedingPreferenceResponse;
import com.hillspet.wearables.response.PetFeedingTimeResponse;
import com.hillspet.wearables.response.PetMobileAppConfigResponse;
import com.hillspet.wearables.response.PetObservationsResponse;
import com.hillspet.wearables.response.PetSpeciesResponse;
import com.hillspet.wearables.response.PetWeightHistoryResponse;
import com.hillspet.wearables.service.pet.PetService;

@Service
public class PetServiceImpl implements PetService {

	private static final Logger LOGGER = LogManager.getLogger(PetServiceImpl.class);

	@Autowired
	private PetDao petDao;

	@Override
	public PetWeightDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException {
		LOGGER.debug("addPetWeight called");
		PetWeightDTO petWeightDTO = petDao.addPetWeight(addPetWeight);
		LOGGER.debug("addPetWeight completed successfully");
		return petWeightDTO;
	}

	@Override
	public PetWeightDTO updateWeight(UpdatePetWeight updatePetWeight) throws ServiceExecutionException {
		LOGGER.debug("addPetWeight called");
		PetWeightDTO petWeightDTO = petDao.updateWeight(updatePetWeight);
		LOGGER.debug("addPetWeight completed successfully");
		return petWeightDTO;
	}

	@Override
	public void updatePetPhoto(int petId, int petParentId, String petPhoto) throws ServiceExecutionException {
		LOGGER.debug("updatePetPhoto called");
		petDao.updatePetPhoto(petId, petParentId, petPhoto);
		LOGGER.debug("updatePetPhoto completed successfully");
	}

	@Override
	public PetWeightHistoryResponse getPetWeightHistory(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetWeightHistory called");
		List<PetWeightHistoryDTO> petsWeightHistory = petDao.getPetWeightHistory(petId);
		PetWeightHistoryResponse response = new PetWeightHistoryResponse();
		response.setPetWeightHistories(petsWeightHistory);
		LOGGER.debug("getPetWeightHistory end");
		return response;
	}

	@Override
	public PetSpeciesResponse getPetSpecies() throws ServiceExecutionException {
		LOGGER.debug("getPetSpecies called");
		List<PetSpecies> speciesList = petDao.getPetSpecies();
		PetSpeciesResponse response = new PetSpeciesResponse();
		response.setSpecies(speciesList);
		LOGGER.debug("getPetSpecies end");
		return response;
	}

	@Override
	public PetBreedResponse getPetBreeds(int speciesId) throws ServiceExecutionException {
		LOGGER.debug("getPetBreeds called");
		List<PetBreed> petBreeds = petDao.getPetBreeds(speciesId);
		PetBreedResponse response = new PetBreedResponse();
		response.setPetBreedList(petBreeds);
		LOGGER.debug("getPetBreeds end");
		return response;
	}

	@Override
	public PetBehaviorsResponse getPetBehaviors(int speciesId, int behaviorTypeId) throws ServiceExecutionException {
		LOGGER.debug("getPetBehaviors called");
		List<PetBehavior> petBehaviors = petDao.getPetBehaviors(speciesId, behaviorTypeId);
		PetBehaviorsResponse response = new PetBehaviorsResponse();
		response.setPetBehaviorList(petBehaviors);
		LOGGER.debug("getPetBehaviors end");
		return response;
	}

	@Override
	public EatingEnthusiasmScaleResponse getPetEatingEnthusiasmScale() throws ServiceExecutionException {
		LOGGER.debug("getPetEatingEnthusiasmScale called");
		List<EatingEnthusiasmScale> eatingEnthusiasmScales = petDao.getPetEatingEnthusiasmScale();
		EatingEnthusiasmScaleResponse response = new EatingEnthusiasmScaleResponse();
		response.setEatingEnthusiasmScales(eatingEnthusiasmScales);
		LOGGER.debug("getPetEatingEnthusiasmScale end");
		return response;
	}

	@Override
	public PetFeedingTimeResponse getPetFeedingTime() throws ServiceExecutionException {
		LOGGER.debug("getPetFeedingTime called");
		List<PetFeedingTime> petFeedingTimes = petDao.getPetFeedingTime();
		PetFeedingTimeResponse response = new PetFeedingTimeResponse();
		response.setPetFeedingTimes(petFeedingTimes);
		LOGGER.debug("getPetFeedingTime end");
		return response;
	}

	@Override
	public PetMobileAppConfigResponse getMobileAppConfigs(int petId) throws ServiceExecutionException {
		LOGGER.debug("getMobileAppConfigs called");
		List<PetMobileAppConfig> petMobileAppConfigs = petDao.getMobileAppConfigs(petId);
		PetMobileAppConfigResponse response = new PetMobileAppConfigResponse();
		response.setPetMobileAppConfigs(petMobileAppConfigs);
		LOGGER.debug("getMobileAppConfigs end");
		return response;
	}

	@Override
	public PetFeedingEnthusiasmScale addPetFeedingTime(PetFeedingEnthusiasmScale petFeedingEnthusiasmScale)
			throws ServiceExecutionException {
		LOGGER.debug("addPetFeedingTime called");
		petFeedingEnthusiasmScale = petDao.addPetFeedingTime(petFeedingEnthusiasmScale);
		LOGGER.debug("addPetFeedingTime completed successfully");
		return petFeedingEnthusiasmScale;
	}

	@Override
	public PetObservationsResponse getPetObservationsByPetId(int petId) {
		LOGGER.debug("getPetObservationsByPetId called");
		List<PetObservation> petObservations = petDao.getPetObservationsByPetId(petId);
		PetObservationsResponse response = new PetObservationsResponse();
		response.setPetObservations(petObservations);
		LOGGER.debug("getPetObservationsByPetId end");
		return response;
	}

	@Override
	public PetObservation savePetObservation(PetObservation addPetObservation) throws ServiceExecutionException {
		LOGGER.debug("savePetObservation called");
		PetObservation petObservation = petDao.savePetObservation(addPetObservation);
		LOGGER.debug("savePetObservation completed successfully");
		return petObservation;
	}

	@Override
	public void deletePetObservation(int observationId, int petId, int petParentId) throws ServiceExecutionException {
		LOGGER.debug("deletePetObservation called");
		petDao.deletePetObservation(observationId, petId, petParentId);
		LOGGER.debug("deletePetObservation completed successfully");
	}

	@Override
	public ImageScoringScalesResponse getPetImageScoringScales(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetImageScoring called");
		List<ImageScoringScale> imageScoringScales = petDao.getPetImageScoringScales(petId);
		ImageScoringScalesResponse response = new ImageScoringScalesResponse();
		response.setImageScoringScales(imageScoringScales);
		LOGGER.debug("getPetImageScoring end");
		return response;
	}

	@Override
	public PetAddImageScoring addPetImageScoring(PetAddImageScoring addPetImageScorings)
			throws ServiceExecutionException {
		LOGGER.debug("addPetImageScoring called");
		addPetImageScorings = petDao.addPetImageScoring(addPetImageScorings);
		LOGGER.debug("addPetImageScoring completed successfully");
		return addPetImageScorings;
	}

	@Override
	public PetFeedingPreferenceResponse getPetFeedingPreferences() throws ServiceExecutionException {
		LOGGER.debug("getPetFeedingPreferences called");
		List<PetFeedingPreference> petFeedingPreferences = petDao.getPetFeedingPreferences();
		PetFeedingPreferenceResponse response = new PetFeedingPreferenceResponse();
		response.setPetFeedingPreferences(petFeedingPreferences);
		LOGGER.debug("getPetFeedingPreferences end");
		return response;
	}

	@Override
	public void addPetFeedingPreferences(PetAddFeedingPreferences petAddFeedingPreferences)
			throws ServiceExecutionException {
		LOGGER.debug("addPetFeedingPreferences called");
		petDao.addPetFeedingPreferences(petAddFeedingPreferences);
		LOGGER.debug("addPetFeedingPreferences completed successfully");
	}

	@Override
	public void updatePet(UpdatePet updatePet) throws ServiceExecutionException {
		LOGGER.debug("updatePet called");
		petDao.updatePet(updatePet);
		LOGGER.debug("updatePet completed successfully");
	}
}
