package com.hillspet.wearables.service.user.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.common.utils.Cryptography;
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
import com.hillspet.wearables.service.user.MigratedService;

@Service
public class MigratedServiceImpl implements MigratedService {

	private static final Logger LOGGER = LogManager.getLogger(MigratedServiceImpl.class);

	@Autowired
	private MigratedDao migratedDao;

	@Override
	public ClientInfo getClientInfoByEmail(String email) throws ServiceExecutionException {
		LOGGER.info("getClientInfoByEmail called");
		ClientInfo clientInfo = migratedDao.getClientInfoByEmail(email);
		LOGGER.info("getClientInfoByEmail end");
		return clientInfo;
	}

	@Override
	public ClientInfo clientLogin(String email, String password) throws ServiceExecutionException {
		LOGGER.info("entered into clientLogin");
		return migratedDao.clientLogin(email, password);
	}

	@Override
	public int insertClientKey(PetParentKeyInfoDTO parentKeyInfoDTO) throws ServiceExecutionException {
		LOGGER.debug("entered into insertClientKey");
		return migratedDao.insertClientKey(parentKeyInfoDTO);
	}

	@Override
	public boolean updateClientInfo(ClientInfo clientInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into updateClientInfo");
		return migratedDao.updateClientInfo(clientInfo);
	}

	@Override
	public ClientSMSCode getClientSMSCodeByClientID(int clientId) throws ServiceExecutionException {
		LOGGER.debug("entered into getClientSMSCodeByClientID");
		return migratedDao.getClientSMSCodeByClientID(clientId);
	}

	@Override
	public int updateClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException {
		LOGGER.debug("entered into updateClientSMSCode");
		return migratedDao.updateClientSMSCode(clientSMSCode);
	}

	@Override
	public int insertClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException {
		LOGGER.debug("entered into insertClientSMSCode");
		return migratedDao.insertClientSMSCode(clientSMSCode);
	}

	@Override
	public ClientSMSCode getClientSMSCodeByClientIDAndVerificationCode(int clientID, String verificationCode)
			throws ServiceExecutionException {
		LOGGER.debug("entered into getClientSMSCodeByClientIDAndVerificationCode");
		return migratedDao.getClientSMSCodeByClientIDAndVerificationCode(clientID, verificationCode);
	}

	@Override
	public int updatePassword(ClientInfo clientInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into updatePassword");
		return migratedDao.updatePassword(clientInfo);
	}

	@Override
	public int expiredClientSMSCode(int petParentId, String verificationCode) throws ServiceExecutionException {
		LOGGER.debug("entered into expiredClientSMSCode");
		int expiredResult = 0;
		ClientSMSCode clientSMSCode = this.getClientSMSCodeByClientIDAndVerificationCode(petParentId, verificationCode);
		if (clientSMSCode.getPetParentSMSCodeId() > 0) {
			clientSMSCode.setExpired(true);
			expiredResult = this.updateClientSMSCode(clientSMSCode);
		}
		return expiredResult;
	}

	@Override
	public List<PetInfoDTO> getPetListByPetParentId(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("entered into getPetListByPetParentId");
		return migratedDao.getPetListByPetParentId(petParentId);
	}

	@Override
	public List<DeviceAssignDTO> getDeviceAssignListByPlanIDAndPetID(int planId, int petId)
			throws ServiceExecutionException {
		LOGGER.debug("entered into getDeviceAssignListByPlanIDAndPetID");
		return migratedDao.getDeviceAssignListByPlanIDAndPetID(planId, petId);
	}

	@Override
	public int getPetParentByPetParentKey(String petParentKey) throws ServiceExecutionException {
		LOGGER.debug("getPetParentByPetParentKey called");
		int petParentKeyId = migratedDao.getPetParentByPetParentKey(petParentKey);
		LOGGER.debug("getPetParentByPetParentKey end");
		return petParentKeyId;
	}

	@Override
	public ClientInfo getClientInfoById(String clientId) throws ServiceExecutionException {
		LOGGER.debug("getClientInfoByID called");
		ClientInfo clientInfo = migratedDao.getClientInfoById(clientId);
		LOGGER.debug("getClientInfoById end");
		return clientInfo;
	}

	@Override
	public String getPasswordByClientID(int clientId) throws ServiceExecutionException {
		LOGGER.debug("entered into getPasswordByClientID");
		return migratedDao.getPasswordByClientID(clientId);
	}

	@Override
	public List<TimerLog> getPetTimerLog(int petParentId) throws ServiceExecutionException {
		LOGGER.debug("entered into getPetTimerLog");
		return migratedDao.getPetTimerLog(petParentId);
	}

	@Override
	public boolean managePetTimerLog(TimerLog timerLog) throws ServiceExecutionException {
		LOGGER.debug("entered into managePetTimerLog");
		return migratedDao.managePetTimerLog(timerLog);
	}

	@Override
	public boolean manageMobileAppScreensFeedback(MobileAppFeedbackDTO appFeedbackDTO)
			throws ServiceExecutionException {
		LOGGER.debug("entered into manageMobileAppScreensFeedback");
		boolean sensorUpdateStatus = migratedDao.manageMobileAppScreensFeedback(appFeedbackDTO);
		LOGGER.debug("returning from manageMobileAppScreensFeedback");
		return sensorUpdateStatus;
	}
	
