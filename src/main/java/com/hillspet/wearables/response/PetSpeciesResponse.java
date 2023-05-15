package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetSpecies;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetSpeciesResponse {
	private List<PetSpecies> species;

	public List<PetSpecies> getSpecies() {
		return species;
	}

	public void setSpecies(List<PetSpecies> species) {
		this.species = species;
	}

}
