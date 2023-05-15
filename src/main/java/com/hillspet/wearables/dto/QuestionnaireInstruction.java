package com.hillspet.wearables.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireInstruction {

	private Integer instructionId;
	private String instruction;
	private Integer instructionOrder;
	private Boolean saveForFuture;
	private Boolean isActive;
	private Boolean isUpdated;

	public Integer getInstructionId() {
		return instructionId;
	}

	public void setInstructionId(Integer instructionId) {
		this.instructionId = instructionId;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public Integer getInstructionOrder() {
		return instructionOrder;
	}

	public void setInstructionOrder(Integer instructionOrder) {
		this.instructionOrder = instructionOrder;
	}

	public Boolean getSaveForFuture() {
		return saveForFuture;
	}

	public void setSaveForFuture(Boolean saveForFuture) {
		this.saveForFuture = saveForFuture;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

}
