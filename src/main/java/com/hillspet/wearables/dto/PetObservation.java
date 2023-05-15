package com.hillspet.wearables.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetObservation {

	private int observationId;
	private int petId;
	private String obsText;
	private String tag;
	private int behaviorId;
	private String behaviorName;
	private LocalDateTime observationDateTime;
	private String emotionIconsText;
	private String seizuresDescription;
	private int loginUserId;
	private LocalDateTime modifiedDate;
	private List<ObservationVideo> videos;
	private List<ObservationPhoto> photos;

	public int getObservationId() {
		return observationId;
	}

	public void setObservationId(int observationId) {
		this.observationId = observationId;
	}

	public int getPetId() {
		return petId;
	}

	public void setPetId(int petId) {
		this.petId = petId;
	}

	public String getObsText() {
		return obsText;
	}

	public void setObsText(String obsText) {
		this.obsText = obsText;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(int behaviorId) {
		this.behaviorId = behaviorId;
	}

	public String getBehaviorName() {
		return behaviorName;
	}

	public void setBehaviorName(String behaviorName) {
		this.behaviorName = behaviorName;
	}

	public LocalDateTime getObservationDateTime() {
		return observationDateTime;
	}

	public void setObservationDateTime(LocalDateTime observationDateTime) {
		this.observationDateTime = observationDateTime;
	}

	public String getEmotionIconsText() {
		return emotionIconsText;
	}

	public void setEmotionIconsText(String emotionIconsText) {
		this.emotionIconsText = emotionIconsText;
	}

	public String getSeizuresDescription() {
		return seizuresDescription;
	}

	public void setSeizuresDescription(String seizuresDescription) {
		this.seizuresDescription = seizuresDescription;
	}

	public int getLoginUserId() {
		return loginUserId;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setLoginUserId(int loginUserId) {
		this.loginUserId = loginUserId;
	}

	public List<ObservationVideo> getVideos() {
		return videos;
	}

	public void setVideos(List<ObservationVideo> videos) {
		this.videos = videos;
	}

	public List<ObservationPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<ObservationPhoto> photos) {
		this.photos = photos;
	}

}
