package com.hillspet.wearables.dto;

import com.hillspet.wearables.dto.EnumHelper.PlanStatus;

public class MonitoringPlan {
	private int planID;
	private int typeID;
	private String serviceAttributeName;
	private int petID;
	private int clientID;
	private int clinicID;
	private String subscriptionID;
	private String startDate;
	private String endDate;
	private int userID;
	private PlanStatus status;
	private boolean active;
	private String createDate;
	private String updateDate;
	private boolean isFree;
	private String logo;
	private String planDesc;
	public int getPlanID() {
		return planID;
	}
	public void setPlanID(int planID) {
		this.planID = planID;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getServiceAttributeName() {
		return serviceAttributeName;
	}
	public void setServiceAttributeName(String serviceAttributeName) {
		this.serviceAttributeName = serviceAttributeName;
	}
	public int getPetID() {
		return petID;
	}
	public void setPetID(int petID) {
		this.petID = petID;
	}
	public int getClientID() {
		return clientID;
	}
	public void setClientID(int clientID) {
		this.clientID = clientID;
	}
	public int getClinicID() {
		return clinicID;
	}
	public void setClinicID(int clinicID) {
		this.clinicID = clinicID;
	}
	public String getSubscriptionID() {
		return subscriptionID;
	}
	public void setSubscriptionID(String subscriptionID) {
		this.subscriptionID = subscriptionID;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public PlanStatus getStatus() {
		return status;
	}
	public void setStatus(PlanStatus status) {
		this.status = status;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	
	
}