	@Override
	public boolean updateSensorSetupStatus(SensorDetailsDTO sensorDetailsDTO) throws ServiceExecutionException {
		LOGGER.debug("updateSensorSetupStatus called");
		boolean sensorUpdateStatus = migratedDao.updateSensorSetupStatus(sensorDetailsDTO);
		LOGGER.debug("updateSensorSetupStatus end");
		return sensorUpdateStatus;
	}
	
	@Override
	public SensorDetailsDTO getSensorSetupStatus(String petParentId, String petId) throws ServiceExecutionException {
		LOGGER.debug("getSensorSetupStatus called");
		SensorDetailsDTO sensorDetailsDTO = migratedDao.getSensorSetupStatus(petParentId, petId);
		LOGGER.debug("getSensorSetupStatus end");
		return sensorDetailsDTO;
	}
	
	@Override
	public boolean manageSensorChargingNotificationSettings(SensorDetailsDTO sensorDetailsDTO)
			throws ServiceExecutionException {
		LOGGER.debug("entered into manageSensorChargingNotificationSettings");
		boolean sensorUpdateStatus = migratedDao.manageSensorChargingNotificationSettings(sensorDetailsDTO);
		LOGGER.debug("returning from manageSensorChargingNotificationSettings");
		return sensorUpdateStatus;
	}
	
	@Override
	public DeviceAssignDTO getDeviceAssignInfoByDeviceNumber(String deviceNumber, String clientId)
			throws ServiceExecutionException {
		LOGGER.debug("entered into getDeviceAssignInfoByDeviceNumber");
		return migratedDao.getDeviceAssignInfoByDeviceNumber(deviceNumber, clientId);
	}
	
	@Override
	public int insertClientInfo(ClientInfo clientInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into insertClientInfo");
		clientInfo.setUniqueId(Cryptography.genPasswordSalt());
		return migratedDao.insertClientInfo(clientInfo);
	}
	
	@Override
	public String handleOnboardingInfo(OnboardingInfo onboardingInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into handleOnboardingInfo");
		if (onboardingInfo.getUID() == null || onboardingInfo.getUID().trim().length() == 0) {
			String uuid = migratedDao.handleOnboardingInfo(onboardingInfo);
			onboardingInfo.setUID(uuid);
		} else {
			migratedDao.handleOnboardingInfo(onboardingInfo);
		}
		return onboardingInfo.getUID();
	}
	
	@Override
	public OnboardingInfo getOnboardingInfoByUID(String uid) throws ServiceExecutionException {
		LOGGER.debug("entered into getOnboardingInfoByUID");
		return migratedDao.getOnboardingInfoByUID(uid);
	}
	
	@Override
	public List<MonitoringPlan> getMonitoringPlanList(int clinicId) throws ServiceExecutionException {
		LOGGER.debug("entered into getMonitoringPlanList");
		return migratedDao.getMonitoringPlanList(clinicId);
	}
	
	@Override
	public PetInfoDTO getPetInfoByID(int petId) throws ServiceExecutionException {
		LOGGER.debug("entered into getPetInfoByID");
		return migratedDao.getPetInfoByID(petId);
	}
	
	@Override
	public int updatePetInfo(PetInfoDTO petInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into updatePetInfo");
		return migratedDao.updatePetInfo(petInfo);
	}
	
	@Override
	public int insertPetInfo(PetInfoDTO petInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into insertPetInfo");
		return migratedDao.insertPetInfo(petInfo);
	}
	
	@Override
	public int handlePetCheckedInfo(PetCheckedInfo petCheckedInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into handlePetCheckedInfo");
		return migratedDao.handlePetCheckedInfo(petCheckedInfo);
	}
	
	@Override
	public DeviceInfo getDeviceInfoByDeviceNumber(String deviceNumber) throws ServiceExecutionException {
		LOGGER.debug("entered into getDeviceInfoByDeviceNumber");
		return migratedDao.getDeviceInfoByDeviceNumber(deviceNumber);
	}
	
	@Override
	public int insertDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException {
		LOGGER.debug("entered into insertDeviceInfo");
		return migratedDao.insertDeviceInfo(deviceInfo);
	}
	
	@Override
	public int insertDeviceAssign(DeviceAssignDTO deviceAssign) throws ServiceExecutionException {
		LOGGER.debug("entered into insertDeviceAssign");
		return migratedDao.insertDeviceAssign(deviceAssign);
	}
	
	@Override
	public void updateOnboardingArchived(String UID, int userId) throws ServiceExecutionException {
		LOGGER.debug("entered into updateOnboardingArchived");
		migratedDao.updateOnboardingArchived(UID, userId);
	}
	
	@Override
	public void updateOnboardingStatus(String UID, String sbStatus, int userId) throws ServiceExecutionException {
		LOGGER.debug("entered into updateOnboardingStatus");
		migratedDao.updateOnboardingStatus(UID, sbStatus, userId);
	}

	@Override
	public int insertMonitoringPlan(MonitoringPlan monitoringPlan) throws ServiceExecutionException {
		LOGGER.debug("entered into insertMonitoringPlan");
		return migratedDao.insertMonitoringPlan(monitoringPlan);
	}

	@Override
	public void logoutUser(String petParentId, String token) throws ServiceExecutionException {
		LOGGER.debug("entered into logoutUser");
		migratedDao.logoutUser(petParentId, token);
		
	}

}
