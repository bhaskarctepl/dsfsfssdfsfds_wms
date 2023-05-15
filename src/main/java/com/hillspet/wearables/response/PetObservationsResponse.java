package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetObservation;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetObservationsResponse {

	private List<PetObservation> petObservations;

	public List<PetObservation> getPetObservations() {
		return petObservations;
	}

	public void setPetObservations(List<PetObservation> petObservations) {
		this.petObservations = petObservations;
	}
}
