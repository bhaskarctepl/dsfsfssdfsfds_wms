package com.hillspet.wearables.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)


public class SensorDetailsDTO {
	
	String petParentId;
	String petId;			
	String deviceNumber;		
	String setupStatus;
	String ssidList;
	String userId;
	String notificationType;
	Date notificationDate;
	String opt;
	
	public String getPetParentId() {
		return petParentId;
	}
	public void setPetParentId(String petParentId) {
		this.petParentId = petParentId;
	}
	public String getPetId() {
		return petId;
	}
	public void setPetId(String petId) {
		this.petId = petId;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public String getSetupStatus() {
		return setupStatus;
	}
	public void setSetupStatus(String setupStatus) {
		this.setupStatus = setupStatus;
	}
	public String getSsidList() {
		return ssidList;
	}
	public void setSsidList(String ssidList) {
		this.ssidList = ssidList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public Date getNotificationDate() {
		return notificationDate;
	}
	public void setNotificationDate(Date notificationDate) {
		this.notificationDate = notificationDate;
	}
	
	
}
