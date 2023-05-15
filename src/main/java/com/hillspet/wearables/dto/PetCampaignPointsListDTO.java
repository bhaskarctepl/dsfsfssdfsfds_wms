package com.hillspet.wearables.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetCampaignPointsListDTO {
	private Integer petCampaignPointsId;
	private Integer petId;
	private String petName;
	private String campaignName;
	private String imageName;
	private String imageUrl;
	private String videoThumbnailName;
	private String videoName;
	private String videoUrl;
	private String videoThumbnailUrl;
	private Integer questionareId;
	private String questionnaireName;
	private String feedback;
	private Integer statusId;
	private String status;
	private LocalDateTime createdDate;
	private String observation;
	private Integer activityId;
	private String activityName;
	private Integer behaviourId;
	private String behaviourName;
	private Integer points;

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public Integer getPetCampaignPointsId() {
		return petCampaignPointsId;
	}

	public void setPetCampaignPointsId(Integer petCampaignPointsId) {
		this.petCampaignPointsId = petCampaignPointsId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoThumbnailName() {
		return videoThumbnailName;
	}

	public void setVideoThumbnailName(String videoThumbnailName) {
		this.videoThumbnailName = videoThumbnailName;
	}

	public String getVideoThumbnailUrl() {
		return videoThumbnailUrl;
	}

	public void setVideoThumbnailUrl(String videoThumbnailUrl) {
		this.videoThumbnailUrl = videoThumbnailUrl;
	}

	public Integer getQuestionareId() {
		return questionareId;
	}

	public void setQuestionareId(Integer questionareId) {
		this.questionareId = questionareId;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getBehaviourName() {
		return behaviourName;
	}

	public void setBehaviourName(String behaviourName) {
		this.behaviourName = behaviourName;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getBehaviourId() {
		return behaviourId;
	}

	public void setBehaviourId(Integer behaviourId) {
		this.behaviourId = behaviourId;
	}

}
