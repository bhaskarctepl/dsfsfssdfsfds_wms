package com.hillspet.wearables.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hillspet.wearables.dto.ImageScoringScale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageScoringScalesResponse {

	List<ImageScoringScale> imageScoringScales;

	public List<ImageScoringScale> getImageScoringScales() {
		return imageScoringScales;
	}

	public void setImageScoringScales(List<ImageScoringScale> imageScoringScales) {
		this.imageScoringScales = imageScoringScales;
	}

}
