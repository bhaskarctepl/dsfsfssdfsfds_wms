package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetRedemptionHistoryDTO {
	private Integer petId;
	private Integer totalPoints;
	private Integer pointsRedeemed;
	private Integer balancePoints;
	private String redeemedByUser;
	private LocalDateTime createdDate;

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getPointsRedeemed() {
		return pointsRedeemed;
	}

	public void setPointsRedeemed(Integer pointsRedeemed) {
		this.pointsRedeemed = pointsRedeemed;
	}

	public Integer getBalancePoints() {
		return balancePoints;
	}

	public void setBalancePoints(Integer balancePoints) {
		this.balancePoints = balancePoints;
	}

	public String getRedeemedByUser() {
		return redeemedByUser;
	}

	public void setRedeemedByUser(String redeemedByUser) {
		this.redeemedByUser = redeemedByUser;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}
