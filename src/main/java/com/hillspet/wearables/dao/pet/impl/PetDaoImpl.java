package com.hillspet.wearables.dao.pet.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.GCPClientUtil;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.pet.PetDao;
import com.hillspet.wearables.dto.EatingEnthusiasmScale;
import com.hillspet.wearables.dto.ImageScoringScale;
import com.hillspet.wearables.dto.ImageScoringScaleDetails;
import com.hillspet.wearables.dto.ObservationPhoto;
import com.hillspet.wearables.dto.ObservationVideo;
import com.hillspet.wearables.dto.PetBehavior;
import com.hillspet.wearables.dto.PetBreed;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;
import com.hillspet.wearables.dto.PetFeedingPreference;
import com.hillspet.wearables.dto.PetFeedingTime;
import com.hillspet.wearables.dto.PetMobileAppConfig;
import com.hillspet.wearables.dto.PetObservation;
import com.hillspet.wearables.dto.PetSpecies;
import com.hillspet.wearables.dto.PetWeightDTO;
import com.hillspet.wearables.dto.PetWeightHistoryDTO;
import com.hillspet.wearables.request.AddPetWeight;
import com.hillspet.wearables.request.PetAddFeedingPreferences;
import com.hillspet.wearables.request.PetAddImageScoring;
import com.hillspet.wearables.request.UpdatePet;
import com.hillspet.wearables.request.UpdatePetWeight;

@Repository
public class PetDaoImpl extends BaseDaoImpl implements PetDao {

	@Value("${gcp.env}")
	private String environment;

	@Autowired
	private GCPClientUtil gcpClientUtil;

	public static final String RESULT_SET_1 = "#result-set-1";
	public static final String RESULT_SET_2 = "#result-set-2";
	public static final String RESULT_SET_3 = "#result-set-3";

	private static String PET_ADD_WEIGHT = "MOBILE_APP_ADD_PET_WEIGHT";
	private static String PET_UPDATE_WEIGHT = "MOBILE_APP_UPDATE_PET_WEIGHT";
	private static String MOBILE_APP_UPDATE_PET_PHOTO = "MOBILE_APP_UPDATE_PET_PHOTO";
	private static String PET_GET_WEIGHT_HISTORY = "CALL MOBILE_APP_GET_PET_WEIGHT_HISTORY_LIST(?)";
	private static String GET_SPECIES = "CALL MOBILE_APP_GET_SPECIES()";
	private static String PET_GET_BREEDS = "CALL MOBILE_APP_GET_PET_BREEDS_BY_SPEICES(?)";
	private static String PET_GET_BEHAVIORS = "CALL MOBILE_APP_GET_PET_BEHAVIORS_BY_SPECIES(?,?)";
	private static String GET_ENTHUSIASM_SCALES = "CALL MOBILE_APP_GET_ENTHUSIASM_SCALES()";
	private static String GET_PET_FEEDING_TIMES = "CALL MOBILE_APP_GET_PET_FEEDING_TIMES()";
	private static String PET_GET_MOBILE_APP_CONFIGS = "CALL MOBILE_APP_GET_MOBILE_APP_CONFIGS_BY_PET_ID(?)";
	private static String ADD_PET_FEEDING_TIME_ENTSM_SCALE = "MOBILE_APP_INSERT_PET_FEEDING_ENTHUSIASM_SCALE";
	private static String SAVE_PET_OBSERVATION = "MOBILE_APP_SAVE_OBSERVATION_INFO";
	private static String DELETE_PET_OBSERVATION = "MOBILE_APP_DELETE_OBSERVATION_INFO";
	private static String GET_PET_IMAGE_SCORINGS = "MOBILE_APP_GET_PET_IMAGE_SCORINGS";
	private static String ADD_PET_IMAGE_SCORINGS = "MOBILE_APP_INSERT_PET_IMAGE_SCORINGS";
	private static String GET_PET_FEEDING_PREFERENCES = "CALL MOBILE_APP_GET_PET_FEEDING_PREFERENCES()";
	private static String ADD_PET_FEEDING_PREFERENCES = "MOBILE_APP_INSERT_PET_FEEDING_PREFERENCES";
	private static String MOBILE_APP_GET_OBSERVATIONS_BY_PET_ID = "MOBILE_APP_GET_OBSERVATIONS_BY_PET_ID";
	private static String MOBILE_APP_UPDATE_PET_PROFILE = "MOBILE_APP_UPDATE_PET_PROFILE";

