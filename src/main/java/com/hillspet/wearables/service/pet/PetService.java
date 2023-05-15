package com.hillspet.wearables.service.pet;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;
import com.hillspet.wearables.dto.PetObservation;
import com.hillspet.wearables.dto.PetWeightDTO;
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

public interface PetService {

	public PetWeightDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException;

	public PetWeightDTO updateWeight(UpdatePetWeight updatePetWeight) throws ServiceExecutionException;

	public void updatePetPhoto(int petId, int petParentId, String petPhoto) throws ServiceExecutionException;

	public PetWeightHistoryResponse getPetWeightHistory(int petId) throws ServiceExecutionException;

	public PetSpeciesResponse getPetSpecies() throws ServiceExecutionException;

	public PetBreedResponse getPetBreeds(int speciesId) throws ServiceExecutionException;

	public PetBehaviorsResponse getPetBehaviors(int speciesId, int behaviorTypeId) throws ServiceExecutionException;

	public EatingEnthusiasmScaleResponse getPetEatingEnthusiasmScale() throws ServiceExecutionException;

	public PetFeedingTimeResponse getPetFeedingTime() throws ServiceExecutionException;

	public PetMobileAppConfigResponse getMobileAppConfigs(int petId) throws ServiceExecutionException;

	public PetFeedingEnthusiasmScale addPetFeedingTime(PetFeedingEnthusiasmScale petFeedingEnthusiasmScale)
			throws ServiceExecutionException;

	public PetObservationsResponse getPetObservationsByPetId(int petId);

	public PetObservation savePetObservation(PetObservation addPetObservation) throws ServiceExecutionException;

	public void deletePetObservation(int observationId, int petId, int petParentId) throws ServiceExecutionException;

	public ImageScoringScalesResponse getPetImageScoringScales(int petId) throws ServiceExecutionException;

	public PetAddImageScoring addPetImageScoring(PetAddImageScoring addPetImageScorings)
			throws ServiceExecutionException;

	public PetFeedingPreferenceResponse getPetFeedingPreferences() throws ServiceExecutionException;

	public void addPetFeedingPreferences(PetAddFeedingPreferences petAddFeedingPreferences)
			throws ServiceExecutionException;

	public void updatePet(UpdatePet updatePet) throws ServiceExecutionException;
}
