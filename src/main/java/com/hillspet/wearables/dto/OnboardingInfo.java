package com.hillspet.wearables.dto;

public class OnboardingInfo {
	private String UID;
	private String Title;
	private String Data;
	private int UserID;
	private int ClinicID;
	private boolean IsArchived;
	private String Status;
	private boolean IsFree;
	private boolean Active;
	private String CreateDate;
	private String UpdateDate;
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public int getClinicID() {
		return ClinicID;
	}
	public void setClinicID(int clinicID) {
		ClinicID = clinicID;
	}
	public boolean isIsArchived() {
		return IsArchived;
	}
	public void setIsArchived(boolean isArchived) {
		IsArchived = isArchived;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public boolean isIsFree() {
		return IsFree;
	}
	public void setIsFree(boolean isFree) {
		IsFree = isFree;
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
	
	
}
