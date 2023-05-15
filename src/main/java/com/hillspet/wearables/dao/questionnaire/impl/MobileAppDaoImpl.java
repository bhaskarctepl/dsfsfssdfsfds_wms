package com.hillspet.wearables.dao.questionnaire.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.questionnaire.MobileAppDao;
import com.hillspet.wearables.dto.Campaign;
import com.hillspet.wearables.dto.DeviceDTO;
import com.hillspet.wearables.dto.LeaderBoard;
import com.hillspet.wearables.dto.MobileAppFeedback;
import com.hillspet.wearables.dto.PetCampaignPointsDTO;
import com.hillspet.wearables.dto.PetCampaignPointsListDTO;
import com.hillspet.wearables.dto.PetDTO;
import com.hillspet.wearables.dto.PetRedemptionHistoryDTO;
import com.hillspet.wearables.dto.Question;
import com.hillspet.wearables.dto.QuestionAnswer;
import com.hillspet.wearables.dto.QuestionAnswerOption;
import com.hillspet.wearables.dto.Questionnaire;
import com.hillspet.wearables.dto.QuestionnaireInstruction;
import com.hillspet.wearables.request.AssignSensorRequest;
import com.hillspet.wearables.request.QuestionAnswerRequest;

@Repository
public class MobileAppDaoImpl extends BaseDaoImpl implements MobileAppDao {

	private static final Logger LOGGER = LogManager.getLogger(MobileAppDaoImpl.class);

	@Value("${gcp.env}")
	private String environment;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	public static final String RESULT_SET_1 = "#result-set-1";
	public static final String RESULT_SET_2 = "#result-set-2";
	public static final String RESULT_SET_3 = "#result-set-3";
	public static final String RESULT_SET_4 = "#result-set-4";
	public static final String RESULT_SET_5 = "#result-set-5";

	public static final String MOBILE_APP_GET_FEEDBACK_QUESTIONNAIRE_BY_PET_ID = "MOBILE_APP_GET_FEEDBACK_QUESTIONNAIRE_BY_PET_ID";
	public static final String MOBILE_APP_GET_QUESTIONNAIRE_BY_PET_ID = "MOBILE_APP_GET_QUESTIONNAIRE_BY_PET_ID";
	public static final String MOBILE_APP_GET_QUESTIONNAIRE_ASNWER_BY_ID = "MOBILE_APP_GET_QUESTIONNAIRE_ASNWER_BY_ID";
	public static final String MOBILE_APP_INSERT_QUESTIONNAIRE_RESPONSE = "MOBILE_APP_INSERT_QUESTIONNAIRE_RESPONSE";
	public static final String MOBILE_APP_GET_CAMPAIGN_POINTS_LIST_BY_PET = "call MOBILE_APP_GET_CAMPAIGN_POINTS_LIST_BY_PET(?)";
	public static final String MOBILE_APP_GET_ALL_CAMPAIGN_POINTS_BY_PET = "call MOBILE_APP_GET_ALL_CAMPAIGN_POINTS_BY_PET(?)";
	public static final String MOBILE_APP_PET_PARENT_AUTH_BY_KEY = "MOBILE_APP_PET_PARENT_AUTH_BY_KEY";
	public static final String MOBILE_APP_GET_CAMPAIGNS_BY_PET = "call MOBILE_APP_GET_CAMPAIGNS_BY_PET(?)";
	public static final String MOBILE_APP_GET_LEADER_BOARD_BY_CAMPAIGN = "call MOBILE_APP_GET_LEADER_BOARD_BY_CAMPAIGN(?)";
	public static final String MOBILE_APP_GET_REDEEMTION_HISTORY_BY_PET = "call MOBILE_APP_GET_REDEEMTION_HISTORY_BY_PET(?)";
	public static final String MOBILE_APP_ASSIGN_PET_SENSOR = "MOBILE_APP_ASSIGN_PET_SENSOR";
	public static final String MOBILE_APP_GET_PET_DEVICES_BY_PET_PARENT_ID = "call MOBILE_APP_GET_PET_DEVICES_BY_PET_PARENT_ID(?)";
	public static final String MOBILE_APP_FEEDBACK_LIST = "call MOBILE_APP_GET_FEEDBACK_BY_PET_PARENT (?)";
	public static final String MOBILE_APP_GET_DEVICE_TYPES = "CALL MOBILE_APP_GET_GET_DEVICE_TYPES()";
	public static final String MOBILE_APP_GET_DEVICE_MODELS = "CALL MOBILE_APP_GET_DEVICE_MODELS(?)";

