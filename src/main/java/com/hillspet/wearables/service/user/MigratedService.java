package com.hillspet.wearables.service.user;

import java.util.List;

import com.hillspet.wearables.common.exceptions.ServiceExecutionException;
import com.hillspet.wearables.dto.ClientInfo;
import com.hillspet.wearables.dto.ClientSMSCode;
import com.hillspet.wearables.dto.DeviceAssignDTO;
import com.hillspet.wearables.dto.MobileAppFeedbackDTO;
import com.hillspet.wearables.dto.MonitoringPlan;
import com.hillspet.wearables.dto.OnboardingInfo;
import com.hillspet.wearables.dto.PetCheckedInfo;
import com.hillspet.wearables.dto.PetInfoDTO;
import com.hillspet.wearables.dto.PetParentKeyInfoDTO;
import com.hillspet.wearables.dto.SensorDetailsDTO;
import com.hillspet.wearables.dto.TimerLog;
import com.hillspet.wearables.dto.DeviceInfo;

public interface MigratedService {

	public ClientInfo getClientInfoByEmail(String email) throws ServiceExecutionException;

	public ClientInfo clientLogin(String email, String password) throws ServiceExecutionException;

	public int insertClientKey(PetParentKeyInfoDTO parentKeyInfoDTO) throws ServiceExecutionException;

	public boolean updateClientInfo(ClientInfo clientInfo) throws ServiceExecutionException;

	public ClientSMSCode getClientSMSCodeByClientID(int clientID) throws ServiceExecutionException;

	public int updateClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException;

	public int insertClientSMSCode(ClientSMSCode clientSMSCode) throws ServiceExecutionException;

	public ClientSMSCode getClientSMSCodeByClientIDAndVerificationCode(int clientId, String verificationCode)
			throws ServiceExecutionException;

	public int updatePassword(ClientInfo clientInfoResult) throws ServiceExecutionException;

	public int expiredClientSMSCode(int clientId, String verificationCode) throws ServiceExecutionException;

	public List<PetInfoDTO> getPetListByPetParentId(int clientId) throws ServiceExecutionException;

	public List<DeviceAssignDTO> getDeviceAssignListByPlanIDAndPetID(int planId, int petId)
			throws ServiceExecutionException;

	public int getPetParentByPetParentKey(String token) throws ServiceExecutionException;

	public ClientInfo getClientInfoById(String string) throws ServiceExecutionException;

	public String getPasswordByClientID(int clientId) throws ServiceExecutionException;

	public List<TimerLog> getPetTimerLog(int parseInt) throws ServiceExecutionException;

	public boolean managePetTimerLog(TimerLog timerLog) throws ServiceExecutionException;

	public boolean manageMobileAppScreensFeedback(MobileAppFeedbackDTO mobileAppFeedbackDTO)
			throws ServiceExecutionException;

	public boolean updateSensorSetupStatus(SensorDetailsDTO sensorDetailsDTO) throws ServiceExecutionException;

	public SensorDetailsDTO getSensorSetupStatus(String petParentId, String petId) throws ServiceExecutionException;

	public boolean manageSensorChargingNotificationSettings(SensorDetailsDTO sensorDetailsDTO)
			throws ServiceExecutionException;

	public DeviceAssignDTO getDeviceAssignInfoByDeviceNumber(String sensorNumber, String clientId)
			throws ServiceExecutionException;

	public int insertClientInfo(ClientInfo newClientInfo) throws ServiceExecutionException;

	public String handleOnboardingInfo(OnboardingInfo onboardingInfo) throws ServiceExecutionException;

	public OnboardingInfo getOnboardingInfoByUID(String onboardingInfoGuid) throws ServiceExecutionException;

	public List<MonitoringPlan> getMonitoringPlanList(int clinicID) throws ServiceExecutionException;

	public PetInfoDTO getPetInfoByID(int petID) throws ServiceExecutionException;

	public int updatePetInfo(PetInfoDTO petInfo) throws ServiceExecutionException;

	public int insertPetInfo(PetInfoDTO petInfo) throws ServiceExecutionException;

	public int insertMonitoringPlan(MonitoringPlan monitoringPlan) throws ServiceExecutionException;

	public int handlePetCheckedInfo(PetCheckedInfo petCheckedInfo) throws ServiceExecutionException;

	public DeviceInfo getDeviceInfoByDeviceNumber(String deviceNumber) throws ServiceExecutionException;

	public int insertDeviceInfo(DeviceInfo deviceInfo) throws ServiceExecutionException;

	public int insertDeviceAssign(DeviceAssignDTO deviceAssign) throws ServiceExecutionException;

	public void updateOnboardingArchived(String uID, int userID) throws ServiceExecutionException;

	public void updateOnboardingStatus(String uID, String string, int userID) throws ServiceExecutionException;

	public void logoutUser(String petParentId, String token) throws ServiceExecutionException;

}
