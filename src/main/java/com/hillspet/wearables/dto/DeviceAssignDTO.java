package com.hillspet.wearables.dto;

import java.util.Date;

public class DeviceAssignDTO {
	private int ID;
	private int deviceAssignId;
	private int PlanID;
	private int PetID;
	private int DeviceID;
	private String DeviceNumber;
	private String DeviceType;
	private boolean IsAssign;
	private String AssignDate;
	private String UnAssignDate;
	private String Note;
	private boolean Active;
	private String CreateDate;
	private String UpdateDate;
	private String PetName;	
	private int ClinicID; // studyId
	private String ClinicName; // studyName
	private String Battery;
	private String DataUploading;
	private Date LastSeen;
	private String Firmware;
	private String LastDataReceived;
	private boolean IsFirmwareVersionUpdateRequired;
	private boolean IsDeviceSetupDone;
	private String FirmwareNew;
	private int ClientID;
	private boolean IsSameClient;
	private int userId;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getDeviceAssignId() {
		return deviceAssignId;
	}
	public void setDeviceAssignId(int deviceAssignId) {
		this.deviceAssignId = deviceAssignId;
	}
	public int getPlanID() {
		return PlanID;
	}
	public void setPlanID(int planID) {
		PlanID = planID;
	}
	public int getPetID() {
		return PetID;
	}
	public void setPetID(int petID) {
		PetID = petID;
	}
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
	public boolean isIsAssign() {
		return IsAssign;
	}
	public void setIsAssign(boolean isAssign) {
		IsAssign = isAssign;
	}
	public String getAssignDate() {
		return AssignDate;
	}
	public void setAssignDate(String assignDate) {
		AssignDate = assignDate;
	}
	public String getUnAssignDate() {
		return UnAssignDate;
	}
	public void setUnAssignDate(String unAssignDate) {
		UnAssignDate = unAssignDate;
	}
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
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
	public String getPetName() {
		return PetName;
	}
	public void setPetName(String petName) {
		PetName = petName;
	}	
	public int getClinicID() {
		return ClinicID;
	}
	public void setClinicID(int clinicID) {
		ClinicID = clinicID;
	}
	public String getClinicName() {
		return ClinicName;
	}
	public void setClinicName(String clinicName) {
		ClinicName = clinicName;
	}
	public String getBattery() {
		return Battery;
	}
	public void setBattery(String battery) {
		Battery = battery;
	}
	public String getDataUploading() {
		return DataUploading;
	}
	public void setDataUploading(String dataUploading) {
		DataUploading = dataUploading;
	}
	public Date getLastSeen() {
		return LastSeen;
	}
	public void setLastSeen(Date lastSeen) {
		LastSeen = lastSeen;
	}
	public String getFirmware() {
		return Firmware;
	}
	public void setFirmware(String firmware) {
		Firmware = firmware;
	}
	public String getLastDataReceived() {
		return LastDataReceived;
	}
	public void setLastDataReceived(String lastDataReceived) {
		LastDataReceived = lastDataReceived;
	}
	public boolean isIsFirmwareVersionUpdateRequired() {
		return IsFirmwareVersionUpdateRequired;
	}
	public void setIsFirmwareVersionUpdateRequired(boolean isFirmwareVersionUpdateRequired) {
		IsFirmwareVersionUpdateRequired = isFirmwareVersionUpdateRequired;
	}
	public boolean isIsDeviceSetupDone() {
		return IsDeviceSetupDone;
	}
	public void setIsDeviceSetupDone(boolean isDeviceSetupDone) {
		IsDeviceSetupDone = isDeviceSetupDone;
	}
	public String getFirmwareNew() {
		return FirmwareNew;
	}
	public void setFirmwareNew(String firmwareNew) {
		FirmwareNew = firmwareNew;
	}
	public int getClientID() {
		return ClientID;
	}
	public void setClientID(int clientID) {
		ClientID = clientID;
	}
	public boolean isIsSameClient() {
		return IsSameClient;
	}
	public void setIsSameClient(boolean isSameClient) {
		IsSameClient = isSameClient;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
