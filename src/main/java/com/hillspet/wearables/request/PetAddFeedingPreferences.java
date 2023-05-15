package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to create pet feeding freferences", value = "PetAddFeedingPreferences")
@JsonInclude(value = Include.NON_NULL)
public class PetAddFeedingPreferences {

	private Integer petId;
	private Integer userId;
	private List<Integer> petFeedingPreferences;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getPetFeedingPreferences() {
		return petFeedingPreferences;
	}

	public void setPetFeedingPreferences(List<Integer> petFeedingPreferences) {
		this.petFeedingPreferences = petFeedingPreferences;
	}

}
