package com.hillspet.wearables.dao.user;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
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

public interface MigratedDao {

	public ClientInfo getClientInfoByEmail(String email) throws ServiceExecutionException;

	public ClientInfo clientLogin(String email, String password) throws ServiceExecutionException;

	public int insertClientKey(PetParentKeyInfoDTO parentKeyInfoDTO) throws ServiceExecutionException;

	public boolean updateClientInfo(ClientInfo clientInfo) throws ServiceExecutionException;

	public ClientSMSCode getClientSMSCodeByClientID(int clientId) throws ServiceExecutionException;

	public int updateClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException;

	public int insertClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException;

	public ClientSMSCode getClientSMSCodeByClientIDAndVerificationCode(int clientID, String verificationCode)
			throws ServiceExecutionException;

	public int updatePassword(ClientInfo clientInfo) throws ServiceExecutionException;

	public List<PetInfoDTO> getPetListByPetParentId(int petParentId) throws ServiceExecutionException;

	public List<DeviceAssignDTO> getDeviceAssignListByPlanIDAndPetID(int planId, int petId)
			throws ServiceExecutionException;

	public int getPetParentByPetParentKey(String petParentKey) throws ServiceExecutionException;

	public ClientInfo getClientInfoById(String clientId) throws ServiceExecutionException;

	public String getPasswordByClientID(int clientId) throws ServiceExecutionException;

	public List<TimerLog> getPetTimerLog(int petParentId) throws ServiceExecutionException;

	public boolean managePetTimerLog(TimerLog timerLog) throws ServiceExecutionException;

	public boolean manageMobileAppScreensFeedback(MobileAppFeedbackDTO appFeedbackDTO) throws ServiceExecutionException;

	public boolean updateSensorSetupStatus(SensorDetailsDTO sensorDetailsDTO) throws ServiceExecutionException;

	public SensorDetailsDTO getSensorSetupStatus(String petParentId, String petId) throws ServiceExecutionException;

	public boolean manageSensorChargingNotificationSettings(SensorDetailsDTO sensorDetailsDTO)
			throws ServiceExecutionException;

	public DeviceAssignDTO getDeviceAssignInfoByDeviceNumber(String deviceNumber, String clientId)
			throws ServiceExecutionException;

	public int insertClientInfo(ClientInfo clientInfo) throws ServiceExecutionException;

	public String handleOnboardingInfo(OnboardingInfo onboardingInfo) throws ServiceExecutionException;

	public OnboardingInfo getOnboardingInfoByUID(String uid) throws ServiceExecutionException;

	public List<MonitoringPlan> getMonitoringPlanList(int clinicId) throws ServiceExecutionException;

	public PetInfoDTO getPetInfoByID(int petId) throws ServiceExecutionException;

	public int updatePetInfo(PetInfoDTO petInfo) throws ServiceExecutionException;

	public int insertPetInfo(PetInfoDTO petInfo) throws ServiceExecutionException;

	public int handlePetCheckedInfo(PetCheckedInfo petCheckedInfo) throws ServiceExecutionException;

	public DeviceInfo getDeviceInfoByDeviceNumber(String deviceNumber) throws ServiceExecutionException;

	public int insertDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException;

	public int insertDeviceAssign(DeviceAssignDTO deviceAssign) throws ServiceExecutionException;

	public void updateOnboardingArchived(String uID, int userId) throws ServiceExecutionException;

	public void updateOnboardingStatus(String uID, String sbStatus, int userId) throws ServiceExecutionException;

	public int insertMonitoringPlan(MonitoringPlan monitoringPlan) throws ServiceExecutionException;

	public void logoutUser(String petParentId, String token) throws ServiceExecutionException;

}
