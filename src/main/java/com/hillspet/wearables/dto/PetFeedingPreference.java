package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetFeedingPreference {
	
	private Integer feedingPreferenceId;
	private String feedingPreference;
	 

	public Integer getFeedingPreferenceId() {
		return feedingPreferenceId;
	}

	public void setFeedingPreferenceId(Integer feedingPreferenceId) {
		this.feedingPreferenceId = feedingPreferenceId;
	}

	public String getFeedingPreference() {
		return feedingPreference;
	}

	public void setFeedingPreference(String feedingPreference) {
		this.feedingPreference = feedingPreference;
	}
}
