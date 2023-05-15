package com.hillspet.wearables.dto;

public class ClientSMSCode {
	private int petParentSMSCodeId;	
	private int clientID;
	private String verificationCode;
	private boolean expired;
	private String addDate;
	private boolean isActive;
	private String createDate;
	private String updateDate;
	private String userId;
	
	public int getPetParentSMSCodeId() {
		return petParentSMSCodeId;
	}
	public void setPetParentSMSCodeId(int petParentSMSCodeId) {
		this.petParentSMSCodeId = petParentSMSCodeId;
	}
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean getExpired() {
		return expired;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
