package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to add image score to a pet", value = "PetAddImageScorings")
@JsonInclude(value = Include.NON_NULL)
public class PetAddImageScoring {

	private Integer imageScoreType;
	private Integer petImageScoringId;
	private Integer imageScoringId;
	private Integer petId;
	private List<PetImageScoreDetails> petImgScoreDetails;
	private Integer petParentId;

	public Integer getImageScoreType() {
		return imageScoreType;
	}

	public void setImageScoreType(Integer imageScoreType) {
		this.imageScoreType = imageScoreType;
	}

	public Integer getPetImageScoringId() {
		return petImageScoringId;
	}

	public void setPetImageScoringId(Integer petImageScoringId) {
		this.petImageScoringId = petImageScoringId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public List<PetImageScoreDetails> getPetImgScoreDetails() {
		return petImgScoreDetails;
	}

	public void setPetImgScoreDetails(List<PetImageScoreDetails> petImgScoreDetails) {
		this.petImgScoreDetails = petImgScoreDetails;
	}

	public Integer getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(Integer petParentId) {
		this.petParentId = petParentId;
	}

	public Integer getImageScoringId() {
		return imageScoringId;
	}

	public void setImageScoringId(Integer imageScoringId) {
		this.imageScoringId = imageScoringId;
	}

}
