package com.hillspet.wearables.dto;

public class PetParentKeyInfoDTO {
	private int petParentKeyId;
	private int petParentId;
	private String key;
	private int isExpired;
	private String addDate;
	private int isActive;
	private String createdDate;
	private String modifiedDate;
	private String userId;
	
	public int getPetParentKeyId() {
		return petParentKeyId;
	}
	public void setPetParentKeyId(int petParentKeyId) {
		this.petParentKeyId = petParentKeyId;
	}
	public int getPetParentId() {
		return petParentId;
	}
	public void setPetParentId(int petParentId) {
		this.petParentId = petParentId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(int isExpired) {
		this.isExpired = isExpired;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
