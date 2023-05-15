package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.PetDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDevicesResponse {

	private List<PetDTO> petDevices;

	public List<PetDTO> getPetDevices() {
		return petDevices;
	}

	public void setPetDevices(List<PetDTO> petDevices) {
		this.petDevices = petDevices;
	}

}
