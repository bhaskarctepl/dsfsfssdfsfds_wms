package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create Pet weight", value = "PetWeightDTO")
@JsonInclude(value = Include.NON_NULL)
public class PetWeightDTO {

	private Integer petWeightId;
	private Integer petId;
	private Integer userId;
	private Double weight;
	private String weightUnit;
	private String addDate;
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

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
