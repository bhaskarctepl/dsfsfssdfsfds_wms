package com.hillspet.wearables.dao.user.impl;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.threeten.bp.LocalDate;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dao.BaseDaoImpl;
import com.hillspet.wearables.dao.user.MigratedDao;
import com.hillspet.wearables.dto.ClientInfo;
import com.hillspet.wearables.dto.ClientSMSCode;
import com.hillspet.wearables.dto.DeviceAssignDTO;
import com.hillspet.wearables.dto.DeviceInfo;
import com.hillspet.wearables.dto.MobileAppFeedbackDTO;
import com.hillspet.wearables.dto.MonitoringPlan;
import com.hillspet.wearables.dto.OnboardingInfo;
import com.hillspet.wearables.dto.PetCheckedInfo;
import com.hillspet.wearables.dto.PetInfoDTO;
import com.hillspet.wearables.dto.PetParentKeyInfoDTO;
import com.hillspet.wearables.dto.SensorDetailsDTO;
import com.hillspet.wearables.dto.TimerLog;

@Repository
public class MigratedDaoImpl extends BaseDaoImpl implements MigratedDao {

	private static final Logger LOGGER = LogManager.getLogger(MigratedDaoImpl.class);

	public static final String RESULT_SET_1 = "#result-set-1";
	public static final String RESULT_SET_2 = "#result-set-2";
	public static final String RESULT_SET_3 = "#result-set-3";
	public static final String RESULT_SET_4 = "#result-set-4";

	public static final String MOBILE_APP_GET_CLIENT_INFO_BY_EMAIL = "call MOBILE_APP_GET_PET_PARENT_INFO_BY_EMAIL (?)";
	public static final String MOBILE_APP_VALIDATE_PET_PARENT_LOGIN = "MOBILE_APP_VALIDATE_PET_PARENT_LOGIN";
	public static final String MOBILE_APP_INSERT_PET_PARENT_KEY = "MOBILE_APP_INSERT_PET_PARENT_KEY";
	public static final String MOBILE_APP_UPDATE_PET_PARENT_INFO = "MOBILE_APP_UPDATE_PET_PARENT_INFO";
	public static final String MOBILE_APP_GET_PET_PARENT_SMS_CODE_BY_PET_PARENT_ID = "call MOBILE_APP_GET_PET_PARENT_SMS_CODE_BY_PET_PARENT_ID(?)";
	public static final String MOBILE_APP_UPDATE_PET_PARENT_SMS_CODE = "MOBILE_APP_UPDATE_PET_PARENT_SMS_CODE";
	public static final String MOBILE_APP_INSERT_PET_PARENT_SMS_CODE = "MOBILE_APP_INSERT_PET_PARENT_SMS_CODE";
	public static final String MOBILE_APP_GET_SMS_CODE_BY_PET_PARENT_ID_AND_VERIFICATION_CODE = "MOBILE_APP_GET_SMS_CODE_BY_PET_PARENT_ID_AND_VERIFICATION_CODE";
	public static final String MOBILE_APP_UPDATE_PET_PARENT_PASSWORD = "MOBILE_APP_UPDATE_PET_PARENT_PASSWORD";
	public static final String MOBILE_APP_GET_PET_LIST_BY_PET_PARENT_ID = "call MOBILE_APP_GET_PET_LIST_BY_PET_PARENT_ID(?)";
	public static final String MOBILE_APP_GET_DEVICE_ASSIGN_LIST_BY_PLAN_ID_AND_PET_ID = "CALL MOBILE_APP_GET_DEVICE_ASSIGN_LIST_BY_PLAN_ID_AND_PET_ID(?,?)";
	public static final String MOBILE_APP_PET_PARENT_AUTH_BY_KEY = "MOBILE_APP_PET_PARENT_AUTH_BY_KEY";
	public static final String MOBILE_APP_GET_PET_PARENT_INFO_BY_PET_PARENT_ID = "call MOBILE_APP_GET_PET_PARENT_INFO_BY_PET_PARENT_ID (?)";
	public static final String MOBILE_APP_GET_PET_PARENT_PASSWORD_BY_PET_PARENT_ID = "call MOBILE_APP_GET_PET_PARENT_PASSWORD_BY_PET_PARENT_ID(?)";
	public static final String MOBILE_APP_GET_PET_TIMER_LOG = "call MOBILE_APP_GET_PET_TIMER_LOG(?)";
	public static final String MOBILE_APP_INSERT_PET_TIMER_LOG = "MOBILE_APP_INSERT_PET_TIMER_LOG";
	public static final String MOBILE_APP_INSERT_MOBILE_APP_SCREENS_FEEDBACK = "MOBILE_APP_INSERT_MOBILE_APP_SCREENS_FEEDBACK";
	public static final String MOBILE_APP_UPDATE_SENSOR_SETUP_STATUS = "MOBILE_APP_UPDATE_SENSOR_SETUP_STATUS";
	public static final String MOBILE_APP_GET_SENSOR_SETUP_STATUS = "MOBILE_APP_GET_SENSOR_SETUP_STATUS";
	public static final String MOBILE_APP_INSERT_UPDATE_SENSOR_CHARGING_NOTIFICATION_SETTINGS = "MOBILE_APP_INSERT_UPDATE_SENSOR_CHARGING_NOTIFICATION_SETTINGS";
	public static final String MOBILE_APP_GET_DEVICE_ASSIGNMENT_BY_DEVICE_NUMBER = "MOBILE_APP_GET_DEVICE_ASSIGNMENT_BY_DEVICE_NUMBER";
	public static final String MOBILE_APP_INSERT_PET_PARENT_INFO = "MOBILE_APP_INSERT_PET_PARENT_INFO";
	public static final String MOBILE_APP_HANDLE_ONBOARDING_INFO = "MOBILE_APP_HANDLE_ONBOARDING_INFO";
	public static final String MOBILE_APP_GET_ONBOARDING_INFO_BY_UID = "call MOBILE_APP_GET_ONBOARDING_INFO_BY_UID(?)";
	public static final String MOBILE_APP_GET_STUDY_PLANS_LIST_BY_STUDY_ID = "call MOBILE_APP_GET_STUDY_PLANS_LIST_BY_STUDY_ID(?)";
	public static final String MOBILE_APP_GET_PET_INFO_BY_PET_ID = "call MOBILE_APP_GET_PET_INFO_BY_PET_ID(?)";
	public static final String MOBILE_APP_UPDATE_PET_INFO = "MOBILE_APP_UPDATE_PET_INFO";
	public static final String MOBILE_APP_INSERT_PET_INFO = "MOBILE_APP_INSERT_PET_INFO";
	public static final String MOBILE_APP_INSERT_PET_CHECKED_INFO = "MOBILE_APP_INSERT_PET_CHECKED_INFO";
	public static final String MOBILE_APP_INSERT_PET_STUDY_DEVICE = "MOBILE_APP_INSERT_PET_STUDY_DEVICE";
	public static final String MOBLIE_APP_GET_DEVICE_INFO_BY_DEVICE_NUMBER = "call MOBLIE_APP_GET_DEVICE_INFO_BY_DEVICE_NUMBER(?)";
	public static final String MOBILE_APP_INSERT_DEVICE_INFO = "MOBILE_APP_INSERT_DEVICE_INFO";
	public static final String MOBILE_APP_UPDATE_ONBOARDING_ARCHIVED = "MOBILE_APP_UPDATE_ONBOARDING_ARCHIVED";
	public static final String MOBILE_APP_UPDATE_ONBOARDING_STATUS = "MOBILE_APP_UPDATE_ONBOARDING_STATUS";
	public static final String MOBILE_APP_INSERT_MONITORING_PLAN = "MOBILE_APP_INSERT_MONITORING_PLAN";
	public static final String MOBILE_APP_PET_PARENT_LOG_OUT = "MOBILE_APP_PET_PARENT_LOG_OUT";

