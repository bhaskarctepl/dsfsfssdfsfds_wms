package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PetBreed;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetBreedResponse {
	
	private List<PetBreed> petBreedList;

	public List<PetBreed> getPetBreedList() {
		return petBreedList;
	}

	public void setPetBreedList(List<PetBreed> petBreedList) {
		this.petBreedList = petBreedList;
	}
	
	

}
