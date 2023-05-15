package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetWeightDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetWeightResponse {
	
	private PetWeightDTO petWeightDTO;

	@ApiModelProperty(value = "Get Pet details of particular id")
	public PetWeightDTO getPetWeightDTO() {
		return petWeightDTO;
	}

	public void setPetWeightDTO(PetWeightDTO petWeightDTO) {
		this.petWeightDTO = petWeightDTO;
	}
}
