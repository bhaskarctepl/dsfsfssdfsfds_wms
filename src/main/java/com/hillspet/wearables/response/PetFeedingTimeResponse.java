package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetFeedingTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetFeedingTimeResponse {
	private List<PetFeedingTime> petFeedingTimes;

	public List<PetFeedingTime> getPetFeedingTimes() {
		return petFeedingTimes;
	}

	public void setPetFeedingTimes(List<PetFeedingTime> petFeedingTimes) {
		this.petFeedingTimes = petFeedingTimes;
	}

}
