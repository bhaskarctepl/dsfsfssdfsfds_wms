package com.hillspet.wearables.dto;

public class TimerLog {
	private int petTimerLogId;
	private int petParentId;
	private int petId;
	private String deviceNumber;
	private String recName;
	private String category;
	private String duration;
	private int isActive;
	private String timerDate;
	private String createdDate;
	private String petParentName;
	private String petName;
	private String userId;
	
	public int getPetTimerLogId() {
		return petTimerLogId;
	}
	public void setPetTimerLogId(int petTimerLogId) {
		this.petTimerLogId = petTimerLogId;
	}
	public int getPetParentId() {
		return petParentId;
	}
	public void setPetParentId(int petParentId) {
		this.petParentId = petParentId;
	}
	public int getPetId() {
		return petId;
	}
	public void setPetId(int petId) {
		this.petId = petId;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public String getRecName() {
		return recName;
	}
	public void setRecName(String recName) {
		this.recName = recName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getTimerDate() {
		return timerDate;
	}
	public void setTimerDate(String timerDate) {
		this.timerDate = timerDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getPetParentName() {
		return petParentName;
	}
	public void setPetParentName(String petParentName) {
		this.petParentName = petParentName;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
