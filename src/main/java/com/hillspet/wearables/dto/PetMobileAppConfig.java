package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetMobileAppConfig {

	private Integer mobileAppConfigId;
	private String mobileAppConfigName;
	private String weightUnit;
	private LocalDate enthsmScaleStartDate;
	private LocalDate enthsmScaleEndDate;

	public Integer getMobileAppConfigId() {
		return mobileAppConfigId;
	}

	public void setMobileAppConfigId(Integer mobileAppConfigId) {
		this.mobileAppConfigId = mobileAppConfigId;
	}

	public String getMobileAppConfigName() {
		return mobileAppConfigName;
	}

	public void setMobileAppConfigName(String mobileAppConfigName) {
		this.mobileAppConfigName = mobileAppConfigName;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public LocalDate getEnthsmScaleStartDate() {
		return enthsmScaleStartDate;
	}

	public void setEnthsmScaleStartDate(LocalDate enthsmScaleStartDate) {
		this.enthsmScaleStartDate = enthsmScaleStartDate;
	}

	public LocalDate getEnthsmScaleEndDate() {
		return enthsmScaleEndDate;
	}

	public void setEnthsmScaleEndDate(LocalDate enthsmScaleEndDate) {
		this.enthsmScaleEndDate = enthsmScaleEndDate;
	}

}
