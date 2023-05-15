package com.hillspet.wearables.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetCampaignResponse {

	private PetCampaignPointsDTO petCampaign;

	@ApiModelProperty(value = "Get Pet compaign details of pet")
	public PetCampaignPointsDTO getPetCampaign() {
		return petCampaign;
	}

	public void setPetCampaign(PetCampaignPointsDTO petCampaign) {
		this.petCampaign = petCampaign;
	}
}
