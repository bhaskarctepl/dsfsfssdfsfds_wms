package com.hillspet.wearables.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hillspet.wearables.dto.QuestionAnswer;

import io.swagger.annotations.ApiModel;

/**
 * 
 * @author sgorle
 *
 */
@ApiModel(description = "Contains all information that needed to save questionnaire answers", value = "QuestionAnswerRequest")
@JsonInclude(value = Include.NON_NULL)
public class QuestionAnswerRequest {

	private Integer questionnaireId;
	private String studyIds;
	private Integer petId;
	private Integer petParentId;
	private List<QuestionAnswer> questionAnswers;

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getStudyIds() {
		return studyIds;
	}

	public void setStudyIds(String studyIds) {
		this.studyIds = studyIds;
	}

	public Integer getPetId() {
		return petId;
	}

	public void setPetId(Integer petId) {
		this.petId = petId;
	}

	public Integer getPetParentId() {
		return petParentId;
	}

	public void setPetParentId(Integer petParentId) {
		this.petParentId = petParentId;
	}

	public List<QuestionAnswer> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

}
