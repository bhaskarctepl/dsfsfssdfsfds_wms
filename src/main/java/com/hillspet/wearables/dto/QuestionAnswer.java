package com.hillspet.wearables.dto;

public class QuestionAnswer {

	private Integer questionnaireResponseId;
	private Integer questionId;
	private Integer answerOptionId;
	private String answer;

	public Integer getQuestionnaireResponseId() {
		return questionnaireResponseId;
	}

	public void setQuestionnaireResponseId(Integer questionnaireResponseId) {
		this.questionnaireResponseId = questionnaireResponseId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getAnswerOptionId() {
		return answerOptionId;
	}

	public void setAnswerOptionId(Integer answerOptionId) {
		this.answerOptionId = answerOptionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
