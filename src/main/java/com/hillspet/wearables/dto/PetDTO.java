package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetDTO {

	private Integer petID;
	private String petName;
	private String photoUrl;
	private String petAge;
	private String petBreed;
	private String birthday;
	private String gender;
	private Boolean isNeutered;
	private String weight;
	private String weightUnit;
	private String speciesId;
	private Integer petStatus;

	private List<DeviceDTO> devices;

	private Integer isPetEditable;

	public Integer getPetID() {
		return petID;
	}

	public void setPetID(Integer petID) {
		this.petID = petID;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPetAge() {
		return petAge;
	}

	public void setPetAge(String petAge) {
		this.petAge = petAge;
	}

	public String getPetBreed() {
		return petBreed;
	}

	public void setPetBreed(String petBreed) {
		this.petBreed = petBreed;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Boolean getIsNeutered() {
		return isNeutered;
	}

	public void setIsNeutered(Boolean isNeutered) {
		this.isNeutered = isNeutered;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public List<DeviceDTO> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceDTO> devices) {
		this.devices = devices;
	}

	public String getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(String speciesId) {
		this.speciesId = speciesId;
	}

	public Integer getIsPetEditable() {
		return isPetEditable;
	}

	public void setIsPetEditable(Integer isPetEditable) {
		this.isPetEditable = isPetEditable;
	}

	public Integer getPetStatus() {
		return petStatus;
	}

	public void setPetStatus(Integer petStatus) {
		this.petStatus = petStatus;
	}

}
