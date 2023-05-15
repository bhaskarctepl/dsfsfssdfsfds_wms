package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PetMobileAppConfig;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetMobileAppConfigResponse {

	private List<PetMobileAppConfig> petMobileAppConfigs;

	public List<PetMobileAppConfig> getPetMobileAppConfigs() {
		return petMobileAppConfigs;
	}

	public void setPetMobileAppConfigs(List<PetMobileAppConfig> petMobileAppConfigs) {
		this.petMobileAppConfigs = petMobileAppConfigs;
	}

}
