package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDTO {

	private Integer deviceId;
	private String deviceNumber;
	private String deviceType;
	private String deviceModel;
	private String battery;
	private String firmware;
	private String firmwareNew;
	private Boolean isDeviceSetupDone;
	private Boolean isFirmwareVersionUpdateRequired;
	private String lastSeen;

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getFirmware() {
		return firmware;
	}

	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}

	public String getFirmwareNew() {
		return firmwareNew;
	}

	public void setFirmwareNew(String firmwareNew) {
		this.firmwareNew = firmwareNew;
	}

	public Boolean getIsDeviceSetupDone() {
		return isDeviceSetupDone;
	}

	public void setIsDeviceSetupDone(Boolean isDeviceSetupDone) {
		this.isDeviceSetupDone = isDeviceSetupDone;
	}

	public Boolean getIsFirmwareVersionUpdateRequired() {
		return isFirmwareVersionUpdateRequired;
	}

	public void setIsFirmwareVersionUpdateRequired(Boolean isFirmwareVersionUpdateRequired) {
		this.isFirmwareVersionUpdateRequired = isFirmwareVersionUpdateRequired;
	}

	public String getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

}
