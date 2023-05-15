package com.hillspet.wearables.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;

/**
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to create Pet Feeding Enthusiasm Scale Details", value = "PetFeedingEnthusiasmScale")
@JsonInclude(value = Include.NON_NULL)
public class PetFeedingEnthusiasmScale {

	private Integer feedingEnthusiasmScaleId;
	private Integer petId;
	private Integer enthusiasmScaleId;
	private Integer feedingTimeId;
	private LocalDate feedingDate;
	private Integer petParentId;

	public Integer getFeedingEnthusiasmScaleId() {
		return feedingEnthusiasmScaleId;
	}

	public void setFeedingEnthusiasmScaleId(Integer feedingEnthusiasmScaleId) {
		this.feedingEnthusiasmScaleId = feedingEnthusiasmScaleId;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getEnthusiasmScaleId() {
		return enthusiasmScaleId;
	}

	public void setEnthusiasmScaleId(Integer enthusiasmScaleId) {
		this.enthusiasmScaleId = enthusiasmScaleId;
	}

	public Integer getFeedingTimeId() {
		return feedingTimeId;
	}

	public void setFeedingTimeId(Integer feedingTimeId) {
		this.feedingTimeId = feedingTimeId;
	}

	public LocalDate getFeedingDate() {
		return feedingDate;
	}

	public void setFeedingDate(LocalDate feedingDate) {
		this.feedingDate = feedingDate;
	}

	public Integer getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(Integer petParentId) {
		this.petParentId = petParentId;
	}
}
