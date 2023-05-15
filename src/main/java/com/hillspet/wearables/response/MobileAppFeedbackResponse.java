package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillspet.wearables.dto.MobileAppFeedback;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileAppFeedbackResponse {
	
	private List<MobileAppFeedback> mobileAppFeedbackList;

	@ApiModelProperty(value = "List of details for mobile app feeedback by pet parent id")
	public List<MobileAppFeedback> getMobileAppFeeback() {
		return mobileAppFeedbackList;
	}

	public void setMobileAppFeeback(List<MobileAppFeedback> mobileAppFeedbackList) {
		this.mobileAppFeedbackList = mobileAppFeedbackList;
	}

}
