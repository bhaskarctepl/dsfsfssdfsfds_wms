package com.hillspet.wearables.dto;

public class DeviceInfo {
	private int DeviceID;

	private String DeviceNumber;

	private String DeviceType;

	private String AddDate;

	private boolean Active;

	private String CreateDate;

	private String UpdateDate;

	private boolean IsFirmwareVersionUpdateRequired;
	
	private int userId;

	public int getDeviceID() {
		return DeviceID;
	}

	public void setDeviceID(int deviceID) {
		DeviceID = deviceID;
	}

	public String getDeviceNumber() {
		return DeviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		DeviceNumber = deviceNumber;
	}

	public String getDeviceType() {
		return DeviceType;
	}

	public void setDeviceType(String deviceType) {
		DeviceType = deviceType;
	}

	public String getAddDate() {
		return AddDate;
	}

	public void setAddDate(String addDate) {
		AddDate = addDate;
	}

	public boolean isActive() {
		return Active;
	}

	public void setActive(boolean active) {
		Active = active;
	}

	public String getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}

	public String getUpdateDate() {
		return UpdateDate;
	}

	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}

	public boolean isIsFirmwareVersionUpdateRequired() {
		return IsFirmwareVersionUpdateRequired;
	}

	public void setIsFirmwareVersionUpdateRequired(boolean isFirmwareVersionUpdateRequired) {
		IsFirmwareVersionUpdateRequired = isFirmwareVersionUpdateRequired;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
