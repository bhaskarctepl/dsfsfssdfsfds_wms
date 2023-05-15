package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetFeedingEnthusiasmScaleResponse {

	private PetFeedingEnthusiasmScale petFeedingEnthusiasmScale;

	@ApiModelProperty(value = "Get Pet Feeding Enthusiasm Scale Details")
	public PetFeedingEnthusiasmScale getPetFeedingEnthusiasmScale() {
		return petFeedingEnthusiasmScale;
	}

	public void setPetFeedingEnthusiasmScale(PetFeedingEnthusiasmScale petFeedingEnthusiasmScale) {
		this.petFeedingEnthusiasmScale = petFeedingEnthusiasmScale;
	}
}
