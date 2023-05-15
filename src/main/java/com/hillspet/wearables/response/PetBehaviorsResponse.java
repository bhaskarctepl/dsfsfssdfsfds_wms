package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hillspet.wearables.dto.PetBehavior;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetBehaviorsResponse {

	private List<PetBehavior> petBehaviorList;

	public List<PetBehavior> getPetBehaviorList() {
		return petBehaviorList;
	}

	public void setPetBehaviorList(List<PetBehavior> petBehaviorList) {
		this.petBehaviorList = petBehaviorList;
	}

}
