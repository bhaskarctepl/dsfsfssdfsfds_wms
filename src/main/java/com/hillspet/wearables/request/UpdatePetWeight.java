package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to modify Pet Weight", value = "PetWeightRequest")
@JsonInclude(value = Include.NON_NULL)
public class UpdatePetWeight {
	private Integer petWeightId;
	private Integer petId;
	private Integer userId;
	private Double weight;
	private String weightUnit;
	private String modifiedDate;

	public Integer getPetWeightId() {
		return petWeightId;
	}

	public void setPetWeightId(Integer petWeightId) {
		this.petWeightId = petWeightId;
	}

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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
