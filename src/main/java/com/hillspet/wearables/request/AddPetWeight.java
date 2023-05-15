package com.hillspet.wearables.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to create pet weight", value = "PetWeightRequest")
@JsonInclude(value = Include.NON_NULL)
public class AddPetWeight {
	private Integer petWeightId;
	private Integer petId;
	private Integer userId;
	private Double weight;
	private String weightUnit;
	private String addDate;

	public Integer getPetWeightId() {
		return petWeightId;
	}

	public void setPetWeightId(Integer petWeightId) {
		this.petWeightId = petWeightId;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
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
}
