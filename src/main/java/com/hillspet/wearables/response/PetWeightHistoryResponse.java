package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PetWeightHistoryDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetWeightHistoryResponse {

	private List<PetWeightHistoryDTO> petWeightHistories;

	public List<PetWeightHistoryDTO> getPetWeightHistories() {
		return petWeightHistories;
	}

	public void setPetWeightHistories(List<PetWeightHistoryDTO> petWeightHistories) {
		this.petWeightHistories = petWeightHistories;
	}

}
