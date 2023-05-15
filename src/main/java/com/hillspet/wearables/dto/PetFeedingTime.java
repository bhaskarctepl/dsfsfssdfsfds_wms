package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetFeedingTime {
	
	private Integer feedingTimeId;
	private String feedingTime;

	public Integer getFeedingTimeId() {
		return feedingTimeId;
	}

	public void setFeedingTimeId(Integer feedingTimeId) {
		this.feedingTimeId = feedingTimeId;
	}

	public String getFeedingTime() {
		return feedingTime;
	}

	public void setFeedingTime(String feedingTime) {
		this.feedingTime = feedingTime;
	}
}
