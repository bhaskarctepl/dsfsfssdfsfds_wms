package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetObservation;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetObservationResponse {

	private PetObservation petObservation;

	@ApiModelProperty(value = "Get Observation details of pet")
	public PetObservation getPetObservation() {
		return petObservation;
	}

	public void setPetObservation(PetObservation petObservation) {
		this.petObservation = petObservation;
	}

}