	@SuppressWarnings("unchecked")
	@Override
	public List<Questionnaire> getFeedbackQuestionnaireByPetId(int petId) throws ServiceExecutionException {
		LOGGER.debug("getFeedbackQuestionnaireByPetId called");
		List<Questionnaire> questionnaireList = new ArrayList<>();

		Map<Integer, List<QuestionnaireInstruction>> instructionsMap = new HashMap<>();
		Map<Integer, List<QuestionAnswerOption>> questionAnsOptsMap = new HashMap<>();
		Map<Integer, List<Question>> questionsMap = new HashMap<>();
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_GET_FEEDBACK_QUESTIONNAIRE_BY_PET_ID,
					inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(instruction -> {
						QuestionnaireInstruction questionnaireInstruction = new QuestionnaireInstruction();
						questionnaireInstruction
								.setInstructionId((Integer) instruction.get("QUESTIONNAIRE_INSTRUCTION_ID"));
						questionnaireInstruction.setInstruction((String) instruction.get("INSTRUCTION"));
						questionnaireInstruction.setInstructionOrder((Integer) instruction.get("INSTRUCTION_ORDER"));

						Integer questionnaireId = (Integer) instruction.get("QUESTIONNAIRE_ID");
						instructionsMap.computeIfAbsent(questionnaireId, k -> new ArrayList<QuestionnaireInstruction>())
								.add(questionnaireInstruction);
					});
				}

