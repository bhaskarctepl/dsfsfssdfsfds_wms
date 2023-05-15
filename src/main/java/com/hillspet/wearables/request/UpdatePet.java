package com.hillspet.wearables.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Contains all the information that needed to modify Pet ", value = "UpdatePetRequest")
@JsonInclude(value = Include.NON_NULL)
public class UpdatePet {
	private Integer petId;
	private String petName;
	private String gender;
	private Integer isNeutered;
	private Integer breedId;
	private LocalDate birthDay;
	private Integer isUnknown;
	private Double weight;
	private String weightUnit;
	private Integer userId;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getIsNeutered() {
		return isNeutered;
	}

	public void setIsNeutered(Integer isNeutered) {
		this.isNeutered = isNeutered;
	}

	public Integer getBreedId() {
		return breedId;
	}

	public void setBreedId(Integer breedId) {
		this.breedId = breedId;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	public Integer getIsUnknown() {
		return isUnknown;
	}

	public void setIsUnknown(Integer isUnknown) {
		this.isUnknown = isUnknown;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