	private static final Logger LOGGER = LogManager.getLogger(PetDaoImpl.class);

	@Override
	public PetWeightDTO addPetWeight(AddPetWeight addPetWeight) throws ServiceExecutionException {
		PetWeightDTO petWeightDTO = new PetWeightDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", addPetWeight.getPetId());
			inputParams.put("p_weight", addPetWeight.getWeight());
			inputParams.put("p_weight_unit", addPetWeight.getWeightUnit());
			inputParams.put("p_user_id", addPetWeight.getUserId());
			inputParams.put("p_add_date", addPetWeight.getAddDate());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(PET_ADD_WEIGHT, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				Integer petWeightId = (int) outParams.get("last_insert_id");
				addPetWeight.setPetWeightId(petWeightId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
			BeanUtils.copyProperties(addPetWeight, petWeightDTO);
		} catch (Exception e) {
			LOGGER.error("error while executing addPetWeight ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petWeightDTO;
	}

	@Override
	public PetWeightDTO updateWeight(UpdatePetWeight updatePetWeight) throws ServiceExecutionException {
		PetWeightDTO petWeightDTO = new PetWeightDTO();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_weight_id", updatePetWeight.getPetWeightId());
			inputParams.put("p_pet_id", updatePetWeight.getPetId());
			inputParams.put("p_weight", updatePetWeight.getWeight());
			inputParams.put("p_weight_unit", updatePetWeight.getWeightUnit());
			inputParams.put("p_user_id", updatePetWeight.getUserId());
			inputParams.put("p_modified_date", updatePetWeight.getModifiedDate());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(PET_UPDATE_WEIGHT, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag < NumberUtils.INTEGER_ZERO) {
				throw new ServiceExecutionException(errorMsg);
			}
			BeanUtils.copyProperties(updatePetWeight, petWeightDTO);
		} catch (Exception e) {
			LOGGER.error("error while executing addPetWeight ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petWeightDTO;
	}

	@Override
	public void updatePetPhoto(int petId, int petParentId, String petPhoto) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", petId);
			inputParams.put("p_photo_name", petPhoto);
			inputParams.put("p_pet_parent_id", petParentId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_PET_PHOTO, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag < NumberUtils.INTEGER_ZERO) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing addPetWeight ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public List<PetWeightHistoryDTO> getPetWeightHistory(int petId) throws ServiceExecutionException {
		List<PetWeightHistoryDTO> weightList = new ArrayList<>();
		LOGGER.debug("getPetWeightHistory called");
		try {
			jdbcTemplate.query(PET_GET_WEIGHT_HISTORY, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetWeightHistoryDTO dto = new PetWeightHistoryDTO();
					dto.setPetId(rs.getInt("PET_ID"));
					dto.setWeight(rs.getDouble("WEIGHT"));
					dto.setActive(rs.getBoolean("IS_ACTIVE"));
					dto.setAddDate(rs.getTimestamp("ADD_DATE").toLocalDateTime());
					dto.setCreatedBy(rs.getInt("CREATED_BY"));
					dto.setModifiedBy(rs.getInt("MODIFIED_BY"));
					dto.setWeightUnit(rs.getString("WEIGHT_UNIT"));
					dto.setPetWeightId(rs.getInt("PET_WEIGHT_ID"));
					Timestamp createDate = rs.getTimestamp("CREATED_DATE");
					if (createDate != null) {
						dto.setCreatedDate(createDate.toLocalDateTime());
					}
					Timestamp modifiedDate = rs.getTimestamp("MODIFIED_DATE");
					if (modifiedDate != null) {
						dto.setModifiedDate(modifiedDate.toLocalDateTime());
					}
					weightList.add(dto);
				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetWeightHistory", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return weightList;
	}

	@Override
	public List<PetSpecies> getPetSpecies() throws ServiceExecutionException {
		List<PetSpecies> speciesList = new ArrayList<>();
		LOGGER.debug("getPetSpecies called");
		try {
			jdbcTemplate.query(GET_SPECIES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetSpecies species = new PetSpecies();
					species.setSpeciesId(rs.getInt("SPECIES_ID"));
					species.setSpeciesName(rs.getString("SPECIES_NAME"));
					speciesList.add(species);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetSpecies ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return speciesList;
	}

	@Override
	public List<PetBreed> getPetBreeds(int speciesId) throws ServiceExecutionException {
		List<PetBreed> breedList = new ArrayList<>();
		LOGGER.debug("getPetBreeds called");
		try {
			jdbcTemplate.query(PET_GET_BREEDS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetBreed petBreed = new PetBreed();
					petBreed.setBreedId(rs.getInt("BREED_ID"));
					petBreed.setBreedName(rs.getString("BREED_NAME"));
					breedList.add(petBreed);
				}
			}, speciesId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetBreeds", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return breedList;
	}

	@Override
	public List<PetBehavior> getPetBehaviors(int speciesId, int behaviorTypeId) throws ServiceExecutionException {
		List<PetBehavior> behaviorList = new ArrayList<>();
		LOGGER.debug("getPetBehaviors called");
		try {
			jdbcTemplate.query(PET_GET_BEHAVIORS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetBehavior petBehavior = new PetBehavior();
					petBehavior.setBehaviorId(rs.getInt("METRIC_ID"));
					petBehavior.setBehaviorName(rs.getString("METRIC_NAME"));
					petBehavior.setSpeciesId(rs.getInt("SPECIES_ID"));
					petBehavior.setBehaviorTypeId(rs.getInt("TYPE"));
					behaviorList.add(petBehavior);
				}
			}, speciesId, behaviorTypeId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetBehaviors", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return behaviorList;
	}

	@Override
	public List<EatingEnthusiasmScale> getPetEatingEnthusiasmScale() throws ServiceExecutionException {
		List<EatingEnthusiasmScale> eatingEnthusiasmScales = new ArrayList<>();
		LOGGER.debug("getPetEatingEnthusiasmScale called");
		try {
			jdbcTemplate.query(GET_ENTHUSIASM_SCALES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					EatingEnthusiasmScale eatingEnthusiasmScale = new EatingEnthusiasmScale();
					eatingEnthusiasmScale.setEnthusiasmScaleId(rs.getInt("ENTHUSIASM_SCALE_ID"));
					eatingEnthusiasmScale.setEnthusiasmScaleValue(rs.getString("ENTHUSIASM_SCALE"));
					eatingEnthusiasmScales.add(eatingEnthusiasmScale);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetEatingEnthusiasmScale", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return eatingEnthusiasmScales;
	}

	@Override
	public List<PetFeedingTime> getPetFeedingTime() throws ServiceExecutionException {
		List<PetFeedingTime> petFeedingTimes = new ArrayList<>();
		LOGGER.debug("getPetFeedingTime called");
		try {
			jdbcTemplate.query(GET_PET_FEEDING_TIMES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetFeedingTime petFeedingTime = new PetFeedingTime();
					petFeedingTime.setFeedingTimeId(rs.getInt("PET_FEEDING_TIME_ID"));
					petFeedingTime.setFeedingTime(rs.getString("FEEDING_VALUE"));
					petFeedingTimes.add(petFeedingTime);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFeedingTime ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petFeedingTimes;
	}

	@Override
	public List<PetMobileAppConfig> getMobileAppConfigs(int petId) throws ServiceExecutionException {
		List<PetMobileAppConfig> petMobileAppConfigs = new ArrayList<>();
		LOGGER.debug("getPetBreeds called");
		try {
			jdbcTemplate.query(PET_GET_MOBILE_APP_CONFIGS, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetMobileAppConfig petMobileAppConfig = new PetMobileAppConfig();
					petMobileAppConfig.setMobileAppConfigId(rs.getInt("MOBILE_APP_CONFIG_ID"));
					petMobileAppConfig.setMobileAppConfigName(rs.getString("MOBILE_APP_CONFIG_NAME"));
					petMobileAppConfig.setWeightUnit(rs.getString("WEIGHT_UNIT"));
					if (rs.getDate("ENTSM_SCALE_START_DATE") != null) {
						petMobileAppConfig.setEnthsmScaleStartDate(rs.getDate("ENTSM_SCALE_START_DATE").toLocalDate());
						petMobileAppConfig.setEnthsmScaleEndDate(rs.getDate("ENTSM_SCALE_END_DATE").toLocalDate());
					}

					petMobileAppConfigs.add(petMobileAppConfig);
				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetBreeds", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petMobileAppConfigs;
	}

	@Override
	public PetFeedingEnthusiasmScale addPetFeedingTime(PetFeedingEnthusiasmScale petFeedingEnthusiasmScale)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", petFeedingEnthusiasmScale.getPetId());
			inputParams.put("p_entsm_scale_id", petFeedingEnthusiasmScale.getEnthusiasmScaleId());
			inputParams.put("p_feeding_time_id", petFeedingEnthusiasmScale.getFeedingTimeId());
			inputParams.put("p_feeding_date", petFeedingEnthusiasmScale.getFeedingDate());
			inputParams.put("p_pet_parent_id", petFeedingEnthusiasmScale.getPetParentId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(ADD_PET_FEEDING_TIME_ENTSM_SCALE, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				Integer feedingEnthusiasmScaleId = (int) outParams.get("last_insert_id");
				petFeedingEnthusiasmScale.setFeedingEnthusiasmScaleId(feedingEnthusiasmScaleId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing addPetFeedingTime ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petFeedingEnthusiasmScale;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PetObservation> getPetObservationsByPetId(int petId) throws ServiceExecutionException {
		LOGGER.debug("getPetObservationsByPetId called");
		List<PetObservation> petObservations = new ArrayList<>();
		Map<Integer, List<ObservationPhoto>> photoDetailsMap = new HashMap<>();
		Map<Integer, List<ObservationVideo>> videoDetailsMap = new HashMap<>();
		long startTime = System.currentTimeMillis();
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(MOBILE_APP_GET_OBSERVATIONS_BY_PET_ID,
					inputParams);
			LOGGER.info("outParams are {}", simpleJdbcCallResult);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(photoObservations -> {
						ObservationPhoto observationPhoto = new ObservationPhoto();
						observationPhoto.setObservationPhotoId((Integer) photoObservations.get("PHOTO_ID"));
						observationPhoto.setFileName((String) photoObservations.get("FILE_NAME"));
						// observationPhoto.setFilePath((String) photoObservations.get("IMAGE_PATH"));

						String imagePathValue = (String) photoObservations.get("IMAGE_PATH");

						if (!StringUtils.contains(imagePathValue, "firebasestorage")) {
							String imageSignedUrl = gcpClientUtil.getDownloaFiledUrl(
									imagePathValue.concat("/").concat(observationPhoto.getFileName()),
									Constants.GCP_OBSERVATION_PHOTO_PATH);
							observationPhoto.setFilePath(imageSignedUrl);
						} else {
							observationPhoto.setFilePath(imagePathValue);
						}

						Integer petObservationId = (Integer) photoObservations.get("PET_OBSERVATION_ID");

						photoDetailsMap.computeIfAbsent(petObservationId, k -> new ArrayList<ObservationPhoto>())
								.add(observationPhoto);

					});
				}

				if (key.equals(RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(videoObservations -> {
						ObservationVideo observationVideo = new ObservationVideo();
						observationVideo.setObservationVideoId((Integer) videoObservations.get("VIDEO_ID"));
						observationVideo.setVideoName((String) videoObservations.get("VIDEO_NAME"));

						String videoURL = (String) videoObservations.get("VIDEO_URL");
						if (StringUtils.isNotBlank(videoURL)) {
							if (!StringUtils.contains(videoURL, "firebasestorage")) {
								String mediaSignedUrl = gcpClientUtil.getDownloaFiledUrl(
										videoURL.replaceAll("https://storage.googleapis.com/wearables-portal-media/"
												+ environment + "/GCloud/WPortal/ObservationVideo/", ""),
										Constants.GCP_OBSERVATION_VIDEO_PATH);
								observationVideo.setVideoUrl(mediaSignedUrl);
							} else {
								observationVideo.setVideoUrl(videoURL);
							}
						}

						String videoThumbnailURL = (String) videoObservations.get("VIDEO_THUMBNAIL_URL");
						if (StringUtils.isNotBlank(videoThumbnailURL)) {
							if (!StringUtils.contains(videoThumbnailURL, "firebasestorage")) {
								String mediaSignedThumbUrl = gcpClientUtil.getDownloaFiledUrl(
										videoThumbnailURL.replaceAll(
												"https://storage.googleapis.com/wearables-portal-media/" + environment
														+ "/GCloud/WPortal/ObservationVideoThumbnail/",
												""),
										Constants.GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH);
								observationVideo.setVideoThumbnailUrl(mediaSignedThumbUrl);
							} else {
								observationVideo.setVideoThumbnailUrl(videoThumbnailURL);
							}
						}

						Integer petObservationId = (Integer) videoObservations.get("PET_OBSERVATION_ID");

						videoDetailsMap.computeIfAbsent(petObservationId, k -> new ArrayList<ObservationVideo>())
								.add(observationVideo);

					});
				}

				if (key.equals(RESULT_SET_3)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(obsDetails -> {
						PetObservation petObservation = new PetObservation();
						petObservation.setPetId((Integer) obsDetails.get("PET_ID"));
						petObservation.setBehaviorId(Integer.parseInt((String) obsDetails.get("ACTIVITY_TYPE_ID")));
						petObservation.setBehaviorName((String) obsDetails.get("ACTIVITY_TYPE"));
						petObservation.setObsText((String) obsDetails.get("OBS_TEXT"));
						petObservation.setObservationDateTime((LocalDateTime) obsDetails.get("OBSERVATION_DATE_TIME"));
						petObservation.setTag((String) obsDetails.get("TAG"));
						petObservation.setEmotionIconsText((String) obsDetails.get("EMOTICONS_TEXT"));
						petObservation.setSeizuresDescription((String) obsDetails.get("SEIZURES_DESCRIPTION"));
						petObservation.setObservationId((Integer) obsDetails.get("PET_OBSERVATION_ID"));
						petObservation.setModifiedDate((LocalDateTime) obsDetails.get("MODIFIED_DATE"));

						petObservation.setPhotos(photoDetailsMap.get(petObservation.getObservationId()) != null
								? photoDetailsMap.get(petObservation.getObservationId())
								: new ArrayList<>());

						petObservation.setVideos(videoDetailsMap.get(petObservation.getObservationId()) != null
								? videoDetailsMap.get(petObservation.getObservationId())
								: new ArrayList<>());
						petObservations.add(petObservation);
					});
				}
			}
			long endTime = System.currentTimeMillis();
			LOGGER.info("Time taken to execute getPetObservationsByPetId service in millis is {}",
					(endTime - startTime));
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetObservationsByPetId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petObservations;
	}

	@Override
	public PetObservation savePetObservation(PetObservation addPetObservation) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_observation_id", addPetObservation.getObservationId());
			inputParams.put("p_pet_id", addPetObservation.getPetId());
			inputParams.put("p_obs_text", addPetObservation.getObsText());
			inputParams.put("p_tag", addPetObservation.getTag());
			inputParams.put("p_behavior_id", addPetObservation.getBehaviorId());
			inputParams.put("p_observation_date_time", addPetObservation.getObservationDateTime());
			inputParams.put("p_emoticons_text", addPetObservation.getEmotionIconsText());
			inputParams.put("p_seizures_description", addPetObservation.getSeizuresDescription());
			inputParams.put("p_login_user_id", addPetObservation.getLoginUserId());
			inputParams.put("p_videos", new ObjectMapper().writeValueAsString(addPetObservation.getVideos()));
			inputParams.put("p_photos", new ObjectMapper().writeValueAsString(addPetObservation.getPhotos()));

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(SAVE_PET_OBSERVATION, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				Integer observationId = (int) outParams.get("last_insert_id");
				addPetObservation.setObservationId(observationId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing savePetObservation ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return addPetObservation;
	}

	@Override
	public void deletePetObservation(int observationId, int petId, int petParentId) throws ServiceExecutionException {
		LOGGER.debug("deletePetObservation called");
		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("p_observation_id", observationId);
		inputParams.put("p_pet_id", petId);
		inputParams.put("p_pet_parent_id", petParentId);
		try {
			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(DELETE_PET_OBSERVATION, inputParams);
			LOGGER.info("outParams are {}", outParams);
			String errorMsg = (String) outParams.get("out_error_msg");
			if (StringUtils.isNotEmpty(errorMsg) || (int) outParams.get("out_flag") < NumberUtils.INTEGER_ONE) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (SQLException e) {
			LOGGER.error("error while executing deletePetObservation", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ImageScoringScale> getPetImageScoringScales(int petId) throws ServiceExecutionException {
		List<ImageScoringScale> imageScoringScales = new ArrayList<>();
		Map<Integer, List<ImageScoringScaleDetails>> scoreDetailsMap = new HashMap<>();

		LOGGER.debug("getPetImageScoringScales called");
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> simpleJdbcCallResult = callStoredProcedure(GET_PET_IMAGE_SCORINGS, inputParams);
			LOGGER.info("outParams are {}", simpleJdbcCallResult);
			Iterator<Entry<String, Object>> itr = simpleJdbcCallResult.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();

					list.forEach(scaleDetails -> {
						ImageScoringScaleDetails scoringScaleDetails = new ImageScoringScaleDetails();
						scoringScaleDetails
								.setImageScoringDetailsId((Integer) scaleDetails.get("IMAGE_SCORING_DTLS_ID"));
						scoringScaleDetails.setScore((Integer) scaleDetails.get("SCORE"));
						scoringScaleDetails.setUom((Integer) scaleDetails.get("UOM"));
						scoringScaleDetails.setUnitName((String) scaleDetails.get("UNIT_NAME"));
						scoringScaleDetails.setDescription((String) scaleDetails.get("DESCRIPTION"));
						scoringScaleDetails.setImageLabel((String) scaleDetails.get("IMAGE_LABEL"));
						String imgPath = (String) scaleDetails.get("IMAGE_PATH");

						if (StringUtils.isNotEmpty(imgPath)) {
							scoringScaleDetails.setImagePath(
									gcpClientUtil.getDownloaFiledUrl(imgPath, Constants.GCP_IMAGE_SCORING_PATH));
						}

						Integer imageScoringScaleId = (Integer) scaleDetails.get("IMAGE_SCORING_ID");

						scoreDetailsMap
								.computeIfAbsent(imageScoringScaleId, k -> new ArrayList<ImageScoringScaleDetails>())
								.add(scoringScaleDetails);
					});
				}

				if (key.equals(RESULT_SET_2)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(imageScales -> {
						ImageScoringScale imageScoringScale = new ImageScoringScale();
						imageScoringScale.setImageScoringScaleId((Integer) imageScales.get("IMAGE_SCORING_ID"));
						imageScoringScale.setImageScaleName((String) imageScales.get("IMAGE_SCALE_NAME"));
						imageScoringScale.setClassificationId((Integer) imageScales.get("CLASSIFICATION_ID"));
						imageScoringScale.setClassification((String) imageScales.get("CLASSIFICATION"));
						imageScoringScale.setScoringTypeId((Integer) imageScales.get("SCORING_TYPE_ID"));
						imageScoringScale.setScoringType((String) imageScales.get("SCORING_TYPE"));
						imageScoringScale.setSpeciesId((Integer) imageScales.get("SPECIES_ID"));
						imageScoringScale.setSpeciesName((String) imageScales.get("SPECIES_NAME"));

						imageScoringScale.setScoringScaleDetails(
								scoreDetailsMap.get(imageScoringScale.getImageScoringScaleId()) != null
										? scoreDetailsMap.get(imageScoringScale.getImageScoringScaleId())
										: new ArrayList<>());
						imageScoringScales.add(imageScoringScale);
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetImageScoringScales", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return imageScoringScales;
	}

	@Override
	public PetAddImageScoring addPetImageScoring(PetAddImageScoring addPetImageScoring)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_image_score_type_id", addPetImageScoring.getImageScoreType());
			inputParams.put("p_image_score_id", addPetImageScoring.getImageScoringId());
			inputParams.put("p_pet_id", addPetImageScoring.getPetId());
			inputParams.put("p_image_score_dtls_json",
					new ObjectMapper().writeValueAsString(addPetImageScoring.getPetImgScoreDetails()));
			inputParams.put("p_pet_parent_id", addPetImageScoring.getPetParentId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(ADD_PET_IMAGE_SCORINGS, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (StringUtils.isEmpty(errorMsg) && statusFlag > NumberUtils.INTEGER_ZERO) {
				Integer petImageScoringId = (int) outParams.get("last_insert_id");
				addPetImageScoring.setPetImageScoringId(petImageScoringId);
			} else {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing addPetImageScoring ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return addPetImageScoring;
	}

	@Override
	public List<PetFeedingPreference> getPetFeedingPreferences() throws ServiceExecutionException {
		List<PetFeedingPreference> petFeedingPreferences = new ArrayList<>();
		LOGGER.debug("getPetFeedingPreferences called");
		try {
			jdbcTemplate.query(GET_PET_FEEDING_PREFERENCES, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetFeedingPreference petFeedingPreference = new PetFeedingPreference();
					petFeedingPreference.setFeedingPreferenceId(rs.getInt("FEEDING_PREFERENCE_ID"));
					petFeedingPreference.setFeedingPreference(rs.getString("PREFERENCE_NAME"));
					petFeedingPreferences.add(petFeedingPreference);
				}
			});
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetFeedingPreferences ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petFeedingPreferences;
	}

	@Override
	public void addPetFeedingPreferences(PetAddFeedingPreferences petAddFeedingPreferences)
			throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", petAddFeedingPreferences.getPetId());
			inputParams.put("p_pet_feeding_preferences_json",
					new ObjectMapper().writeValueAsString(petAddFeedingPreferences.getPetFeedingPreferences()));
			inputParams.put("p_user_id", petAddFeedingPreferences.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(ADD_PET_FEEDING_PREFERENCES, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (!StringUtils.isEmpty(errorMsg) && !(statusFlag > NumberUtils.INTEGER_ZERO)) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing addPetImageScoring ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updatePet(UpdatePet updatePet) throws ServiceExecutionException {
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", updatePet.getPetId());
			inputParams.put("p_pet_name", updatePet.getPetName());
			inputParams.put("p_gender", updatePet.getGender());
			inputParams.put("p_is_neutered", updatePet.getIsNeutered());
			inputParams.put("p_breed_id", updatePet.getBreedId());
			inputParams.put("p_dob", updatePet.getBirthDay());
			inputParams.put("p_dob_unknown", updatePet.getIsUnknown());
			inputParams.put("p_weight", updatePet.getWeight());
			inputParams.put("p_weight_unit", updatePet.getWeightUnit());
			inputParams.put("p_user_id", updatePet.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_PET_PROFILE, inputParams);
			LOGGER.info("outParams are {}", outParams);

			String errorMsg = (String) outParams.get("out_error_msg");
			int statusFlag = (int) outParams.get("out_flag");

			if (!StringUtils.isEmpty(errorMsg) && !(statusFlag > NumberUtils.INTEGER_ZERO)) {
				throw new ServiceExecutionException(errorMsg);
			}
		} catch (Exception e) {
			LOGGER.error("error while executing updatePet", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}
}