	public ClientInfo getClientInfoByEmail(String email) throws ServiceExecutionException {
		LOGGER.info("entered into getClientInfoByEmail");
		ClientInfo clientInfo = new ClientInfo();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_CLIENT_INFO_BY_EMAIL, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					// set the column values to fields like below

					clientInfo.setEmail(rs.getString("email"));
					clientInfo.setPhoneNumber(rs.getString("phone_number"));
					clientInfo.setUniqueId(rs.getString("UNIQUE_ID"));
					clientInfo.setClientId(rs.getInt("PET_PARENT_ID"));
				}
			}, email);

			LOGGER.info("clientInfo clientId {}", clientInfo.getClientId());

		} catch (Exception e) {
			LOGGER.error("error while fetching getClientInfoByEmail", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.info("returning from getClientInfoByEmail");
		return clientInfo;
	}

	@SuppressWarnings("unchecked")
	public ClientInfo clientLogin(String email, String password) throws ServiceExecutionException {
		LOGGER.info("entered into clientLogin");

		ClientInfo clientInfo = new ClientInfo();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_email", email);
			inputParams.put("p_password", password);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_VALIDATE_PET_PARENT_LOGIN, inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				if (entry.getValue() instanceof Integer) {
					LOGGER.info("No records found");
				} else {
					LOGGER.info("record found");
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(sensorSetupStatus -> {
						clientInfo.setClientId((Integer) sensorSetupStatus.get("PET_PARENT_ID"));
						clientInfo.setUniqueId((String) sensorSetupStatus.get("UNIQUE_ID"));
						clientInfo.setClientName((String) sensorSetupStatus.get("PET_PARENT_NAME"));
						clientInfo.setFullName((String) sensorSetupStatus.get("FULL_NAME"));
						clientInfo.setEmail((String) sensorSetupStatus.get("EMAIL"));
						clientInfo.setPhoneNumber((String) sensorSetupStatus.get("PHONE_NUMBER"));
						clientInfo.setCustomerID((String) sensorSetupStatus.get("CUSTOMER_ID"));
						clientInfo.setActive((Boolean) sensorSetupStatus.get("IS_ACTIVE"));
					});
				}
			}
			LOGGER.info("returning from clientLogin");
		} catch (SQLException e) {
			LOGGER.error("error while executing clientLogin ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return clientInfo;
	}

	@Override
	public int insertClientKey(PetParentKeyInfoDTO parentKeyInfoDTO) throws ServiceExecutionException {
		LOGGER.debug("entered into insertClientKey");
		int uniqueId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", parentKeyInfoDTO.getPetParentId());
			inputParams.put("p_pet_parent_key", parentKeyInfoDTO.getKey());
			inputParams.put("p_is_expired", parentKeyInfoDTO.getIsExpired());
			inputParams.put("p_add_date", parentKeyInfoDTO.getAddDate());
			inputParams.put("p_login_user_id", parentKeyInfoDTO.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_PET_PARENT_KEY, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from insertClientKey");
		} catch (SQLException e) {
			LOGGER.error("error while executing insertClientKey ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return uniqueId;
	}

	@Override
	public boolean updateClientInfo(ClientInfo clientInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into updateClientInfo");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", clientInfo.getClientId());
			inputParams.put("p_full_name", clientInfo.getFullName());
			inputParams.put("p_first_name", clientInfo.getFirstName());
			inputParams.put("p_last_name", clientInfo.getLastName());
			inputParams.put("p_email", clientInfo.getEmail());
			inputParams.put("p_phone_number", clientInfo.getPhoneNumber());
			inputParams.put("p_fcm_token", clientInfo.getFcmToken());
			inputParams.put("p_login_user_id", clientInfo.getClientId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_PET_PARENT_INFO, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			int uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from updateClientInfo");
			return uniqueId > 0 ? true : false;
		} catch (SQLException e) {
			LOGGER.error("error while executing updateClientInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public ClientSMSCode getClientSMSCodeByClientID(int clientId) throws ServiceExecutionException {
		LOGGER.debug("entered into getClientSMSCodeByClientID");
		ClientSMSCode clientSMSCode = new ClientSMSCode();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_PET_PARENT_SMS_CODE_BY_PET_PARENT_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					clientSMSCode.setPetParentSMSCodeId(rs.getInt("PET_PARENT_SMS_CODE_ID"));
					clientSMSCode.setClientID(rs.getInt("PET_PARENT_ID"));
					clientSMSCode.setVerificationCode(rs.getString("VERIFICATION_CODE"));
					int expired = rs.getInt("IS_EXPIRED");
					clientSMSCode.setExpired(expired > 0 ? true : false);
					clientSMSCode.setAddDate(rs.getString("ADD_DATE"));
					int active = rs.getInt("IS_ACTIVE");
					clientSMSCode.setIsActive(active > 0 ? true : false);
					clientSMSCode.setCreateDate(rs.getString("CREATED_DATE"));
					clientSMSCode.setUpdateDate(rs.getString("MODIFIED_DATE"));
				}
			}, clientId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getClientSMSCodeByClientID", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getClientSMSCodeByClientID");
		return clientSMSCode;
	}

	@Override
	public int updateClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException {
		LOGGER.debug("entered into updateClientSMSCode");
		int uniqueId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_parent_sms_code_id", clientSMSCode.getPetParentSMSCodeId());
			inputParams.put("p_pet_parent_id", clientSMSCode.getClientID());
			inputParams.put("p_verification_code", clientSMSCode.getVerificationCode());
			inputParams.put("p_is_expired", clientSMSCode.getExpired());
			inputParams.put("p_login_user_id", clientSMSCode.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_PET_PARENT_SMS_CODE, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from updateClientSMSCode");
		} catch (SQLException e) {
			LOGGER.error("error while executing updateClientSMSCode ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return uniqueId;
	}

	@Override
	public int insertClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException {
		LOGGER.debug("entered into insertClientSMSCode");
		int uniqueId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", clientSMSCode.getClientID());
			inputParams.put("p_verification_code", clientSMSCode.getVerificationCode());
			inputParams.put("p_is_expired", clientSMSCode.getExpired());
			inputParams.put("p_add_date", clientSMSCode.getAddDate());
			inputParams.put("p_login_user_id", clientSMSCode.getClientID());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_PET_PARENT_SMS_CODE, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from insertClientSMSCode");
		} catch (SQLException e) {
			LOGGER.error("error while executing insertClientSMSCode ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return uniqueId;
	}

	@SuppressWarnings("unchecked")
	public ClientSMSCode getClientSMSCodeByClientIDAndVerificationCode(int clientID, String verificationCode)
			throws ServiceExecutionException {
		LOGGER.debug("entered into getClientSMSCodeByClientIDAndVerificationCode");
		ClientSMSCode smsCodeDTO = new ClientSMSCode();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", clientID);
			inputParams.put("p_verification_code", verificationCode);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(
					MOBILE_APP_GET_SMS_CODE_BY_PET_PARENT_ID_AND_VERIFICATION_CODE, inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				if (entry.getValue() instanceof Integer) {
					LOGGER.debug("No records found");
				} else {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(sensorSetupStatus -> {
						smsCodeDTO.setVerificationCode((String) sensorSetupStatus.get("VERIFICATION_CODE"));
						smsCodeDTO.setIsActive((Boolean) sensorSetupStatus.get("IS_ACTIVE"));
						LocalDateTime addDate = (LocalDateTime) sensorSetupStatus.get("ADD_DATE");
						smsCodeDTO.setAddDate(addDate.toString());
						LocalDateTime createdDate = (LocalDateTime) sensorSetupStatus.get("CREATED_DATE");
						smsCodeDTO.setCreateDate(createdDate.toString());
						smsCodeDTO.setExpired((Boolean) sensorSetupStatus.get("IS_EXPIRED"));
						LocalDateTime modifiedDate = (LocalDateTime) sensorSetupStatus.get("MODIFIED_DATE");
						smsCodeDTO.setUpdateDate(modifiedDate.toString());
						smsCodeDTO.setClientID((Integer) sensorSetupStatus.get("PET_PARENT_ID"));
						smsCodeDTO.setPetParentSMSCodeId((Integer) sensorSetupStatus.get("PET_PARENT_SMS_CODE_ID"));
					});
				}
			}
			LOGGER.debug("returning from getClientSMSCodeByClientIDAndVerificationCode");
		} catch (

		SQLException e) {
			LOGGER.error("error while executing getClientSMSCodeByClientIDAndVerificationCode ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return smsCodeDTO;
	}

	@Override
	public int updatePassword(ClientInfo clientInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into updatePassword");
		int uniqueId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", clientInfo.getClientId());
			inputParams.put("p_password", clientInfo.getPassword());
			inputParams.put("p_login_user_id", clientInfo.getClientId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_PET_PARENT_PASSWORD, inputParams);
			LOGGER.info("outParams are {}", outParams);

			uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from insertClientKey");
		} catch (SQLException e) {
			LOGGER.error("error while executing insertClientKey ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return uniqueId;
	}

	@Override
	public List<PetInfoDTO> getPetListByPetParentId(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("entered into getPetListByPetParentId");
		List<PetInfoDTO> petList = new ArrayList<PetInfoDTO>();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_PET_LIST_BY_PET_PARENT_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					PetInfoDTO petInfoDTO = new PetInfoDTO();
					petInfoDTO.setPetId(rs.getInt("PET_ID"));
					petInfoDTO.setPetName(rs.getString("PET_NAME"));
					petInfoDTO.setGender(rs.getString("GENDER"));
					petInfoDTO.setBreedId(rs.getInt("BREED_ID"));
					petInfoDTO.setMixBreed(rs.getString("MIX_BREED"));
					petInfoDTO.setIsMixed(rs.getBoolean("IS_MIXED"));
					petInfoDTO.setUnknown(rs.getBoolean("IS_UNKNOWN"));
					petInfoDTO.setNeutered(rs.getBoolean("IS_NEUTERED"));
					petInfoDTO.setPlanId(rs.getInt("PLAN_ID"));
					petInfoDTO.setPlanStatus(rs.getInt("PLAN_STATUS"));
					petList.add(petInfoDTO);
				}
			}, petParentId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetListByPetParentId", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getPetListByPetParentId");
		return petList;
	}

	public List<DeviceAssignDTO> getDeviceAssignListByPlanIDAndPetID(int planId, int petId)
			throws ServiceExecutionException {
		LOGGER.debug("entered into getClientSMSCodeByClientIDAndVerificationCode");
		List<DeviceAssignDTO> deviceAssignList = new ArrayList<DeviceAssignDTO>();
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_plan_id", planId);
			inputParams.put("p_pet_id", petId);

			jdbcTemplate.query(MOBILE_APP_GET_DEVICE_ASSIGN_LIST_BY_PLAN_ID_AND_PET_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					DeviceAssignDTO assignDTO = new DeviceAssignDTO();
					assignDTO.setDeviceID(rs.getInt("DEVICE_ID"));
					assignDTO.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					assignDTO.setPetID(rs.getInt("PET_ID"));
					assignDTO.setPlanID(rs.getInt("PLAN_ID"));
					assignDTO.setDeviceType(rs.getString("DEVICE_TYPE"));
					assignDTO.setNote(rs.getString("NOTE"));
					assignDTO.setFirmware(rs.getString("FIRMWARE"));
					assignDTO.setFirmwareNew((String) rs.getString("FIRMWARE_NEW"));
					assignDTO.setActive(rs.getBoolean("IS_ACTIVE"));
					assignDTO.setIsAssign(
							rs.getInt("IS_ASSIGN") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE : Boolean.FALSE);
					assignDTO.setBattery(rs.getFloat("BATTERY") > 0 ? String.valueOf(rs.getFloat("BATTERY")) : "");

					Date date = rs.getDate("LAST_SEEN");
					if (date != null) {
						java.util.Date utilDate = new java.util.Date();
						utilDate.setTime(date.getTime());

						assignDTO.setLastSeen(utilDate);
					}
					// assignDTO.setAssignDate(assignDate);
					// assignDTO.setUnAssignDate(unAssignDate);
					// assignDTO.setCreateDate(createDate);
					// assignDTO.setUpdateDate(updateDate);
					assignDTO.setLastDataReceived(rs.getString("LAST_DATA_RECEIVED"));

					assignDTO.setDataUploading(rs.getString("DATA_UPLOADING"));
					assignDTO.setIsFirmwareVersionUpdateRequired(
							rs.getInt("IS_FIRMWARE_VERSION_UPDATE_REQUIRED") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
									: Boolean.FALSE);
					assignDTO.setIsDeviceSetupDone(
							rs.getInt("IS_DEVICE_SETUP_DONE") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
									: Boolean.FALSE);
					deviceAssignList.add(assignDTO);
				}
			}, planId, petId);

			LOGGER.debug("returning from getDeviceAssignListByPlanIDAndPetID");
		} catch (Exception e) {
			LOGGER.error("error while executing getDeviceAssignListByPlanIDAndPetID ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceAssignList;
	}

	@SuppressWarnings("unchecked")
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
	public ClientInfo getClientInfoById(String clientId) throws ServiceExecutionException {
		LOGGER.debug("entered into getClientInfoById");
		ClientInfo clientInfo = new ClientInfo();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_PET_PARENT_INFO_BY_PET_PARENT_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					// set the column values to fields like below
					clientInfo.setClientId(rs.getInt("PET_PARENT_ID"));
					clientInfo.setEmail(rs.getString("email"));
					clientInfo.setPhoneNumber(rs.getString("phone_number"));
					clientInfo.setClientId(rs.getInt("pet_parent_id"));
					clientInfo.setFullName(rs.getString("full_name"));
					clientInfo.setFirstName(rs.getString("FIRST_NAME"));
					clientInfo.setLastName(rs.getString("LAST_NAME"));
					clientInfo.setUniqueId(rs.getString("UNIQUE_ID"));
					clientInfo.setFcmToken(rs.getString("FCM_TOKEN"));
					clientInfo.setPhoneNumber(rs.getString("PHONE_NUMBER"));
					clientInfo.setUserId(String.valueOf(rs.getInt("PET_PARENT_ID")));
				}
			}, clientId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getClientInfoById", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getClientInfoById");
		return clientInfo;
	}

	@Override
	public String getPasswordByClientID(int clientId) throws ServiceExecutionException {
		LOGGER.debug("entered into getPasswordByClientID");
		ClientInfo clientInfo = new ClientInfo();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_PET_PARENT_PASSWORD_BY_PET_PARENT_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					clientInfo.setPassword(rs.getString(1));
				}
			}, clientId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getClientKeyByClientID", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getClientKeyByClientID");
		return clientInfo.getPassword();
	}

	@Override
	public List<TimerLog> getPetTimerLog(int petParentId) throws ServiceExecutionException {
		List<TimerLog> petTimerLogDTOs = new ArrayList<TimerLog>();
		LOGGER.debug("getPetTimerLog called");
		try {
			jdbcTemplate.query(MOBILE_APP_GET_PET_TIMER_LOG, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					TimerLog timerLog = new TimerLog();
					timerLog.setCategory(rs.getString("CATEGORY"));
					timerLog.setCreatedDate(rs.getString("CREATED_DATE"));
					timerLog.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					timerLog.setDuration(rs.getString("DURATION"));
					timerLog.setIsActive(rs.getInt("IS_ACTIVE"));
					timerLog.setPetId(rs.getInt("PET_ID"));
					timerLog.setPetName(rs.getString("PET_NAME"));
					timerLog.setPetParentId(rs.getInt("PET_PARENT_ID"));
					timerLog.setPetParentName(rs.getString("PET_PARENT_NAME"));
					timerLog.setPetTimerLogId(rs.getInt("PET_TIMER_LOG_ID"));
					timerLog.setRecName(rs.getString("REC_NAME"));
					timerLog.setTimerDate(rs.getString("TIMER_DATE"));

					petTimerLogDTOs.add(timerLog);
				}
			}, petParentId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getPetTimerLog", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getPetTimerLog");
		return petTimerLogDTOs;
	}

	@Override
	public boolean managePetTimerLog(TimerLog timerLog) throws ServiceExecutionException {
		LOGGER.debug("entered into managePetTimerLog");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", timerLog.getPetId());
			inputParams.put("p_pet_parent_id", timerLog.getPetParentId());
			inputParams.put("p_category", timerLog.getCategory());
			inputParams.put("P_device_number", timerLog.getDeviceNumber());
			inputParams.put("p_duration", timerLog.getDuration());
			inputParams.put("p_timer_date", timerLog.getTimerDate());
			inputParams.put("p_login_user_id", timerLog.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_PET_TIMER_LOG, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			int uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from managePetTimerLog");
			return uniqueId > 0 ? true : false;
		} catch (SQLException e) {
			LOGGER.error("error while executing managePetTimerLog ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public boolean manageMobileAppScreensFeedback(MobileAppFeedbackDTO mobileAppFeedbackDTO)
			throws ServiceExecutionException {
		LOGGER.debug("entered into manageMobileAppScreensFeedback");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", mobileAppFeedbackDTO.getPetParentId());
			inputParams.put("p_pet_id", mobileAppFeedbackDTO.getPetId());
			inputParams.put("p_page_name", mobileAppFeedbackDTO.getPageName());
			inputParams.put("p_device_type", mobileAppFeedbackDTO.getDeviceType());
			inputParams.put("p_feedback_text", mobileAppFeedbackDTO.getFeedbackText());
			inputParams.put("p_login_user_id", mobileAppFeedbackDTO.getLoginUserId());
			if (mobileAppFeedbackDTO.getOpt() != null && mobileAppFeedbackDTO.getOpt().trim().length() > 0) {
				inputParams.put("p_opt", mobileAppFeedbackDTO.getOpt());
			} else {
				inputParams.put("p_opt", 0);
			}

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_MOBILE_APP_SCREENS_FEEDBACK,
					inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			int uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from manageMobileAppScreensFeedback");
			return uniqueId > 0 ? true : false;
		} catch (SQLException e) {
			LOGGER.error("error while executing manageMobileAppScreensFeedback ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	public boolean updateSensorSetupStatus(SensorDetailsDTO sensorDetailsDTO) throws ServiceExecutionException {
		LOGGER.debug("entered into updateSensorSetupStatus");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", sensorDetailsDTO.getPetId());
			inputParams.put("p_pet_parent_id", sensorDetailsDTO.getPetParentId());
			inputParams.put("p_device_number", sensorDetailsDTO.getDeviceNumber());
			inputParams.put("p_setup_status", sensorDetailsDTO.getSetupStatus());
			inputParams.put("p_ssid_list", sensorDetailsDTO.getSsidList());
			inputParams.put("p_login_user_id", sensorDetailsDTO.getPetParentId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_SENSOR_SETUP_STATUS, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			int uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from updateSensorSetupStatus");
			return uniqueId > 0 ? true : false;
		} catch (SQLException e) {
			LOGGER.error("error while executing updateSensorSetupStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SensorDetailsDTO getSensorSetupStatus(String petParentId, String petId) throws ServiceExecutionException {
		SensorDetailsDTO sensorDetailsDTO = new SensorDetailsDTO();
		try {

			// in params
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_pet_id", petId);
			inputParams.put("p_pet_parent_id", petParentId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_GET_SENSOR_SETUP_STATUS, inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				if (entry.getValue() instanceof Integer) {
					LOGGER.debug("No records found");
				} else {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(sensorSetupStatus -> {
						sensorDetailsDTO.setSetupStatus((String) sensorSetupStatus.get("SETUP_STATUS"));
						sensorDetailsDTO.setSsidList((String) sensorSetupStatus.get("SSID_LIST"));
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching Sensor Setup Status", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return sensorDetailsDTO;
	}

	@Override
	public boolean manageSensorChargingNotificationSettings(SensorDetailsDTO sensorDetailsDTO)
			throws ServiceExecutionException {
		LOGGER.debug("entered into manageSensorChargingNotificationSettings");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", sensorDetailsDTO.getPetParentId());
			inputParams.put("p_pet_id", sensorDetailsDTO.getPetId());
			inputParams.put("p_notification_type", sensorDetailsDTO.getNotificationType());
			inputParams.put("p_notification_date", sensorDetailsDTO.getNotificationDate() != null ?  sensorDetailsDTO.getNotificationDate() : LocalDate.now());
			if (sensorDetailsDTO.getOpt() != null && sensorDetailsDTO.getOpt().trim().length() > 0) {
				inputParams.put("p_opt", sensorDetailsDTO.getOpt());
			} else {
				inputParams.put("p_opt", 0);
			}
			inputParams.put("p_login_user_id", sensorDetailsDTO.getPetParentId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(
					MOBILE_APP_INSERT_UPDATE_SENSOR_CHARGING_NOTIFICATION_SETTINGS, inputParams);
			LOGGER.info("outParams are {}", outParams);

			// System.out.println(outParams);
			int uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from manageSensorChargingNotificationSettings");
			return uniqueId > 0 ? true : false;
		} catch (SQLException e) {
			LOGGER.error("error while executing manageSensorChargingNotificationSettings ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public DeviceAssignDTO getDeviceAssignInfoByDeviceNumber(String deviceNumber, String clientId)
			throws ServiceExecutionException {
		LOGGER.debug("entered into getDeviceAssignInfoByDeviceNumber");
		DeviceAssignDTO assignDTO = new DeviceAssignDTO();
		try {
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("p_device_number", deviceNumber);
			inputParams.put("p_pet_parent_id", clientId);

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_GET_DEVICE_ASSIGNMENT_BY_DEVICE_NUMBER,
					inputParams);
			LOGGER.info("outParams are {}", outParams);

			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				if (entry.getValue() instanceof Integer) {
					LOGGER.debug("No records found");
				} else {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(sensorSetupStatus -> {
						assignDTO.setDeviceID((Integer) sensorSetupStatus.get("DEVICE_ID"));
						assignDTO.setDeviceNumber((String) sensorSetupStatus.get("DEVICE_NUMBER"));
						assignDTO.setPetID((Integer) sensorSetupStatus.get("PET_ID"));
						assignDTO.setPetName((String) sensorSetupStatus.get("PET_NAME"));
						assignDTO.setClientID((Integer) sensorSetupStatus.get("PET_PARENT_ID"));
						assignDTO.setClinicName((String) sensorSetupStatus.get("PET_PARENT_NAME"));
						// assignDTO.setStudyId((Integer) sensorSetupStatus.get("STUDY_ID"));
						// assignDTO.setStudyName((String) sensorSetupStatus.get("STUDY_NAME"));

						assignDTO.setIsAssign(
								(Integer) sensorSetupStatus.get("IS_ASSIGN") > NumberUtils.INTEGER_ZERO ? Boolean.TRUE
										: Boolean.FALSE);
						assignDTO.setIsSameClient(
								(Integer) sensorSetupStatus.get("IS_SAME_PET_PARENT") > NumberUtils.INTEGER_ZERO
										? Boolean.TRUE
										: Boolean.FALSE);
					});
				}
			}
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceAssignInfoByDeviceNumber", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getDeviceAssignInfoByDeviceNumber");

		return assignDTO;
	}

	@Override
	public int insertClientInfo(ClientInfo clientInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into insertClientInfo");
		int uniqueId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_name", clientInfo.getClientName());
			inputParams.put("p_unique_id", clientInfo.getUniqueId());
			inputParams.put("p_password", clientInfo.getPassword());
			inputParams.put("p_full_name", clientInfo.getFullName());
			inputParams.put("p_first_name", clientInfo.getFirstName());
			inputParams.put("p_last_name", clientInfo.getLastName());
			inputParams.put("p_email", clientInfo.getEmail());
			inputParams.put("p_phone_number", clientInfo.getPhoneNumber());
			inputParams.put("p_login_user_id", clientInfo.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_PET_PARENT_INFO, inputParams);
			LOGGER.info("outParams are {}", outParams);

			// System.out.println(outParams);
			uniqueId = (int) outParams.get("last_insert_id");

			LOGGER.debug("returning from insertClientInfo");
		} catch (SQLException e) {
			LOGGER.error("error while executing insertClientInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return uniqueId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String handleOnboardingInfo(OnboardingInfo onboardingInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into handleOnboardingInfo");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_uid", onboardingInfo.getUID());
			inputParams.put("p_title", onboardingInfo.getTitle());
			inputParams.put("p_onboarding_data", onboardingInfo.getData());
			inputParams.put("p_user_id", onboardingInfo.getUserID());
			inputParams.put("p_study_id", onboardingInfo.getClinicID());
			inputParams.put("p_is_free", onboardingInfo.isIsFree());
			inputParams.put("p_login_user_id", onboardingInfo.getUserID());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_HANDLE_ONBOARDING_INFO, inputParams);
			LOGGER.info("outParams are {}", outParams);
			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();

				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(instruction -> {
						if (instruction.containsKey("v_newUid")) {
							String uid = (String) instruction.get("v_newUid");
							onboardingInfo.setUID(uid);
						}
					});
				}
			}
			LOGGER.debug("returning from handleOnboardingInfo");
		} catch (SQLException e) {
			LOGGER.error("error while executing handleOnboardingInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return onboardingInfo.getUID();
	}

	@Override
	public OnboardingInfo getOnboardingInfoByUID(String uid) throws ServiceExecutionException {
		LOGGER.debug("entered into getOnboardingInfoByUID");
		OnboardingInfo onboardingInfo = new OnboardingInfo();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_ONBOARDING_INFO_BY_UID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					onboardingInfo.setUID(rs.getString("UID"));
					onboardingInfo.setTitle(rs.getString("TITLE"));
					onboardingInfo.setData(rs.getString("ONBOARDING_DATA"));
					onboardingInfo.setUserID(rs.getInt("USER_ID"));
					onboardingInfo.setClinicID(rs.getInt("STUDY_ID"));
					onboardingInfo.setIsArchived(rs.getBoolean("IS_ARCHIVED"));
					onboardingInfo.setIsFree(rs.getBoolean("IS_FREE"));
					onboardingInfo.setActive(rs.getBoolean("IS_ACTIVE"));
					onboardingInfo.setCreateDate(rs.getString("CREATED_DATE"));
					onboardingInfo.setUpdateDate(rs.getString("MODIFIED_DATE"));
				}
			}, uid);
		} catch (Exception e) {
			LOGGER.error("error while fetching getOnboardingInfoByUID", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getOnboardingInfoByUID");
		return onboardingInfo;
	}

	@Override
	public List<MonitoringPlan> getMonitoringPlanList(int clinicId) throws ServiceExecutionException {
		LOGGER.debug("entered into getMonitoringPlanList");
		List<MonitoringPlan> monitoringPlanList = new ArrayList<MonitoringPlan>();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_STUDY_PLANS_LIST_BY_STUDY_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					MonitoringPlan monitoringPlan = new MonitoringPlan();
					monitoringPlan.setPlanID(rs.getInt("PLAN_ID"));
					monitoringPlan.setPlanDesc(rs.getString("PLAN_NAME"));
					monitoringPlan.setTypeID(rs.getInt("STUDY_ID"));
					monitoringPlan.setSubscriptionID(rs.getString("STUDY_NAME"));
					monitoringPlanList.add(monitoringPlan);
				}
			}, clinicId);
		} catch (Exception e) {
			LOGGER.error("error while fetching getMonitoringPlanList", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getMonitoringPlanList");
		return monitoringPlanList;
	}

	@Override
	public PetInfoDTO getPetInfoByID(int petId) throws ServiceExecutionException {
		LOGGER.debug("entered into getPetInfoByID");
		PetInfoDTO petInfoDTO = new PetInfoDTO();
		try {
			jdbcTemplate.query(MOBILE_APP_GET_PET_INFO_BY_PET_ID, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					Date sqlDate = null;
					java.util.Date utilDate = new java.util.Date();
					petInfoDTO.setBirthDay(rs.getString("BIRTHDAY"));
					petInfoDTO.setBreedId(rs.getInt("BREED_ID"));
					petInfoDTO.setBreedName(rs.getString("BREED_NAME"));
					petInfoDTO.setCheckedName(rs.getString("CHECKED_NAME"));

					sqlDate = rs.getDate("CHECKED_TIME");
					utilDate = new java.util.Date();
					utilDate.setTime(sqlDate.getTime());
					petInfoDTO.setCheckedTime(utilDate);

					sqlDate = rs.getDate("CREATED_DATE");
					utilDate = new java.util.Date();
					utilDate.setTime(sqlDate.getTime());
					petInfoDTO.setCreatedDate(utilDate);

					petInfoDTO.setGender(rs.getString("GENDER"));
					petInfoDTO.setIsActive(rs.getInt("IS_ACTIVE"));
					petInfoDTO.setIsDeceased(rs.getInt("IS_DECEASED"));
					petInfoDTO.setIsMixed(rs.getBoolean("IS_MIXED"));
					petInfoDTO.setNeutered(rs.getBoolean("IS_NEUTERED"));
					petInfoDTO.setUnknown(rs.getBoolean("IS_UNKNOWN"));
					petInfoDTO.setMixBreed(rs.getString("MIX_BREED"));

					sqlDate = rs.getDate("MODIFIED_DATE");
					utilDate = new java.util.Date();
					utilDate.setTime(sqlDate.getTime());
					petInfoDTO.setModifiedDate(utilDate);

					sqlDate = rs.getDate("NEXT_CHECKED_TIME");
					utilDate = new java.util.Date();
					utilDate.setTime(sqlDate.getTime());
					petInfoDTO.setNextCheckedTime(utilDate);

					petInfoDTO.setPetId(rs.getInt("PET_ID"));
					petInfoDTO.setPetName(rs.getString("PET_NAME"));
					petInfoDTO.setPetParentFullName(rs.getString("PET_PARENT_FULL_NAME"));
					petInfoDTO.setPetParentId(rs.getInt("PET_PARENT_ID"));
					petInfoDTO.setPhotoName(rs.getString("PHOTO_NAME"));

					sqlDate = rs.getDate("PLAN_START_DATE");
					utilDate = new java.util.Date();
					utilDate.setTime(sqlDate.getTime());
					petInfoDTO.setPlanStartDate(utilDate);

					sqlDate = rs.getDate("PLAN_END_DATE");
					utilDate = new java.util.Date();
					utilDate.setTime(sqlDate.getTime());
					petInfoDTO.setPlanEndDate(utilDate);

					petInfoDTO.setPlanId(rs.getInt("PLAN_ID"));
					petInfoDTO.setPlanStatus(rs.getInt("PLAN_STATUS"));
					petInfoDTO.setStudyId(rs.getInt("STUDY_ID"));
				}
			}, petId);

		} catch (Exception e) {
			LOGGER.error("error while fetching getPetInfoByID", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		LOGGER.debug("returning from getPetInfoByID");
		return petInfoDTO;
	}

	@Override
	public int updatePetInfo(PetInfoDTO petInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into updatePetInfo");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", petInfo.getPetId());
			inputParams.put("p_pet_name", petInfo.getPetName());
			inputParams.put("p_gender", petInfo.getGender());
			inputParams.put("p_breed_id", petInfo.getBreedId());
			inputParams.put("p_is_mixed", petInfo.getIsMixed());
			inputParams.put("p_mixed_breed", petInfo.getMixBreed());
			inputParams.put("p_birthday", petInfo.getBirthDay());
			inputParams.put("p_is_unknown", petInfo.isUnknown());
			inputParams.put("p_weight", petInfo.getWeight());
			inputParams.put("p_bfi", petInfo.getPetBFI());
			inputParams.put("p_is_neutered", petInfo.isNeutered());
			inputParams.put("p_photo_name", petInfo.getPhotoName());
			inputParams.put("p_plan_id", petInfo.getPlanId());
			inputParams.put("p_ideal_bfi", petInfo.getIdealBFI());
			inputParams.put("p_ideal_weight", petInfo.getIdealWeight());
			inputParams.put("p_is_deceased", petInfo.getIsDeceased());
			inputParams.put("p_login_user_id", petInfo.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_PET_INFO, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			int uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from updatePetInfo");
			return uniqueId;
		} catch (SQLException e) {
			LOGGER.error("error while executing updatePetInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@Override
	public int insertPetInfo(PetInfoDTO petInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into insertPetInfo");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_id", petInfo.getPetParentId());
			inputParams.put("p_pet_name", petInfo.getPetName());
			inputParams.put("p_gender", petInfo.getGender());
			inputParams.put("p_breed_id", petInfo.getBreedId());
			inputParams.put("p_is_mixed", petInfo.getIsMixed());
			inputParams.put("p_mixed_breed", petInfo.getMixBreed());
			inputParams.put("p_birthday", petInfo.getBirthDay());
			inputParams.put("p_is_unknown", petInfo.isUnknown());
			inputParams.put("p_weight", petInfo.getWeight());
			inputParams.put("p_bfi", petInfo.getPetBFI());
			inputParams.put("p_is_neutered", petInfo.isNeutered());
			inputParams.put("p_weight_unit", petInfo.getWeightUnit());
			inputParams.put("p_algorithm_version", petInfo.getAlgorithmVersion());
			inputParams.put("p_login_user_id", petInfo.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_PET_INFO, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			int uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from insertPetInfo");
			return uniqueId;
		} catch (SQLException e) {
			LOGGER.error("error while executing insertPetInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public int handlePetCheckedInfo(PetCheckedInfo petCheckedInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into handlePetCheckedInfo");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_id", petCheckedInfo.getPetID());
			inputParams.put("p_checked_time", petCheckedInfo.getCheckedTime());
			inputParams.put("p_next_checked", petCheckedInfo.getNextCheckedTime());
			inputParams.put("p_checked_by", petCheckedInfo.getCheckedBy());
			inputParams.put("p_login_user_id", petCheckedInfo.getCheckedBy());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_PET_CHECKED_INFO, inputParams);
			LOGGER.info("outParams are {}", outParams);
			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();
				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(instruction -> {
						if (instruction.containsKey("LAST_INSERT_ID()")) {
							petCheckedInfo
									.setPetCheckInfoID(((BigInteger) instruction.get("LAST_INSERT_ID()")).intValue());
						}
					});
				}
			}
			LOGGER.debug("returning from handlePetCheckedInfo");
		} catch (SQLException e) {
			LOGGER.error("error while executing handlePetCheckedInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return petCheckedInfo.getPetCheckInfoID();
	}

	@Override
	public DeviceInfo getDeviceInfoByDeviceNumber(String deviceNumber) throws ServiceExecutionException {
		DeviceInfo deviceInfo = new DeviceInfo();
		LOGGER.debug("getDeviceInfoByDeviceNumber called");
		try {
			jdbcTemplate.query(MOBLIE_APP_GET_DEVICE_INFO_BY_DEVICE_NUMBER, new RowCallbackHandler() {
				@Override
				public void processRow(ResultSet rs) throws SQLException {
					deviceInfo.setDeviceID(rs.getInt("DEVICE_ID"));
					deviceInfo.setDeviceNumber(rs.getString("DEVICE_NUMBER"));
					deviceInfo.setDeviceType(rs.getString("DEVICE_TYPE"));
					deviceInfo.setAddDate(rs.getString("ADD_DATE"));
					deviceInfo.setActive(rs.getBoolean("IS_ACTIVE"));
				}
			}, deviceNumber);
		} catch (Exception e) {
			LOGGER.error("error while fetching getDeviceInfoByDeviceNumber", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceInfo;

	}

	@Override
	public int insertDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into insertDeviceInfo");
		int uniqueId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_device_number", deviceInfo.getDeviceNumber());
			inputParams.put("p_device_type", deviceInfo.getDeviceType());
			inputParams.put("p_add_date", deviceInfo.getAddDate());
			inputParams.put("p_login_user_id", deviceInfo.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_DEVICE_INFO, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from insertDeviceInfo");
		} catch (SQLException e) {
			LOGGER.error("error while executing insertDeviceInfo ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return uniqueId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertDeviceAssign(DeviceAssignDTO deviceAssign) throws ServiceExecutionException {
		LOGGER.debug("entered into insertDeviceAssign");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_plan_id", deviceAssign.getPlanID());
			inputParams.put("p_device_id", deviceAssign.getDeviceID());
			inputParams.put("p_note", deviceAssign.getNote());
			inputParams.put("p_login_user_id", deviceAssign.getUserId());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_PET_STUDY_DEVICE, inputParams);
			LOGGER.info("outParams are {}", outParams);
			Iterator<Entry<String, Object>> itr = outParams.entrySet().iterator();

			while (itr.hasNext()) {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) itr.next();
				String key = entry.getKey();
				if (key.equals(RESULT_SET_1)) {
					List<Map<String, Object>> list = (List<Map<String, Object>>) entry.getValue();
					list.forEach(instruction -> {
						if (instruction.containsKey("LAST_INSERT_ID()")) {
							deviceAssign.setID(((BigInteger) instruction.get("LAST_INSERT_ID()")).intValue());
						}
					});
				}
			}
			LOGGER.debug("returning from insertDeviceAssign");
		} catch (SQLException e) {
			LOGGER.error("error while executing insertDeviceAssign ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return deviceAssign.getID();
	}

	@Override
	public void updateOnboardingArchived(String UID, int userId) throws ServiceExecutionException {
		LOGGER.debug("entered into updateOnboardingArchived");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_uid", UID);
			inputParams.put("p_login_user_id", userId);
			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_ONBOARDING_ARCHIVED, inputParams);
			LOGGER.info("outParams are {}", outParams);
		} catch (SQLException e) {
			LOGGER.error("error while executing updateSensorSetupStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public void updateOnboardingStatus(String UID, String sbStatus, int userId) throws ServiceExecutionException {
		LOGGER.debug("entered into updateOnboardingArchived");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_uid", UID);
			inputParams.put("p_onboarding_status", sbStatus);
			inputParams.put("p_login_user_id", userId);
			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_UPDATE_ONBOARDING_STATUS, inputParams);
			LOGGER.info("outParams are {}", outParams);
		} catch (SQLException e) {
			LOGGER.error("error while executing updateSensorSetupStatus ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
	}

	@Override
	public int insertMonitoringPlan(MonitoringPlan monitoringPlan) throws ServiceExecutionException {
		LOGGER.debug("entered into insertMonitoringPlan");
		int uniqueId = 0;
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_plan_id", monitoringPlan.getTypeID());
			inputParams.put("p_pet_id", monitoringPlan.getPetID());
			inputParams.put("p_pet_parent_id", monitoringPlan.getClientID());
			inputParams.put("p_study_id", monitoringPlan.getClinicID());
			if (monitoringPlan.getStartDate() != null) {
				inputParams.put("p_start_date", monitoringPlan.getStartDate());
			} else {
				inputParams.put("p_start_date", null);
			}

			if (monitoringPlan.getEndDate() != null) {
				inputParams.put("p_end_date", monitoringPlan.getEndDate());
			} else {
				inputParams.put("p_end_date", null);
			}
			inputParams.put("p_user_id", monitoringPlan.getUserID());
			inputParams.put("p_login_user_id", monitoringPlan.getUserID());

			LOGGER.info("inputParams are {}", inputParams);
			Map<String, Object> outParams = callStoredProcedure(MOBILE_APP_INSERT_MONITORING_PLAN, inputParams);
			LOGGER.info("outParams are {}", outParams);
			// System.out.println(outParams);
			uniqueId = (int) outParams.get("last_insert_id");
			LOGGER.debug("returning from insertMonitoringPlan");
		} catch (SQLException e) {
			LOGGER.error("error while executing insertMonitoringPlan ", e);
			throw new ServiceExecutionException(e.getMessage());
		}
		return uniqueId;
	}

	@Override
	public void logoutUser(String petParentId, String token) throws ServiceExecutionException {
		LOGGER.debug("entered into logoutUser");
		try {
			Map<String, Object> inputParams = new HashMap<>();
			inputParams.put("p_pet_parent_key", token);
			inputParams.put("p_pet_parent_id", petParentId);
			LOGGER.info("inputParams are {}", inputParams);
			callStoredProcedure(MOBILE_APP_PET_PARENT_LOG_OUT, inputParams);
		} catch (SQLException e) {
			LOGGER.error("error while executing logoutUser ", e);
			throw new ServiceExecutionException(e.getMessage());
		}

	}

}
