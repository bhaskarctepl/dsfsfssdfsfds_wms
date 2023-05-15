package com.hillspet.wearables.dto;

import java.time.LocalDate;
import java.util.List;

public class Questionnaire {

	private Integer questionnaireId;
	private Integer questionnaireResponseId;
	private String questionnaireName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String studyIds;
	private String status;
	private List<QuestionnaireInstruction> instructions;
	private List<Question> questions;

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Integer getQuestionnaireResponseId() {
		return questionnaireResponseId;
	}

	public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
		this.questionnaireResponseId = questionnaireResponseId;
	}

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getStudyIds() {
		return studyIds;
	}

	public void setStudyIds(String studyIds) {
		this.studyIds = studyIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<QuestionnaireInstruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(List<QuestionnaireInstruction> instructions) {
		this.instructions = instructions;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

}
