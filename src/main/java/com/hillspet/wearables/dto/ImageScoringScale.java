package com.hillspet.wearables.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageScoringScale {

	private Integer imageScoringScaleId;
	private String imageScaleName;
	private Integer classificationId;
	private String classification;
	private Integer scoringTypeId;
	private String scoringType;
	private Integer speciesId;
	private String speciesName;

	private List<ImageScoringScaleDetails> scoringScaleDetails;

	public Integer getImageScoringScaleId() {
		return imageScoringScaleId;
	}

	public void setImageScoringScaleId(Integer imageScoringScaleId) {
		this.imageScoringScaleId = imageScoringScaleId;
	}

	public String getImageScaleName() {
		return imageScaleName;
	}

	public void setImageScaleName(String imageScaleName) {
		this.imageScaleName = imageScaleName;
	}

	public Integer getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public Integer getScoringTypeId() {
		return scoringTypeId;
	}

	public void setScoringTypeId(Integer scoringTypeId) {
		this.scoringTypeId = scoringTypeId;
	}

	public String getScoringType() {
		return scoringType;
	}

	public void setScoringType(String scoringType) {
		this.scoringType = scoringType;
	}

	public Integer getSpeciesId() {
		return speciesId;
	}

	public void setSpeciesId(Integer speciesId) {
		this.speciesId = speciesId;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public List<ImageScoringScaleDetails> getScoringScaleDetails() {
		return scoringScaleDetails;
	}

	public void setScoringScaleDetails(List<ImageScoringScaleDetails> scoringScaleDetails) {
		this.scoringScaleDetails = scoringScaleDetails;
	}

}
