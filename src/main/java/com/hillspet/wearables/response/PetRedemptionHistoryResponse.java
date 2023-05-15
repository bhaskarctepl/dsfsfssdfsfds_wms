package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetRedemptionHistoryDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetRedemptionHistoryResponse {

	private List<PetRedemptionHistoryDTO> redemptionHistoryList;

	public List<PetRedemptionHistoryDTO> getRedemptionHistoryList() {
		return redemptionHistoryList;
	}

	public void setRedemptionHistoryList(List<PetRedemptionHistoryDTO> redemptionHistoryList) {
		this.redemptionHistoryList = redemptionHistoryList;
	}

}
