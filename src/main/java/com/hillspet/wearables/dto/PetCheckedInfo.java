package com.hillspet.wearables.dto;

public class PetCheckedInfo {
	
	private int petCheckInfoID;
	
	private int petID;

	private String checkedTime;

	private String nextCheckedTime;

	private int checkedBy;

	private String checkedByName;

	private boolean active;

	private String createDate;

	private String updateDate;

	private String nextCheckInterval;

	public int getPetID() {
		return petID;
	}

	public void setPetID(int petID) {
		this.petID = petID;
	}

	public String getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(String checkedTime) {
		this.checkedTime = checkedTime;
	}

	public String getNextCheckedTime() {
		return nextCheckedTime;
	}

	public void setNextCheckedTime(String nextCheckedTime) {
		this.nextCheckedTime = nextCheckedTime;
	}

	public int getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(int checkedBy) {
		this.checkedBy = checkedBy;
	}

	public String getCheckedByName() {
		return checkedByName;
	}

	public void setCheckedByName(String checkedByName) {
		this.checkedByName = checkedByName;
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

	public String getNextCheckInterval() {
		return nextCheckInterval;
	}

	public void setNextCheckInterval(String nextCheckInterval) {
		this.nextCheckInterval = nextCheckInterval;
	}

	public int getPetCheckInfoID() {
		return petCheckInfoID;
	}

	public void setPetCheckInfoID(int petCheckInfoID) {
		this.petCheckInfoID = petCheckInfoID;
	}
	
	
}