				if (key.equals(RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(answerOpts -> {
						QuestionAnswerOption ansOptions = new QuestionAnswerOption();
						ansOptions.setQuestionAnswerId((Integer) answerOpts.get("QUESTION_ANSWER_OPTION_ID"));
						ansOptions.setQuestionAnswer((String) answerOpts.get("ANSWER"));

						Integer questionId = (Integer) answerOpts.get("QUESTION_ID");

						questionAnsOptsMap.computeIfAbsent(questionId, k -> new ArrayList<QuestionAnswerOption>())
								.add(ansOptions);
					});
				}

				if (key.equals(RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quest -> {
						Question question = new Question();
						Integer questionId = (Integer) quest.get("QUESTION_ID");
						question.setQuestionId(questionId);
						question.setQuestion((String) quest.get("QUESTION"));
						question.setQuestionTypeId((Integer) quest.get("QUESTION_TYPE_ID"));
						question.setQuestionType((String) quest.get("QUESTION_TYPE"));
						question.setQuestionOrder((Integer) quest.get("QUESTION_ORDER"));
						question.setIsMandatory(quest.get("IS_MANDATORY") != null
								&& (Integer) quest.get("IS_MANDATORY") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);

						question.setCeil((Integer) quest.get("SCALE_MAX"));
						question.setFloor((Integer) quest.get("SCALE_MIN"));
						question.setTickStep((Integer) quest.get("STEP_VALUE"));

						question.setQuestionAnswerOptions(
								questionAnsOptsMap.get(questionId) != null ? questionAnsOptsMap.get(questionId)
										: new ArrayList<>());

						Integer questionnaireId = (Integer) quest.get("QUESTIONNAIRE_ID");
						questionsMap.computeIfAbsent(questionnaireId, k -> new ArrayList<Question>()).add(question);
					});
				}

				if (key.equals(RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quesObj -> {
						Questionnaire questionnaire = new Questionnaire();
						Integer questionnaireId = (Integer) quesObj.get("QUESTIONNAIRE_ID");

						questionnaire.setQuestionnaireId(questionnaireId);
						questionnaire.setQuestionnaireName((String) quesObj.get("QUESTIONNAIRE_NAME"));

						Date startDate = (Date) quesObj.get("START_DATE");
						questionnaire.setStartDate((startDate.toLocalDate()));

						Date endDate = (Date) quesObj.get("END_DATE");
						questionnaire.setEndDate((endDate.toLocalDate()));

						/*
						 * Long ansCount = (Long) quesObj.get("ANS_CNT"); if (ansCount > 0) {
						 * questionnaire.setStatus("Submitted"); } else { if
						 * (endDate.toLocalDate().isBefore(LocalDate.now())) {
						 * questionnaire.setStatus("Elapsed"); } else { questionnaire.setStatus("Open");
						 * } }
						 */

						questionnaire.setQuestions(
								questionsMap.get(questionnaireId) != null ? questionsMap.get(questionnaireId)
										: new ArrayList<>());

						questionnaire.setInstructions(
								instructionsMap.get(questionnaireId) != null ? instructionsMap.get(questionnaireId)
										: new ArrayList<>());
						questionnaireList.add(questionnaire);
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getFeedbackQuestionnaireByPetId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questionnaire> getQuestionnaireByPetId(int petId) throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireByPetId called");
		List<Questionnaire> questionnaireList = new ArrayList<>();
		List<QuestionAnswer> questionAnswerList = new ArrayList<>();

		Map<Integer, List<QuestionnaireInstruction>> instructionsMap = new HashMap<>();
		Map<Integer, List<QuestionAnswerOption>> questionAnsOptsMap = new HashMap<>();
		Map<Integer, List<Question>> questionsMap = new HashMap<>();
		long startTime = System.currentTimeMillis();
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_GET_QUESTIONNAIRE_BY_PET_ID, inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(instruction -> {
						QuestionnaireInstruction questionnaireInstruction = new QuestionnaireInstruction();
						questionnaireInstruction
								.setInstructionId((Integer) instruction.get("QUESTIONNAIRE_INSTRUCTION_ID"));
						questionnaireInstruction.setInstruction((String) instruction.get("INSTRUCTION"));
						questionnaireInstruction.setInstructionOrder((Integer) instruction.get("INSTRUCTION_ORDER"));

						Integer questionnaireId = (Integer) instruction.get("QUESTIONNAIRE_ID");
						instructionsMap.computeIfAbsent(questionnaireId, k -> new ArrayList<QuestionnaireInstruction>())
								.add(questionnaireInstruction);
					});
				}

				if (key.equals(RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(answerOpts -> {
						QuestionAnswerOption ansOptions = new QuestionAnswerOption();
						ansOptions.setQuestionAnswerId((Integer) answerOpts.get("QUESTION_ANSWER_OPTION_ID"));
						ansOptions.setQuestionAnswer((String) answerOpts.get("ANSWER"));

						Integer questionId = (Integer) answerOpts.get("QUESTION_ID");

						questionAnsOptsMap.computeIfAbsent(questionId, k -> new ArrayList<QuestionAnswerOption>())
								.add(ansOptions);
					});
				}

				if (key.equals(RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quest -> {
						Question question = new Question();
						Integer questionId = (Integer) quest.get("QUESTION_ID");
						question.setQuestionId(questionId);
						question.setQuestion((String) quest.get("QUESTION"));
						question.setQuestionTypeId((Integer) quest.get("QUESTION_TYPE_ID"));
						question.setQuestionType((String) quest.get("QUESTION_TYPE"));
						question.setQuestionOrder((Integer) quest.get("QUESTION_ORDER"));
						question.setIsMandatory(quest.get("IS_MANDATORY") != null
								&& (Integer) quest.get("IS_MANDATORY") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);

						question.setCeil((Integer) quest.get("SCALE_MAX"));
						question.setFloor((Integer) quest.get("SCALE_MIN"));
						question.setTickStep((Integer) quest.get("STEP_VALUE"));

						question.setQuestionAnswerOptions(
								questionAnsOptsMap.get(questionId) != null ? questionAnsOptsMap.get(questionId)
										: new ArrayList<>());

						Integer questionnaireId = (Integer) quest.get("QUESTIONNAIRE_ID");

						questionsMap.computeIfAbsent(questionnaireId, k -> new ArrayList<Question>()).add(question);
					});
				}

				if (key.equals(RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quesObj -> {
						Questionnaire questionnaire = new Questionnaire();
						Integer questionnaireId = (Integer) quesObj.get("QUESTIONNAIRE_ID");
						questionnaire.setStudyIds((String) quesObj.get("STUDY_ID"));

						questionnaire.setQuestionnaireId(questionnaireId);
						questionnaire.setQuestionnaireName((String) quesObj.get("QUESTIONNAIRE_NAME"));

						Date startDate = (Date) quesObj.get("START_DATE");
						questionnaire.setStartDate((startDate.toLocalDate()));

						Date endDate = (Date) quesObj.get("END_DATE");
						questionnaire.setEndDate((endDate.toLocalDate()));

						Long ansCount = (Long) quesObj.get("ANS_CNT");
						if (ansCount > 0) {
							questionnaire.setStatus("Submitted");
						} else {
							if (endDate.toLocalDate().isBefore(LocalDate.now())) {
								questionnaire.setStatus("Elapsed");
							} else {
								questionnaire.setStatus("Open");
							}
						}

						Integer questionnaireResponseId = quesObj.get("QUESTIONNAIRE_RESPONSE_ID") != null
								? (Integer) quesObj.get("QUESTIONNAIRE_RESPONSE_ID")
								: NumberUtils.INTEGER_ZERO;
						questionnaire.setQuestionnaireResponseId(questionnaireResponseId);

						questionnaire.setQuestions(
								questionsMap.get(questionnaireId) != null ? questionsMap.get(questionnaireId)
										: new ArrayList<>());

						questionnaire.setInstructions(
								instructionsMap.get(questionnaireId) != null ? instructionsMap.get(questionnaireId)
										: new ArrayList<>());
						questionnaireList.add(questionnaire);
					});
				}

				if (key.equals(RESULT_SET_5)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quesObj -> {
						QuestionAnswer questionAnswer = new QuestionAnswer();

						questionAnswer.setQuestionnaireResponseId((Integer) quesObj.get("QUESTIONNAIRE_RESPONSE_ID"));
						questionAnswer.setQuestionId((Integer) quesObj.get("QUESTION_ID"));
						questionAnswer.setAnswer((String) quesObj.get("ANSWER"));
						questionAnswer.setAnswerOptionId((Integer) quesObj.get("ANSWER_OPTION_ID"));

						questionAnswerList.add(questionAnswer);
					});
				}

				questionnaireList.parallelStream().forEach(questionnaire -> {
					questionnaire.getQuestions().parallelStream().forEach(question -> {
						Optional<QuestionAnswer> optionalAnswer = questionAnswerList.stream()
								.filter(answer -> answer.getQuestionnaireResponseId()
										.equals(questionnaire.getQuestionnaireResponseId())
										&& answer.getQuestionId().equals(question.getQuestionId()))
								.findFirst();
						if (optionalAnswer.isPresent()) {
							question.setAnswer(optionalAnswer.get().getAnswer());
						}
					});
				});
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Time taken to execute getQuestionnaireByPetId service in millis is {}", (endTime - startTime));
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireByPetId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Questionnaire> getQuestionnaireAnswers(int petId, int inQuestionnaireId)
			throws ServiceExecutionException {
		LOGGER.debug("getQuestionnaireAnswers called");
		List<Questionnaire> questionnaireList = new ArrayList<>();

		Map<Integer, List<QuestionnaireInstruction>> instructionsMap = new HashMap<>();
		Map<Integer, List<QuestionAnswerOption>> questionAnsOptsMap = new HashMap<>();
		Map<Integer, List<Question>> questionsMap = new HashMap<>();
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);
			inputParams.put("p_questionnaire_id", inQuestionnaireId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_GET_QUESTIONNAIRE_ASNWER_BY_ID, inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(instruction -> {
						QuestionnaireInstruction questionnaireInstruction = new QuestionnaireInstruction();
						questionnaireInstruction
								.setInstructionId((Integer) instruction.get("QUESTIONNAIRE_INSTRUCTION_ID"));
						questionnaireInstruction.setInstruction((String) instruction.get("INSTRUCTION"));
						questionnaireInstruction.setInstructionOrder((Integer) instruction.get("INSTRUCTION_ORDER"));

						Integer questionnaireId = (Integer) instruction.get("QUESTIONNAIRE_ID");
						instructionsMap.computeIfAbsent(questionnaireId, k -> new ArrayList<QuestionnaireInstruction>())
								.add(questionnaireInstruction);
					});
				}

				if (key.equals(RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(answerOpts -> {
						QuestionAnswerOption ansOptions = new QuestionAnswerOption();
						ansOptions.setQuestionAnswerId((Integer) answerOpts.get("QUESTION_ANSWER_OPTION_ID"));
						ansOptions.setQuestionAnswer((String) answerOpts.get("ANSWER"));

						Integer questionId = (Integer) answerOpts.get("QUESTION_ID");

						questionAnsOptsMap.computeIfAbsent(questionId, k -> new ArrayList<QuestionAnswerOption>())
								.add(ansOptions);
					});
				}

				if (key.equals(RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quest -> {
						Question question = new Question();
						Integer questionId = (Integer) quest.get("QUESTION_ID");
						question.setQuestionId(questionId);
						question.setQuestion((String) quest.get("QUESTION"));
						question.setAnswer((String) quest.get("ANSWER"));
						question.setQuestionTypeId((Integer) quest.get("QUESTION_TYPE_ID"));
						question.setQuestionType((String) quest.get("QUESTION_TYPE"));
						question.setQuestionOrder((Integer) quest.get("QUESTION_ORDER"));
						question.setIsMandatory(quest.get("IS_MANDATORY") != null
								&& (Integer) quest.get("IS_MANDATORY") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);

						question.setCeil((Integer) quest.get("SCALE_MAX"));
						question.setFloor((Integer) quest.get("SCALE_MIN"));
						question.setTickStep((Integer) quest.get("STEP_VALUE"));

						question.setQuestionAnswerOptions(
								questionAnsOptsMap.get(questionId) != null ? questionAnsOptsMap.get(questionId)
										: new ArrayList<>());

						Integer questionnaireId = (Integer) quest.get("QUESTIONNAIRE_ID");
						questionsMap.computeIfAbsent(questionnaireId, k -> new ArrayList<Question>()).add(question);
					});
				}

				if (key.equals(RESULT_SET_4)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(quesObj -> {
						Questionnaire questionnaire = new Questionnaire();
						Integer questionnaireId = (Integer) quesObj.get("QUESTIONNAIRE_ID");
						questionnaire.setStudyIds((String) quesObj.get("STUDY_ID"));

						questionnaire.setQuestionnaireId(questionnaireId);
						questionnaire.setQuestionnaireName((String) quesObj.get("QUESTIONNAIRE_NAME"));

						Date startDate = (Date) quesObj.get("START_DATE");
						questionnaire.setStartDate((startDate.toLocalDate()));

						Date endDate = (Date) quesObj.get("END_DATE");
						questionnaire.setEndDate((endDate.toLocalDate()));

						Long ansCount = (Long) quesObj.get("ANS_CNT");
						if (ansCount > 0) {
							questionnaire.setStatus("Submitted");
						} else {
							if (endDate.toLocalDate().isBefore(LocalDate.now())) {
								questionnaire.setStatus("Elapsed");
							} else {
								questionnaire.setStatus("Open");
							}
						}

						questionnaire.setQuestions(
								questionsMap.get(questionnaireId) != null ? questionsMap.get(questionnaireId)
										: new ArrayList<>());

						questionnaire.setInstructions(
								instructionsMap.get(questionnaireId) != null ? instructionsMap.get(questionnaireId)
										: new ArrayList<>());
						questionnaireList.add(questionnaire);
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getQuestionnaireAnswers", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return questionnaireList;
	}

	@Override
	public void saveQuestionAnswers(QuestionAnswerRequest questionAnswerRequest) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", questionAnswerRequest.getPetId());
			inputParams.put("p_pet_parent_id", questionAnswerRequest.getPetParentId());
			inputParams.put("p_questionnaire_id", questionAnswerRequest.getQuestionnaireId());
			inputParams.put("p_study_ids", questionAnswerRequest.getStudyIds());
			inputParams.put("p_question_answer_json",
					new ObjectMapper().writeValueAsString(questionAnswerRequest.getQuestionAnswers()));

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_QUESTIONNAIRE_RESPONSE, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer questionnaireId = (int) outParams.get("last_insert_id");
				LOGGER.info("Questionnaire answers has been created successfully, Questionnaire id is ",
						questionnaireId);
			} else {
				if (statusFlag == -2) {
					/*
					 * throw new ServiceExecutionException(
					 * "saveQuestionAnswers service validation failed cannot proceed further",
					 * Status.BAD_REQUEST.getStatusCode(), Arrays.asList(new
					 * WearablesError(WearablesErrorCode.QUESTIONNAIRE_NAME_ALREADY_EXISTS,
					 * questionnaireRequest.getQuestionnaire().getQuestionnaireName())));
					 */
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException | JsonProcessingException e) {
			LOGGER.error("error while executing saveQuestionAnswers ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public PetCampaignPointsDTO getPetCampaignPoints(int petId) throws ServiceExecutionException {
		final PetCampaignPointsDTO petCampaignPointsDTO = new PetCampaignPointsDTO();
		LOGGER.debug("getPetCampaignPoints called");
		try {
			jdbcTemplate.query(MOBILE_APP_GET_ALL_CAMPAIGN_POINTS_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					petCampaignPointsDTO.setTotalEarnedPoints(rs.getInt("TOTAL_EARNED_POINTS"));
					petCampaignPointsDTO.setImages(rs.getInt("TOTAL_IMAGES"));
					petCampaignPointsDTO.setObservations(rs.getInt("TOTAL_OBSERVATIONS"));
					petCampaignPointsDTO.setPointsUtilized(rs.getInt("POINTS_UTILIZED"));
					petCampaignPointsDTO.setQuestionnaire(rs.getInt("TOTAL_QUESIONARES"));
					petCampaignPointsDTO.setFeeback(rs.getInt("TOTAL_FEEDBACK"));
					petCampaignPointsDTO.setVideos(rs.getInt("TOTAL_VIDEOS"));
					petCampaignPointsDTO.setRedeemablePoints(rs.getInt("REMAINING_POINTS"));
				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetCampaignPoints", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petCampaignPointsDTO;
	}

	@Override
	public List<PetCampaignPointsListDTO> getPetCampaignPointsList(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetCampaignList called");
		List<PetCampaignPointsListDTO> campaignList = new ArrayList<>();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_CAMPAIGN_POINTS_LIST_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetCampaignPointsListDTO campagin = new PetCampaignPointsListDTO();
					campagin.setPetCampaignPointsId(rs.getInt("TRACKER_PET_POINTS_ID"));
					campagin.setPetId(rs.getInt("PET_ID"));
					campagin.setPetName(rs.getString("PET_NAME"));
					campagin.setCampaignName(rs.getString("CAMPAIGN_NAME"));

					campagin.setQuestionareId(rs.getInt("QUESTIONNAIRE_ID"));
					campagin.setQuestionnaireName(rs.getString("QUESTIONNAIRE_NAME"));
					campagin.setFeedback(rs.getString("FEED_BACK_TEXT"));
					campagin.setStatusId(rs.getInt("STATUS_ID"));
					campagin.setStatus(rs.getString("STATUS"));

					String fileName = rs.getString("FILE_NAME");
					String imageURL = rs.getString("IMAGE_PATH");
					if (StringUtils.isNotBlank(imageURL)) {
						if (!StringUtils.contains(imageURL, "firebasestorage")) {
							String imageSignedUrl = gcpClientUtil.getDownloaFiledUrl(
									imageURL.concat("/").concat(fileName), Constants.GCP_OBSERVATION_PHOTO_PATH);
							campagin.setImageUrl(imageSignedUrl);
						} else {
							campagin.setImageUrl(imageURL);
						}
					}

					String videoURL = rs.getString("VIDEO_URL");
					if (StringUtils.isNotBlank(videoURL)) {
						if (!StringUtils.contains(videoURL, "firebasestorage")) {
							String mediaSignedUrl = gcpClientUtil.getDownloaFiledUrl(
									videoURL.replaceAll("https://storage.googleapis.com/wearables-portal-media/"
											+ environment + "/GCloud/WPortal/ObservationVideo/", ""),
									Constants.GCP_OBSERVATION_VIDEO_PATH);
							campagin.setVideoUrl(mediaSignedUrl);
						} else {
							campagin.setVideoUrl(videoURL);
						}
					}

					String videoThumbnailURL = rs.getString("VIDEO_THUMBNAIL");
					if (StringUtils.isNotBlank(videoThumbnailURL)) {
						if (!StringUtils.contains(videoThumbnailURL, "firebasestorage")) {
							String mediaSignedThumbUrl = gcpClientUtil.getDownloaFiledUrl(
									videoThumbnailURL
											.replaceAll("https://storage.googleapis.com/wearables-portal-media/"
													+ environment + "/GCloud/WPortal/ObservationVideoThumbnail/", ""),
									Constants.GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH);
							campagin.setVideoThumbnailUrl(mediaSignedThumbUrl);
						} else {
							campagin.setVideoThumbnailUrl(videoThumbnailURL);
						}
					}

					Timestamp createdDate = (Timestamp) rs.getTimestamp("CREATED_DATE");
					campagin.setCreatedDate(createdDate != null ? createdDate.toLocalDateTime() : null);

					campagin.setObservation(rs.getString("OBSERVATION"));
					campagin.setActivityId(rs.getInt("ACTIVITY_ID"));
					campagin.setActivityName(rs.getString("ACTIVITY"));
					campagin.setBehaviourId(rs.getInt("BEHAVIOR_ID"));
					campagin.setBehaviourName(rs.getString("BEHAVIOR"));
					campagin.setPoints(rs.getInt("POINTS"));
					campaignList.add(campagin);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetCampaignList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return campaignList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getPetParentByPetParentKey(String petParentKey) throws ServiceExecutionException {
		LOGGER.debug("getPetParentByAuthKey called");
		int petParentKeyId = 0;
		try {
			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_parent_key", petParentKey);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_PET_PARENT_AUTH_BY_KEY, inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					if (CollectionUtils.isNotEmpty(list)) {
						petParentKeyId = (Integer) list.get(NumberUtils.INTEGER_ZERO).get("PET_PARENT_KEY_ID");
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetParentByAuthKey", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petParentKeyId;
	}

	@Override
	public List<Campaign> getCampaignListByPet(int petId) throws ServiceExecutionException {
		List<Campaign> campaignList = new ArrayList<>();
		LOGGER.debug("getCampaignListByPet called");
		try {
			jdbcTemplate.query(MOBILE_APP_GET_CAMPAIGNS_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Campaign campaign = new Campaign();
					campaign.setCampaignId(rs.getInt("CAMPAIGN_ID"));
					campaign.setCampaignName(rs.getString("CAMPAIGN_NAME"));
					Date startDate = rs.getDate("START_DATE");
					campaign.setStartDate((startDate.toLocalDate()));

					Date endDate = rs.getDate("END_DATE");
					campaign.setEndDate((endDate.toLocalDate()));
					campaignList.add(campaign);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getCampaignListByPet", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return campaignList;
	}

	@Override
	public List<LeaderBoard> getLeaderBoardByCampaignId(int campaignId) throws ServiceExecutionException {
		List<LeaderBoard> leaderBoards = new ArrayList<>();
		LOGGER.debug("getLeaderBoardByCampaignId called");
		try {
			jdbcTemplate.query(MOBILE_APP_GET_LEADER_BOARD_BY_CAMPAIGN, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					LeaderBoard leaderBoard = new LeaderBoard();
					leaderBoard.setCampaignId(rs.getInt("CAMPAIGN_ID"));
					leaderBoard.setCampaignName(rs.getString("CAMPAIGN_NAME"));
					leaderBoard.setPetId(rs.getInt("PET_ID"));
					leaderBoard.setPetName(rs.getString("PET_NAME"));
					if (rs.getString("PHOTO_NAME") != null && !rs.getString("PHOTO_NAME").isEmpty()) {
						String imageName = rs.getString("PHOTO_NAME");
						// String petPhotoUrl = photoPath + imageName;
						// leaderBoard.setPetPhotoUrl(petPhotoUrl);
						leaderBoard.setPetPhotoUrl(
								gcpClientUtil.getDownloaFiledUrl(imageName, Constants.GCP_PET_PHOTO_PATH));
					}

					leaderBoard.setPoints(rs.getInt("POINTS"));
					leaderBoard.setRank(rs.getInt("RANKING"));

					leaderBoards.add(leaderBoard);
				}
			}, campaignId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getLeaderBoardByCampaignId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return leaderBoards;
	}

	@Override
	public List<PetRedemptionHistoryDTO> getPetRedemptionHistory(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetRedemptionHistory called");
		List<PetRedemptionHistoryDTO> redemptionHistoryDTOs = new ArrayList<>();

		try {
			jdbcTemplate.query(MOBILE_APP_GET_REDEEMTION_HISTORY_BY_PET, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetRedemptionHistoryDTO redemptionHistoryDTO = new PetRedemptionHistoryDTO();
					redemptionHistoryDTO.setPetId(rs.getInt("PET_ID"));
					redemptionHistoryDTO.setTotalPoints(rs.getInt("TOTAL_POINTS"));
					redemptionHistoryDTO.setPointsRedeemed(rs.getInt("POINTS_REDEEMED"));
					redemptionHistoryDTO.setBalancePoints(rs.getInt("POINTS_BALANCE"));

					redemptionHistoryDTO.setRedeemedByUser(rs.getString("FULL_NAME"));
					Timestamp createdDate = (Timestamp) rs.getTimestamp("CREATED_DATE");
					redemptionHistoryDTO.setCreatedDate(createdDate != null ? createdDate.toLocalDateTime() : null);

					redemptionHistoryDTOs.add(redemptionHistoryDTO);
				}
			}, petId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetRedemptionHistory", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return redemptionHistoryDTOs;
	}

	@Override
	public void assignSensorToPet(AssignSensorRequest assignSensorRequest) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", assignSensorRequest.getPetId());
			inputParams.put("p_pet_parent_id", assignSensorRequest.getPetParentId());
			inputParams.put("p_device_number", assignSensorRequest.getDeviceNumber());
			inputParams.put("p_device_type", assignSensorRequest.getDeviceType());
			inputParams.put("p_assigned_date", assignSensorRequest.getAssignedDate());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_ASSIGN_PET_SENSOR, inputParams);
			LOGGER.info("outParams are {}", outParams);

			// System.out.println(outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");
			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				// getting the inserted flag value
				Integer outFlag = (int) outParams.get("out_flag");
				LOGGER.info("Sensor has been assigned successfully ", outFlag);
			} else {
				if (statusFlag == -2) {
					throw new ServiceExecutionException(
							"assignSensorToPet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(),
							Arrays.asList(new WearablesError(WearablesErrorCode.INVALID_DEVICE_NUMBER,
									assignSensorRequest.getDeviceNumber())));

				} else if (statusFlag == -3) {
					throw new ServiceExecutionException(
							"assignSensorToPet service validation failed cannot proceed further",
							Status.BAD_REQUEST.getStatusCode(), Arrays.asList(
									new WearablesError(WearablesErrorCode.INVALID_STUDY_VIRTUAL_STUDY_ALREADY_MAPPED)));
				} else {
					throw new ServiceExecutionException(errorMsg);
				}
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing assignSensorToPet ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PetDTO> getPetDevicesByPetParent(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("getPetDevicesByPetParent called");
		List<PetDTO> petDevices = new ArrayList<>();
		Map<Integer, List<DeviceDTO>> devicesMap = new HashMap<>();
		long startTime = System.currentTimeMillis();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_PET_DEVICES_BY_PET_PARENT_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetDTO petDTO = new PetDTO();
					petDTO.setPetID(rs.getInt("PET_ID"));
					petDTO.setPetName(rs.getString("PET_NAME"));

					String imageName = rs.getString("PHOTO_NAME");
					if (StringUtils.isNotEmpty(imageName)) {
						// String photoUrl = photoPath + imageName;
						// petDeviceDTO.setPhotoUrl(photoUrl);
						petDTO.setPhotoUrl(gcpClientUtil.getDownloaFiledUrl(imageName, Constants.GCP_PET_PHOTO_PATH));
					}
					if (StringUtils.isNotEmpty(rs.getString("weightData"))) {
						String[] weightData = rs.getString("weightData").split("###");
						if (("lbs").equals(weightData[Constants.NUMBER_TWO])) {
							petDTO.setWeight(weightData[Constants.NUMBER_ZERO]);
						} else {
							petDTO.setWeight(weightData[Constants.NUMBER_ONE]);
						}
						petDTO.setWeightUnit(weightData[Constants.NUMBER_TWO]);
					}
					petDTO.setPetAge(rs.getString("PET_AGE"));
					petDTO.setPetBreed(rs.getString("BREED_NAME"));
					petDTO.setSpeciesId(rs.getString("SPECIES_ID"));
					petDTO.setBirthday(rs.getString("BIRTHDAY"));
					petDTO.setGender(rs.getString("GENDER"));
					petDTO.setIsNeutered(rs.getBoolean("IS_NEUTERED"));
					petDTO.setIsPetEditable(rs.getInt("IS_PET_EDITABLE"));
					petDTO.setPetStatus(rs.getInt("PET_STATUS_ID"));

					DeviceDTO deviceDto = new DeviceDTO();

					deviceDto.setDeviceId(rs.getInt("DEVICE_ID"));
					deviceDto.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					deviceDto.setDeviceType(rs.getString("DEVICE_TYPE"));
					deviceDto.setDeviceModel(rs.getString("DEVICE_MODEL"));
					deviceDto.setBattery(rs.getString("BATTERY"));
					deviceDto.setFirmware(rs.getString("FIRMWARE"));
					deviceDto.setFirmwareNew(rs.getString("FIRMWARE_NEW"));
					deviceDto.setIsDeviceSetupDone(rs.getBoolean("IS_DEVICE_SETUP_DONE"));
					deviceDto.setIsFirmwareVersionUpdateRequired(rs.getBoolean("IS_FIRMWARE_VERSION_UPDATE_REQUIRED"));
					deviceDto.setLastSeen(rs.getString("LAST_SEEN"));

					if (devicesMap.containsKey(petDTO.getPetID())) {
						if (deviceDto.getDeviceId() != 0) {
							devicesMap.get(petDTO.getPetID()).add(deviceDto);
						}
					} else {
						List<DeviceDTO> devices = new ArrayList<>();
						if (deviceDto.getDeviceId() != 0) {
							devices.add(deviceDto);
						}
						devicesMap.put(petDTO.getPetID(), devices);
						petDTO.setDevices(devicesMap.get(petDTO.getPetID()));
						petDevices.add(petDTO);
					}
				}
			}, petParentId);
			long endTime = System.currentTimeMillis();
			LOGGER.info("Time taken to execute getPetDevicesByPetParent service in millis is {}",
					(endTime - startTime));
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetDevicesByPetParent", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petDevices;
	}

	@Override
	public List<MobileAppFeedback> getFeedbackByPetParent(int petParentId) throws ServiceExecutionException {
		List<MobileAppFeedback> mobileAppFeedbackList = new ArrayList<>();
		LOGGER.debug("getMobileAppFeedback called");
		try {
			jdbcTemplate.query(MOBILE_APP_FEEDBACK_LIST, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MobileAppFeedback mobileAppFeedback = new MobileAppFeedback();

					// set the column values to fields like below
					mobileAppFeedback.setFeedBackId(rs.getInt("app_feedback_id"));
					mobileAppFeedback.setStudyName(rs.getString("study_name"));
					mobileAppFeedback.setPetOwnerName(rs.getString("pet_owner_name"));
					mobileAppFeedback.setPetName(rs.getString("pet_name"));
					mobileAppFeedback.setPageName(rs.getString("page_name"));
					mobileAppFeedback.setDeviceType(rs.getString("device_type"));
					mobileAppFeedback.setFeedback(rs.getString("feed_back_text"));
					mobileAppFeedback.setFeedbackDate(rs.getTimestamp("created_date").toLocalDateTime());
					mobileAppFeedbackList.add(mobileAppFeedback);
				}
			}, petParentId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getMobileAppFeedback", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return mobileAppFeedbackList;
	}

	@Override
	public List<String> getDeviceTypes() throws ServiceExecutionException {
		List<String> deviceTypeList = new ArrayList<>();
		LOGGER.debug("getDeviceType called");
		try {
			jdbcTemplate.query(MOBILE_APP_GET_DEVICE_TYPES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					deviceTypeList.add(resultSet.getString("DEVICE_TYPE"));
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceType ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceTypeList;
	}

	@Override
	public List<String> getDeviceModels(String deviceType) throws ServiceExecutionException {
		List<String> deviceModelList = new ArrayList<>();
		LOGGER.debug("getDeviceModel called");
		try {
			jdbcTemplate.query(MOBILE_APP_GET_DEVICE_MODELS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet resultSet) throws SQLException {
					deviceModelList.add(resultSet.getString("DEVICE_MODEL"));
				}
			}, deviceType);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceModel ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceModelList;
	}
}
