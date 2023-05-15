package com.hillspet.wearables.dao.pet;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
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

public interface PetDao {

	PetWeightDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException;

	PetWeightDTO updateWeight(UpdatePetWeight updatePetWeight) throws ServiceExecutionException;
	
	void updatePetPhoto(int petId, int petParentId, String petPhoto) throws ServiceExecutionException;

	List<PetWeightHistoryDTO> getPetWeightHistory(int petId) throws ServiceExecutionException;

	List<PetSpecies> getPetSpecies() throws ServiceExecutionException;

	List<PetBreed> getPetBreeds(int speciesId) throws ServiceExecutionException;

	List<PetBehavior> getPetBehaviors(int speciesId, int behaviorTypeId) throws ServiceExecutionException;

	List<EatingEnthusiasmScale> getPetEatingEnthusiasmScale() throws ServiceExecutionException;

	List<PetFeedingTime> getPetFeedingTime() throws ServiceExecutionException;

	List<PetMobileAppConfig> getMobileAppConfigs(int petId) throws ServiceExecutionException;

	PetFeedingEnthusiasmScale addPetFeedingTime(PetFeedingEnthusiasmScale petFeedingEnthusiasmScale)
			throws ServiceExecutionException;

	List<PetObservation> getPetObservationsByPetId(int petId) throws ServiceExecutionException;

	PetObservation savePetObservation(PetObservation addPetObservation) throws ServiceExecutionException;

	void deletePetObservation(int observationId, int petId, int petParentId) throws ServiceExecutionException;

	List<ImageScoringScale> getPetImageScoringScales(int petId) throws ServiceExecutionException;

	PetAddImageScoring addPetImageScoring(PetAddImageScoring addPetImageScorings) throws ServiceExecutionException;

	List<PetFeedingPreference> getPetFeedingPreferences() throws ServiceExecutionException;

	void addPetFeedingPreferences(PetAddFeedingPreferences petAddFeedingPreferences) throws ServiceExecutionException;

	void updatePet(UpdatePet updatePet) throws ServiceExecutionException;
}
