package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetCampaignPointsListDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetCampaignListResponse {

	private List<PetCampaignPointsListDTO> petCampaignList;

	public List<PetCampaignPointsListDTO> getPetCampaignList() {
		return petCampaignList;
	}

	public void setPetCampaignList(List<PetCampaignPointsListDTO> petCampaignList) {
		this.petCampaignList = petCampaignList;
	}
}
