package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetFeedingPreference;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetFeedingPreferenceResponse {
	private List<PetFeedingPreference> petFeedingPreferences;

	public List<PetFeedingPreference> getPetFeedingPreferences() {
		return petFeedingPreferences;
	}

	public void setPetFeedingPreferences(List<PetFeedingPreference> petFeedingPreferences) {
		this.petFeedingPreferences = petFeedingPreferences;
	}

}
