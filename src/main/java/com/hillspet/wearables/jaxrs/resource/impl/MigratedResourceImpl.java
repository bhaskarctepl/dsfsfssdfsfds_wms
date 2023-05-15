package com.hillspet.wearables.jaxrs.resource.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ServiceValidationException;
import com.hillspet.wearables.common.response.SuccessResponse;
import com.hillspet.wearables.common.utils.Cryptography;
import com.hillspet.wearables.concurrent.EmailSenderThread;
import com.hillspet.wearables.concurrent.EmailThreadPoolExecutor;
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
import com.hillspet.wearables.email.templates.EmailTemplate;
import com.hillspet.wearables.format.validation.FormatValidationService;
import com.hillspet.wearables.jaxrs.resource.MigratedResource;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.service.user.MigratedService;

@Service
public class MigratedResourceImpl implements MigratedResource {

	private static final Logger LOGGER = LogManager.getLogger(MigratedResourceImpl.class);

	@Autowired
	private MigratedService migratedService;

	@Autowired
	FormatValidationService formatValidationService;

	private static final String dateFormat = "yyyy-MM-dd hh:mm:ss";

	@Override
	public Response clientLogin(String payload) {
		LOGGER.debug("entered into clientLogin");
		JSONObject jsonObject = null;
		JSONObject resultJson = null;
		try {
			jsonObject = new JSONObject(payload);
			resultJson = new JSONObject();
			String email = jsonObject.getString("Email");
			String password = jsonObject.getString("Password");
			String fcmToken = jsonObject.getString("FCMToken");

			if (email == null || email.trim().length() == 0) {
				throw new ServiceValidationException("Email is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.EMAIL_REQUIRED)));
			}

			if (password == null || password.trim().length() == 0) {
				throw new ServiceValidationException("Password is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PASSWORD_REQUIRED)));
			}

			ClientInfo clientInfo = migratedService.getClientInfoByEmail(email);
			if (clientInfo.getClientId() > 0) {
				password = Cryptography.encrypt(Cryptography.petParentDecrypt(password), clientInfo.getUniqueId());
			}
			System.out.println("password " + password);
			clientInfo = migratedService.clientLogin(email, password);
			clientInfo.setPassword(password);
			System.out.println("clientInfo.getClientId() " + clientInfo.getClientId());
			JSONObject object = null;

			if (clientInfo != null && clientInfo.getClientId() > 0) {
				object = new JSONObject();
				String key = UUID.randomUUID().toString();
				Calendar startTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				String addDate = startTime.toString();
				PetParentKeyInfoDTO parentKeyInfoDTO = new PetParentKeyInfoDTO();
				parentKeyInfoDTO.setAddDate(addDate);
				parentKeyInfoDTO.setIsExpired(0);
				parentKeyInfoDTO.setKey(key);
				parentKeyInfoDTO.setUserId(String.valueOf(clientInfo.getClientId()));
				parentKeyInfoDTO.setPetParentId(clientInfo.getClientId());
				migratedService.insertClientKey(parentKeyInfoDTO);

				clientInfo.setLastLogin(addDate);
				if (StringUtils.isNoneEmpty(fcmToken)) {
					clientInfo.setFcmToken(fcmToken);
				}
				migratedService.updateClientInfo(clientInfo);
				object.put("clientID", clientInfo.getClientId());
				object.put("email", clientInfo.getEmail());
				object.put("token", key);

				resultJson.put("responseCode", 0);
				resultJson.put("responseMessage", "SUCCESS");
				resultJson.put("success", true);
				resultJson.put("result", object);
			} else {
				resultJson.put("responseCode", 1);
				resultJson.put("responseMessage", "ERROR");
				resultJson.put("success", false);
				resultJson.put("result", "null");
			}

			resultJson.put("errors", "null");
			resultJson.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(resultJson.toString()).build();
	}

	@Override
	public Response sendEmailVerificationCode(String payload) {
		LOGGER.debug("entered into SendEmailVerificationCode");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {

			request = new JSONObject(payload);
			String email = request.getString("Email");

			if (email == null || email.trim().length() == 0) {
				throw new ServiceValidationException("Email informaton is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.EMAIL_REQUIRED)));
			}

			ClientInfo clientInfo = migratedService.getClientInfoByEmail(email);
			String sid = sendClientVerificationCode(clientInfo, "Email", true);
			boolean status = false;
			if (sid != null && sid.trim().length() > 0) {
				status = true;
			}
			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONObject().put("Key", status).put("Value", ""));
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response checkClientSMSCode(String payload) {
		LOGGER.debug("entered into checkClientSMSCode");
		JSONObject jsonObject = null;
		JSONObject resultJson = null;
		try {
			jsonObject = new JSONObject(payload);
			resultJson = new JSONObject();
			String email = jsonObject.getString("Email");
			String verificationCode = jsonObject.getString("VerificationCode");

			if (email == null || email.trim().length() == 0) {
				throw new ServiceValidationException("Email is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.EMAIL_REQUIRED)));
			}

			if (verificationCode == null || verificationCode.trim().length() == 0) {
				throw new ServiceValidationException("Verification Code is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.VERIFICATION_CODE_REQUIRED)));
			}

			ClientInfo clientInfo = migratedService.getClientInfoByEmail(email);
			System.out.println("clientInfo.getClientId() " + clientInfo.getClientId());
			ClientSMSCode codeDTO = migratedService
					.getClientSMSCodeByClientIDAndVerificationCode(clientInfo.getClientId(), verificationCode);

			boolean result = false;
			if (codeDTO.getPetParentSMSCodeId() > 0) {
				result = true;
			}

			resultJson.put("errors", "null");
			resultJson.put("responseCode", 0);
			resultJson.put("responseMessage", "SUCCESS");
			resultJson.put("result", new JSONObject().put("Key", result).put("Value", ""));
			resultJson.put("success", true);
			resultJson.put("warnings", "null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(resultJson.toString()).build();
	}

	@Override
	public Response setClientPasswordBySMSCode(String payload) {
		LOGGER.debug("entered into clientLogin");
		JSONObject request = null;
		JSONObject response = null;
		try {
			request = new JSONObject(payload);
			String email = request.getString("Email");
			String verificationCode = request.getString("VerificationCode");
			String password = request.getString("Password");

			if (email == null || email.trim().length() == 0) {
				throw new ServiceValidationException("Email is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.EMAIL_REQUIRED)));
			}

			if (verificationCode == null || verificationCode.trim().length() == 0) {
				throw new ServiceValidationException("Verification Code is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.VERIFICATION_CODE_REQUIRED)));
			}

			if (password == null || password.trim().length() == 0) {
				throw new ServiceValidationException("Password is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PASSWORD_REQUIRED)));
			}

			String deviceNumber = "";
			int petID = 0;
			String petName = "";

			ClientInfo clientInfoResult = new ClientInfo();
			clientInfoResult = migratedService.getClientInfoByEmail(email);
			ClientSMSCode clientSMSCode = migratedService
					.getClientSMSCodeByClientIDAndVerificationCode(clientInfoResult.getClientId(), verificationCode);

			if (clientSMSCode.getPetParentSMSCodeId() <= 0) {
				clientInfoResult = new ClientInfo();
			}

			if (clientInfoResult.getClientId() > 0) {
				String newPassword = "";
				newPassword = Cryptography.encrypt(Cryptography.petParentDecrypt(password),
						clientInfoResult.getUniqueId());
				clientInfoResult.setPassword(newPassword);
				int status = migratedService.updatePassword(clientInfoResult);
				if (status > 0) {
					migratedService.expiredClientSMSCode(clientInfoResult.getClientId(), verificationCode);
				}
				clientInfoResult = migratedService.clientLogin(clientInfoResult.getEmail(), newPassword);
				if (clientInfoResult.getClientId() > 0) {
					List<PetInfoDTO> petList = migratedService.getPetListByPetParentId(clientInfoResult.getClientId());
					petList.sort(Comparator.comparing(PetInfoDTO::getPlanId));
					PetInfoDTO petInfoDTO = null;
					for (PetInfoDTO petInfo : petList) {
						if (petInfo.getPlanStatus() != 0) {
							petInfoDTO = petInfo;
							break;
						}
					}
					if (petInfoDTO == null && petList.size() > 0) {
						petInfoDTO = petList.get(0);
					}
					if (petInfoDTO != null) {
						List<DeviceAssignDTO> devices = migratedService
								.getDeviceAssignListByPlanIDAndPetID(petInfoDTO.getPlanId(), petInfoDTO.getPetId());
						DeviceAssignDTO deviceAssignDTO = null;
						for (DeviceAssignDTO deviceAssignDTO1 : devices) {
							if (deviceAssignDTO1.isIsAssign()) {
								deviceAssignDTO = deviceAssignDTO1;
								break;
							}
						}
						if (deviceAssignDTO == null) {
							deviceAssignDTO = devices.get(0);
						}
						if (deviceAssignDTO != null) {
							deviceNumber = deviceAssignDTO.getDeviceNumber();
						}
						petID = petInfoDTO.getPetId();
						petName = petInfoDTO.getPetName();
					}
				}
			}
			JSONObject result = new JSONObject();
			result.put("clientID", clientInfoResult.getClientId());
			result.put("petID", petID);
			result.put("petName", petName);
			result.put("token", UUID.randomUUID().toString());
			result.put("deviceNumber", deviceNumber);

			response = new JSONObject();
			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", result);
			response.put("success", true);
			response.put("warnings", "null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response changePassword(String payload, String token) {
		LOGGER.debug("entered into clientLogin");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {

			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);
			int clientID = request.getInt("ClientID");
			String newPassword = request.getString("NewPassword");
			String oldPassword = request.getString("Password");

			if (clientID == 0) {
				throw new ServiceValidationException("Client ID not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			if (newPassword == null || newPassword.trim().length() == 0) {
				throw new ServiceValidationException("New Password is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.NEW_PASSWORD_REQUIRED)));
			}

			if (oldPassword == null || oldPassword.trim().length() == 0) {
				throw new ServiceValidationException("Password is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PASSWORD_REQUIRED)));
			}

			ClientInfo clientInfo = migratedService.getClientInfoById(Integer.toString(clientID));
			JSONObject result = new JSONObject();
			if (clientInfo.getClientId() > 0) {
				String databasePassword = Cryptography.decrypt(
						migratedService.getPasswordByClientID(clientInfo.getClientId()), 
							clientInfo.getUniqueId() == null ? Cryptography.DEFAULT_ENCRYPTION_KEY : clientInfo.getUniqueId());
				if (!databasePassword.equals(Cryptography.petParentDecrypt(oldPassword))) {
					result.put("Key", false);
					result.put("Value", "INVALID OLD PASSWORD");
				} else {
					String password = Cryptography.encrypt(Cryptography.petParentDecrypt(newPassword),
							clientInfo.getUniqueId());

					clientInfo.setPassword(password);
					int status = migratedService.updatePassword(clientInfo);
					if (status == 0) {
						result.put("Key", false);
						result.put("Value", "CHANGE FAILED");
					} else {
						result.put("Key", true);
						result.put("Value", "");
					}
				}
			}

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", result);
			response.put("success", true);
			response.put("warnings", "null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response getClientInfo(String payload, String token) {
		LOGGER.debug("CheckClientEmail called");
		int clientid = 0;
		String clientID = "";
		JSONObject jsonObject = null;
		try {
			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			jsonObject = new JSONObject(payload);
			if (jsonObject.has("ClientID")) {
				clientid = jsonObject.getInt("ClientID");
			}
			if (clientid > 0) {
				clientID = Integer.toString(clientid);
			}
			if (clientID == null || clientID.trim().length() == 0) {
				throw new ServiceValidationException("Clinet ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			ClientInfo clientInfo = migratedService.getClientInfoById(clientID);
			JSONObject json = new JSONObject();
			json.put("clientID", clientInfo.getClientId());
			json.put("email", clientInfo.getEmail());
			json.put("fullName", clientInfo.getFullName());
			json.put("phoneNumber", clientInfo.getPhoneNumber());
			json.put("firstName", clientInfo.getFirstName());
			json.put("lastName", clientInfo.getLastName());

			jsonObject = new JSONObject();
			jsonObject.put("errors", "null");
			jsonObject.put("responseCode", 0);
			jsonObject.put("responseMessage", "SUCCESS");
			jsonObject.put("result", json);
			jsonObject.put("success", true);
			jsonObject.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(jsonObject.toString()).build();
	}

	@Override
	public Response changeClientInfo(String payload, String token) {
		LOGGER.debug("entered into changeClientInfo");

		JSONObject request = null;
		JSONObject response = new JSONObject();

		try {
			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);
			int petParentId = request.getInt("ClientID");
			String firstName = request.getString("FirstName");
			String lastName = request.getString("LastName");
			String phoneNumber = request.getString("PhoneNumber");

			if (petParentId == 0) {
				throw new ServiceValidationException("Client ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			if (firstName == null || firstName.trim().length() == 0) {
				throw new ServiceValidationException("First Name is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.FIRST_NAME_REQUIRED)));
			}

			if (lastName == null || lastName.trim().length() == 0) {
				throw new ServiceValidationException("Last Name is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.LAST_NAME_REQUIRED)));
			}

			if (phoneNumber == null || phoneNumber.trim().length() == 0) {
				throw new ServiceValidationException("Phone Number is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PHONE_NUMBER_REQUIRED)));
			}

			ClientInfo clientInfo = migratedService.getClientInfoById(Integer.toString(petParentId));
			String fullName = "";

			if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
				fullName += firstName + " " + lastName;
			} else {
				if (StringUtils.isNoneEmpty(firstName)) {
					fullName = firstName;
				}
			}
			clientInfo.setFullName(fullName);
			clientInfo.setFcmToken(null);

			clientInfo.setFullName(fullName);
			clientInfo.setFirstName(firstName);
			clientInfo.setLastName(lastName);
			clientInfo.setClientId(petParentId);

			if (phoneNumber != null && phoneNumber.trim().length() > 0) {
				if (phoneNumber.contains(" ")) {
					String[] phoneCodeArray = phoneNumber.split(" ");
					String countryCode = phoneCodeArray[0];
					String phoneCode = phoneCodeArray[1];
					if (countryCode == "+1") {
						String firstPhoneCode = " (" + phoneCode.substring(0, 3) + ") ";
						String secondPhoneCode = phoneCode.substring(3, 3);
						String thirdPhoneCode = "-" + phoneCode.substring(6);
						phoneNumber = countryCode + firstPhoneCode + secondPhoneCode + thirdPhoneCode;
					}
				}
				clientInfo.setPhoneNumber(phoneNumber);
			}

			boolean result = migratedService.updateClientInfo(clientInfo);

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONObject().put("result", result));
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response manageMobileAppScreensFeedback(String payload, String token) {
		LOGGER.debug("entered into manageMobileAppScreensFeedback");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {
			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);
			int petParentId = request.getInt("ClientID");
			int petId = request.getInt("PetId");
			String pageName = request.getString("PageName");
			String deviceType = request.getString("DeviceType");
			String feedbackText = request.getString("FeedbackText");

			if (petParentId == 0) {
				throw new ServiceValidationException("Clinet ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			if (petId == 0) {
				throw new ServiceValidationException("Pet ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_ID_REQUIRED)));
			}

			if (pageName == null || pageName.trim().length() == 0) {
				throw new ServiceValidationException("Page Name is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PAGE_NAME_REQUIRED)));
			}

			if (deviceType == null || deviceType.trim().length() == 0) {
				throw new ServiceValidationException("Device Type is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_TYPE_REQUIRED)));
			}

			if (feedbackText == null || feedbackText.trim().length() == 0) {
				throw new ServiceValidationException("Feedback Text is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.FEEDBACK_TEXT_REQUIRED)));
			}

			MobileAppFeedbackDTO mobileAppFeedbackDTO = new MobileAppFeedbackDTO();
			mobileAppFeedbackDTO.setDeviceType(deviceType);
			mobileAppFeedbackDTO.setFeedbackText(feedbackText);
			mobileAppFeedbackDTO.setPageName(pageName);
			mobileAppFeedbackDTO.setPetId(Integer.toString(petId));
			mobileAppFeedbackDTO.setPetParentId(Integer.toString(petParentId));
			mobileAppFeedbackDTO.setLoginUserId(Integer.toString(petParentId));

			boolean status = migratedService.manageMobileAppScreensFeedback(mobileAppFeedbackDTO);

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", status);
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response getPetTimerLog(String payload, String token) {
		LOGGER.debug("entered into getPetTimerLog");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {

			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);

			String petParentId = request.getString("ClientID");

			if (petParentId == null || petParentId.trim().length() == 0) {
				throw new ServiceValidationException("Client ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			List<TimerLog> petTimerLogs = migratedService.getPetTimerLog(Integer.parseInt(petParentId));

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(petTimerLogs);

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONArray(json));
			response.put("success", true);
			response.put("warnings", "null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response managePetTimerLog(String payload, String token) {
		LOGGER.debug("entered into managePetTimerLog");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {

			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);

			String petParentId = request.getString("ClientID");
			String petId = request.getString("PetID");
			String category = request.getString("Category");
			String deviceNumber = request.getString("DeviceNumber");
			String duration = request.getString("Duration");
			String timerDate = request.getString("TimerDate");

			if (petParentId == null || petParentId.trim().length() == 0) {
				throw new ServiceValidationException("Client ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			if (petId == null || petId.trim().length() == 0) {
				throw new ServiceValidationException("Pet Id is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_ID_REQUIRED)));
			}

			if (category == null || category.trim().length() == 0) {
				throw new ServiceValidationException("Category is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CATEGORY_REQUIRED)));
			}

			if (deviceNumber == null || deviceNumber.trim().length() == 0) {
				throw new ServiceValidationException("Device Number is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_NUMBER_REQUIRED)));
			}

			if (duration == null || duration.trim().length() == 0) {
				throw new ServiceValidationException("Duration is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.DURATION_REQUIRED)));
			}

			if (timerDate == null || timerDate.trim().length() == 0) {
				throw new ServiceValidationException("Timer Date is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.TIMER_DATE_REQUIRED)));
			}

			TimerLog timerLog = new TimerLog();
			timerLog.setCategory(category);
			timerLog.setDeviceNumber(deviceNumber);
			timerLog.setDuration(duration);
			timerLog.setPetId(Integer.parseInt(petId));
			timerLog.setPetParentId(Integer.parseInt(petParentId));
			timerLog.setTimerDate(timerDate);
			timerLog.setUserId(petParentId);

			migratedService.managePetTimerLog(timerLog);

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONObject().put("responseCode", "SUCCESS").put("responseMessage", "SUCCESS"));
			response.put("success", true);
			response.put("warnings", "null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response updateSensorSetupStatus(String payload, String token) {
		LOGGER.debug("updateSensorSetupStatus called");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {
			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);

			String petParentId = request.getString("ClientID");
			String petId = request.getString("PatientID");
			String setupStatus = request.getString("SetupStatus");
			String ssidList = request.getString("SSIDList");
			String deviceNumber = request.getString("DeviceNumber");

			if (petParentId == null || petParentId.trim().length() == 0) {
				throw new ServiceValidationException("Clinet ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			if (petId == null || petId.trim().length() == 0) {
				throw new ServiceValidationException("Patient Id is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_ID_REQUIRED)));
			}

			if (setupStatus == null || setupStatus.trim().length() == 0) {
				throw new ServiceValidationException("Setup Status is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.SETUP_STATUS_REQUIRED)));
			}

			if (ssidList == null || ssidList.trim().length() == 0) {
				throw new ServiceValidationException("SSID List is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.SSID_REQUIRED)));
			}

			SensorDetailsDTO sensorDetailsDTO = new SensorDetailsDTO();
			sensorDetailsDTO.setDeviceNumber(deviceNumber);
			sensorDetailsDTO.setPetId(petId);
			sensorDetailsDTO.setPetParentId(petParentId);
			sensorDetailsDTO.setSetupStatus(setupStatus);
			sensorDetailsDTO.setSsidList(ssidList);
			sensorDetailsDTO.setUserId(petParentId);

			boolean sensorUpdateStatus = migratedService.updateSensorSetupStatus(sensorDetailsDTO);
			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONObject().put("Key", sensorUpdateStatus));
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response getSensorStatus(String payload, String token) {
		LOGGER.debug("getSensorSetupStatus called");
		// System.out.println("getSensorSetupStatus entered");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {
			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);
			String petParentId = request.getString("ClientID");
			String petId = request.getString("PetID");

			if (petParentId == null || petParentId.trim().length() == 0) {
				throw new ServiceValidationException("Clinet ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			if (petId == null || petId.trim().length() == 0) {
				throw new ServiceValidationException("Pet ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_ID_REQUIRED)));
			}
			LOGGER.debug("petParentId " + petParentId + " petId " + petId);
			SensorDetailsDTO sensorDetailsDTO = migratedService.getSensorSetupStatus(petParentId, petId);

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONObject().put("SetupStatus", sensorDetailsDTO.getSetupStatus())
					.put("SSIDList", sensorDetailsDTO.getSsidList() == null ? " " : sensorDetailsDTO.getSsidList()));
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response manageSensorChargingNotificationSettings(String payload, String token) {
		LOGGER.debug("entered into manageSensorChargingNotificationSettings");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {
			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}
			request = new JSONObject(payload);

			int petParentId = request.getInt("ClientID");
			int petId = request.getInt("PetID");
			String notificationType = request.getString("NotificationType");
			String notificationDay = request.getString("NotificationDay");
			String opt = request.getString("Opt");

			if (petParentId == 0) {
				throw new ServiceValidationException("Clinet ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			if (notificationType == null || notificationType.trim().length() == 0) {
				throw new ServiceValidationException("Notification Type is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.NOTIFICATION_TYPE_REQUIRED)));
			}

			SensorDetailsDTO sensorDetailsDTO = new SensorDetailsDTO();
			sensorDetailsDTO.setPetId(Integer.toString(petId));
			sensorDetailsDTO.setPetParentId(Integer.toString(petParentId));
			sensorDetailsDTO.setNotificationType(notificationType);
			sensorDetailsDTO.setOpt(opt);

			Calendar weekStartDate = Calendar.getInstance();
			if (notificationType.trim().toLowerCase().equals("weekly")) {
				OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
				int DayNo = 0;
				DayOfWeek Day = now.getDayOfWeek();
				int Days = Day.getValue() - DayOfWeek.MONDAY.getValue();
				now = now.minusDays(Days);
				weekStartDate.setTimeInMillis(now.toInstant().toEpochMilli());

				if (notificationDay != null && notificationDay.trim().length() > 0) {
					switch (notificationDay) {
					case "monday":
						DayNo = 0;
						break;
					case "tuesday":
						DayNo = 1;
						break;
					case "wednesday":
						DayNo = 2;
						break;
					case "thursday":
						DayNo = 3;
						break;
					case "friday":
						DayNo = 4;
						break;
					case "saturday":
						DayNo = 5;
						break;
					case "sunday":
						DayNo = 6;
						break;
					default:
						DayNo = 0;
						break;
					}
				}

				weekStartDate.add(Calendar.DAY_OF_MONTH, DayNo);
				Calendar notificationDate = Calendar.getInstance();
				notificationDate.setTimeInMillis(weekStartDate.getTimeInMillis());
				Calendar startTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

				if (notificationDate.get(Calendar.DAY_OF_MONTH) <= startTime.get(Calendar.DAY_OF_MONTH)) {
					notificationDate.add(Calendar.DAY_OF_MONTH, 7);
				}
				sensorDetailsDTO.setNotificationDate(notificationDate.getTime());
			}
			boolean status = false;
			if (petParentId > 0 && sensorDetailsDTO.getNotificationDate() != null) {
				status = migratedService.manageSensorChargingNotificationSettings(sensorDetailsDTO);
			}
			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", status);
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response validateDeviceNumber(String payload, String token) {
		LOGGER.debug("entered into validateDeviceNumber");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {
			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}

			request = new JSONObject(payload);
			String sensorNumber = request.getString("SensorNumber");
			String clientId = request.getString("ClientID");

			if (sensorNumber == null || sensorNumber.trim().length() == 0) {
				throw new ServiceValidationException("Sensor Number is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_NUMBER_REQUIRED)));
			}

			if (clientId == null || clientId.trim().length() == 0) {
				throw new ServiceValidationException("Client ID is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.CLIENT_ID_REQUIRED)));
			}

			String responseCode = "";
			String message = "";
			boolean IsValidDeviceNumber = false;

			DeviceAssignDTO assignDTO = migratedService.getDeviceAssignInfoByDeviceNumber(sensorNumber, clientId);
			if (!assignDTO.isIsAssign()) {
				responseCode = "SUCCESS";
				message = "SUCCESS";
				IsValidDeviceNumber = true;
			} else {
				responseCode = "ERROR";
				if (assignDTO.isIsSameClient()) {
					message = "The sensor you entered is already assigned to " + assignDTO.getPetName()
							+ " in your practice. You must un-assign the sensor from " + assignDTO.getPetName()
							+ " before it can be used with another patient.";
				} else {
					message = "The sensor you entered is already assigned to another patient. Please confirm you entered the correct device number or select a different sensor.";
				}

				IsValidDeviceNumber = false;
			}

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONObject().put("isValidDeviceNumber", IsValidDeviceNumber)
					.put("message", message).put("responseCode", responseCode));
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response manageClientInfo(String payload, String token) {
		LOGGER.debug("entered into manageClientInfo");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		JSONObject result = new JSONObject();
		try {

			/*
			 * int petParentKeyId = migratedService.getPetParentByPetParentKey(token); if
			 * (petParentKeyId == 0) { return
			 * Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).
			 * build(); }
			 */

			request = new JSONObject(payload);

			String email = request.getString("Email");
			String firstName = request.getString("FirstName");
			String lastName = request.getString("LastName");
			String phoneNumber = request.getString("PhoneNumber");

			if (email == null || email.trim().length() == 0) {
				throw new ServiceValidationException("Email is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.EMAIL_REQUIRED)));
			}

			if (firstName == null || firstName.trim().length() == 0) {
				throw new ServiceValidationException("First Name is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.FIRST_NAME_REQUIRED)));
			}

			if (lastName == null || lastName.trim().length() == 0) {
				throw new ServiceValidationException("Last Name is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.LAST_NAME_REQUIRED)));
			}

			if (phoneNumber == null || phoneNumber.trim().length() == 0) {
				throw new ServiceValidationException("Phone Number is not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PHONE_NUMBER_REQUIRED)));
			}

			ClientInfo client = migratedService.getClientInfoByEmail(email);

			if (client != null && client.getClientId() > 0) {
				String password = migratedService.getPasswordByClientID(client.getClientId());
				if (StringUtils.isEmpty(password)) {
					client.setFirstName(firstName);
					client.setLastName(lastName);

					String fullName = "";
					if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
						fullName += firstName + " " + lastName;
					} else {
						if (StringUtils.isNoneEmpty(firstName)) {
							fullName = firstName;
						}
					}

					client.setFullName(fullName);

					if (client.getPhoneNumber() != null && client.getPhoneNumber().trim().length() > 0) {
						if (phoneNumber != null && phoneNumber.trim().length() > 0) {
							if (phoneNumber.contains(" ")) {
								String[] phoneCodeArray = phoneNumber.split(" ");
								String countryCode = phoneCodeArray[0];
								String phoneCode = phoneCodeArray[1];
								if (countryCode == "+1") {
									String firstPhoneCode = " (" + phoneCode.substring(0, 3) + ") ";
									String secondPhoneCode = phoneCode.substring(3, 3);
									String thirdPhoneCode = "-" + phoneCode.substring(6);
									phoneNumber = countryCode + firstPhoneCode + secondPhoneCode + thirdPhoneCode;
									client.setPhoneNumber(phoneNumber);
								}
							}
						}
					}
					client.setUserId("1");
					boolean status = migratedService.updateClientInfo(client);
					if (status) {
						result.put("ClientID", client.getClientId());
						result.put("FirstName", client.getFirstName());
						result.put("LastName", client.getLastName());
						result.put("Email", client.getEmail());
						result.put("PhoneNumber", client.getPhoneNumber());
						result.put("ResponseCode", "SUCCESS");
					} else {
						result.put("ClientID", 0);
						result.put("FirstName", "");
						result.put("LastName", "");
						result.put("Email", "");
						result.put("PhoneNumber", "");
						result.put("ResponseCode", "Please contact system administrator.");

					}
				} else {
					result.put("ClientID", 0);
					result.put("FirstName", "");
					result.put("LastName", "");
					result.put("Email", "");
					result.put("PhoneNumber", "");
					result.put("ResponseCode", "Given email address already existed in the System.");
				}
			} else {
				String fullName = "";
				if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName)) {
					fullName += firstName + " " + lastName;
				} else {
					if (StringUtils.isNoneEmpty(firstName)) {
						fullName = firstName;
					}
				}

				if (phoneNumber != null && phoneNumber.trim().length() > 0) {
					if (phoneNumber.contains(" ")) {
						String[] phoneCodeArray = phoneNumber.split(" ");
						String countryCode = phoneCodeArray[0];
						String phoneCode = phoneCodeArray[1];
						if (countryCode == "+1") {
							String firstPhoneCode = " (" + phoneCode.substring(0, 3) + ") ";
							String secondPhoneCode = phoneCode.substring(3, 3);
							String thirdPhoneCode = "-" + phoneCode.substring(6);
							phoneNumber = countryCode + firstPhoneCode + secondPhoneCode + thirdPhoneCode;
						}
					}
				}

				ClientInfo newClientInfo = new ClientInfo();
				newClientInfo.setFirstName(firstName);
				newClientInfo.setLastName(lastName);
				newClientInfo.setEmail(email);
				newClientInfo.setFullName(fullName);
				newClientInfo.setPhoneNumber(phoneNumber);
				newClientInfo.setUserId("1");
				int clientID = migratedService.insertClientInfo(newClientInfo);
				if (clientID > 0) {
					result.put("ClientID", clientID);
					result.put("FirstName", firstName);
					result.put("LastName", lastName);
					result.put("Email", email);
					result.put("PhoneNumber", phoneNumber);
					result.put("ResponseCode", "SUCCESS");
				}
			}

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", result);
			response.put("success", true);
			response.put("warnings", "null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@SuppressWarnings("unused")
	@Override
	public Response completeOnboardingInfo(String payload, String token) {
		LOGGER.debug("entered into completeOnboardingInfo");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {

			int petParentKeyId = migratedService.getPetParentByPetParentKey(token);
			if (petParentKeyId == 0) {
				return Response.status(Response.Status.FORBIDDEN).entity(Constants.INVALID_TOKEN).build();
			}
			request = new JSONObject(payload);
			JSONObject about = (JSONObject) request.get("About");
			JSONObject plan = (JSONObject) request.get("Plan");
			JSONObject device = (JSONObject) request.get("Device");
			JSONObject client = (JSONObject) request.get("Client");
			JSONObject billing = (JSONObject) request.get("Billing");

			if (about.getString("PetName") == null || about.getString("PetName").trim().length() == 0) {
				throw new ServiceValidationException("Pet Name not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_NAME_REQUIRED)));
			}

			if (about.getString("PetGender") == null || about.getString("PetGender").trim().length() == 0) {
				throw new ServiceValidationException("Gender not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PET_GENDER_REQUIRED)));
			}

			if (about.getString("IsNeutered") == null || about.getString("IsNeutered").trim().length() == 0) {
				throw new ServiceValidationException("IsNeutered not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.IS_NEUTERED_REQUIRED)));
			}

			if (about.getString("PetWeight") != null && about.getString("PetWeight").trim().length() == 0) {
				throw new ServiceValidationException("Weight not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.WEIGHT_REQUIRED)));
			}

			if (about.getString("WeightUnit") == null || about.getString("WeightUnit").trim().length() == 0) {
				throw new ServiceValidationException("Weight Unit not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.WEIGHT_UNIT_REQUIRED)));
			}

			if (about.getString("IsMixed") == null || about.getString("IsMixed").trim().length() == 0) {
				throw new ServiceValidationException("IsMixed not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.IS_MIXED_REQUIRED)));
			}

			if (about.getString("PetBreedID") == null || about.getString("PetBreedID").trim().length() == 0) {
				throw new ServiceValidationException("Breed ID not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.BREED_ID_REQUIRED)));
			}

			if (device.getString("SensorNumber") == null || device.getString("SensorNumber").trim().length() == 0) {
				throw new ServiceValidationException("Sensor Number not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_NUMBER_REQUIRED)));
			}

			if (device.getString("DeviceType") == null || device.getString("DeviceType").trim().length() == 0) {
				throw new ServiceValidationException("Device Type not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_TYPE_REQUIRED)));
			}

			if (device.getString("DeviceAddDate") == null || device.getString("DeviceAddDate").trim().length() == 0) {
				throw new ServiceValidationException("Device Add Date not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.DEVICE_ADD_DATE_REQUIRED)));
			}

			if (client.getString("ClientEmail") == null || client.getString("ClientEmail").trim().length() == 0) {
				throw new ServiceValidationException("Email not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.EMAIL_REQUIRED)));
			}

			if (client.getString("ClientFirstName") == null
					|| client.getString("ClientFirstName").trim().length() == 0) {
				throw new ServiceValidationException("Client First Name not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.FIRST_NAME_REQUIRED)));
			}

			if (client.getString("ClientLastName") == null || client.getString("ClientLastName").trim().length() == 0) {
				throw new ServiceValidationException("Client Last Name not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.LAST_NAME_REQUIRED)));
			}

			if (client.getString("ClientPhone") == null || client.getString("ClientPhone").trim().length() == 0) {
				throw new ServiceValidationException("Client Phone not provided",
						Arrays.asList(new WearablesError(WearablesErrorCode.PHONE_NUMBER_REQUIRED)));
			}

			StringBuilder sbStatus = new StringBuilder();
			int petID = 0;
			String OnboardingInfoGuid;
			String UID = "";

			OnboardingInfo onboardingInfo = new OnboardingInfo();
			onboardingInfo.setClinicID(2901);// Convert.ToInt32(AppConfig.StudyId);
			onboardingInfo.setUserID(1);// Convert.ToInt32(AppConfig.ClinicUserID);

			if (about != null) {
				String petName = about.getString("PetName");
				if (petName != null && petName.trim().length() > 0) {
					onboardingInfo.setTitle(petName);
				}
			}

			onboardingInfo.setData(payload);
			OnboardingInfoGuid = migratedService.handleOnboardingInfo(onboardingInfo);
			OnboardingInfo onboardingData = migratedService.getOnboardingInfoByUID(OnboardingInfoGuid);
			List<MonitoringPlan> monitoringPlanList = migratedService
					.getMonitoringPlanList(onboardingData.getClinicID());
			PetInfoDTO petInfo = new PetInfoDTO();
			String petId = about.getString("PetID");
			if (petId != null && petId.trim().length() != 0) {
				petID = Integer.parseInt(about.getString("PetID"));
				petInfo = migratedService.getPetInfoByID(petID);
			}

			String clientId = client.getString("ClientID");
			if (clientId != null && clientId.trim().length() > 0) {
				petInfo.setPetParentId(Integer.parseInt(clientId));
			}
			petInfo.setPetName(about.getString("PetName"));
			petInfo.setGender(about.getString("PetGender"));
			if (about.getString("IsUnknown") != null && about.getString("IsUnknown").trim().length() > 0) {
				petInfo.setUnknown(Boolean.valueOf(about.getString("IsUnknown")));
			}
			petInfo.setBirthDay(about.getString("PetBirthday"));
			if (about.getString("PetBreedID") != null && about.getString("PetBreedID").trim().length() > 0) {
				petInfo.setBreedId(Integer.parseInt(about.getString("PetBreedID")));
			}

			if (about.getString("IsMixed") != null && about.getString("IsMixed").trim().length() > 0) {
				petInfo.setIsMixed(Boolean.valueOf(about.getString("IsMixed")));
			}

			petInfo.setMixBreed(about.getString("PetMixBreed"));
			petInfo.setWeightUnit(about.getString("WeightUnit"));
			petInfo.setWeight((about.getString("PetWeight")));
			petInfo.setUserId(onboardingInfo.getUserID());
			/*
			 * if (about.getString("PetWeight") != null &&
			 * about.getString("PetWeight").trim().length() > 0) { double weight =
			 * Double.parseDouble(about.getString("PetWeight")); if
			 * (!petInfo.getWeightUnit().equalsIgnoreCase("lbs")) { weight = weight *
			 * 2.20462; } petInfo.setWeight(Double.toString(weight)); }
			 */

			if (about.getString("PetBFI") != null && about.getString("PetBFI").trim().length() > 0) {
				petInfo.setPetBFI(Integer.parseInt(about.getString("PetBFI")));
			}

			if (about.getString("IsNeutered") != null && about.getString("IsNeutered").trim().length() > 0) {
				petInfo.setNeutered(Boolean.valueOf(about.getString("IsNeutered")));
			}

			if (petId != null && petId.trim().length() != 0) {
				petInfo.setPetId(Integer.parseInt(petId));
				migratedService.updatePetInfo(petInfo); // Completed
				sbStatus.append("UPDATE PETINFO " + petId);
			} else {
				petInfo.setAlgorithmVersion("2.9.2");
				petID = migratedService.insertPetInfo(petInfo);
				petInfo.setPetId(petID);
				sbStatus.append("INSERT PETINFO " + petId);
			}
			int clientID = 0;
			ClientInfo clientInfo = new ClientInfo();
			if (client.getString("ClientID") != null && client.getString("ClientID").trim().length() > 0) {
				clientID = Integer.parseInt(client.getString("ClientID"));
				clientInfo.setClientId(clientID);
				clientInfo = migratedService.getClientInfoById(Integer.toString(clientID));
			}
			clientInfo.setFullName(client.getString("ClientFullName"));
			clientInfo.setEmail(client.getString("ClientEmail"));
			clientInfo.setPhoneNumber(client.getString("ClientPhone"));
			clientInfo.setCustomerID(billing.getString("CustomerID"));
			clientInfo.setUserId(Integer.toString(onboardingInfo.getUserID()));
			if (clientID > 0) {
				migratedService.updateClientInfo(clientInfo);
				sbStatus.append("UPDATE CLIENTINFO " + clientID);
			} else {
				if (clientInfo.getPassword() != null && clientInfo.getPassword().trim().length() > 0) {
					clientInfo.setPassword(Cryptography.encrypt(clientInfo.getPassword(), clientInfo.getUniqueId()));
				}

				if (clientInfo.getFullName() != null && clientInfo.getFullName().trim().length() > 0) {
					if (clientInfo.getFullName().contains(" ")) {
						String[] nameArray = clientInfo.getFullName().split(" ");
						clientInfo.setFirstName(nameArray[0]);
						clientInfo.setLastName(nameArray[1]);
					} else {
						clientInfo.setFirstName(clientInfo.getFullName());
						clientInfo.setLastName("");
					}
				} else {
					clientInfo.setFirstName("");
					clientInfo.setLastName("");
				}
				clientID = migratedService.insertClientInfo(clientInfo); // Completed
				clientInfo.setClientId(clientID);
				sbStatus.append("INSERT CLIENTINFO " + clientID);
			}

			int planID = 0;
			MonitoringPlan monitoringPlan = new MonitoringPlan();
			monitoringPlan.setPetID(petID);
			monitoringPlan.setClientID(clientID);
			monitoringPlan.setClinicID(onboardingData.getClinicID());
			monitoringPlan.setSubscriptionID(billing.getString("SubscriptionID"));
			monitoringPlan.setFree(onboardingData.isIsFree());
			monitoringPlan.setUserID(onboardingData.getUserID());
			Calendar startTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			monitoringPlan.setStartDate(getStringDateFormat("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
			startTime.add(Calendar.YEAR, 1);
			monitoringPlan.setEndDate(getStringDateFormat("yyyy-MM-dd HH:mm:ss", startTime.getTime()));

			String PlanTypeID = plan.getString("PlanTypeID");
			if (PlanTypeID != null && PlanTypeID.trim().length() > 0) {
				monitoringPlan.setTypeID(Integer.parseInt(PlanTypeID));
			}
			// String comment = "onboarded new patient "+petInfo.getPetName();
			planID = migratedService.insertMonitoringPlan(monitoringPlan);

			sbStatus.append("INSERT MONITORINGPLAN " + planID);

			PetCheckedInfo petCheckedInfo = new PetCheckedInfo();
			petCheckedInfo.setPetID(petID);
			petCheckedInfo.setCheckedBy(onboardingData.getUserID());

			startTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			petCheckedInfo.setCheckedTime(getStringDateFormat("yyyy-MM-dd HH:mm:ss", startTime.getTime()));
			startTime.add(Calendar.DATE, 1);
			String petCheckStartTime = "06:00";
			String[] configTime = petCheckStartTime.split(":");
			int hours = Integer.parseInt(configTime[0]);
			int minutes = Integer.parseInt(configTime[1]);
			startTime.set(Calendar.HOUR, hours);
			startTime.set(Calendar.MINUTE, minutes);
			petCheckedInfo.setNextCheckedTime(getStringDateFormat("yyyy-MM-dd HH:mm:ss", startTime.getTime()));

			int petCheckedInfoID = migratedService.handlePetCheckedInfo(petCheckedInfo);
			sbStatus.append("INSERT PETCHECKEDINFO " + petCheckedInfoID);

			boolean IsJoinCompetition = false;
			String joinCompleted = plan.getString("IsJoinCompetition");
			if (joinCompleted != null && joinCompleted.trim().length() > 0) {
				IsJoinCompetition = Boolean.parseBoolean(joinCompleted);
			}

			DeviceAssignDTO deviceAssign = new DeviceAssignDTO();
			int deviceID = 0;
			String deviceNumber = "";
			if (device.getString("SensorNumber") != null && device.getString("SensorNumber").trim().length() > 0) {
				deviceNumber = device.getString("SensorNumber");
				DeviceInfo deviceInfo = migratedService.getDeviceInfoByDeviceNumber(deviceNumber);
				if (deviceInfo.getDeviceID() > 0) {
					deviceID = deviceInfo.getDeviceID();
				} else {
					deviceInfo = new DeviceInfo();
					deviceInfo.setDeviceNumber(deviceNumber);
					deviceInfo.setDeviceType(device.getString("DeviceType"));
					deviceInfo.setAddDate(device.getString("DeviceAddDate"));
					deviceInfo.setUserId(onboardingData.getUserID());
					deviceID = migratedService.insertDeviceInfo(deviceInfo);
				}

				if (deviceID > 0) {
					deviceAssign = new DeviceAssignDTO();
					deviceAssign.setUserId(onboardingData.getUserID());
					deviceAssign.setPetID(petID);
					deviceAssign.setPlanID(planID);
					deviceAssign.setDeviceID(deviceID);
					deviceAssign.setDeviceNumber(deviceNumber);
					deviceAssign.setIsAssign(true);
					deviceAssign.setAssignDate(device.getString("DeviceAddDate"));

					int id = migratedService.insertDeviceAssign(deviceAssign);
					sbStatus.append("INSERT DEVICEASSIGN " + id);
				}

			}

			migratedService.updateOnboardingArchived(UID, onboardingData.getUserID());
			migratedService.updateOnboardingStatus(UID, sbStatus.toString(), onboardingData.getUserID());

			String emailSubject = "Welcome to Wearables Clinical Trials";
			String emailContent = EmailTemplate.getPetOnboardWelcomeEmail(clientInfo.getFirstName());

			// Sending mail using SendGrid
			ThreadPoolExecutor threadPoolExecutor = EmailThreadPoolExecutor.getEmailThreadPoolExecutor();
			EmailSenderThread emailSenderThread = new EmailSenderThread(clientInfo.getEmail(), emailSubject,
					emailContent);
			threadPoolExecutor.submit(emailSenderThread);

			JSONObject result = new JSONObject();
			result.put("encryptPetID", Cryptography.encryptByAES(Integer.toString(petID)));
			result.put("petID", petID);
			result.put("responseCode", "SUCCESS");
			result.put("uid", UID);

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("result", result);
			response.put("responseMessage", "SUCCESS");
			response.put("success", true);
			response.put("warnings", "null");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}

	@Override
	public Response logoutUser(String payload, String token) {
		LOGGER.debug("entered into logoutUser");
		JSONObject request = null;
		JSONObject response = new JSONObject();
		try {

			request = new JSONObject(payload);
			String petParentId = request.getString("PetParentId");

			migratedService.logoutUser(petParentId, token);

			response.put("errors", "null");
			response.put("responseCode", 0);
			response.put("responseMessage", "SUCCESS");
			response.put("result", new JSONObject().put("responseCode", "SUCCESS"));
			response.put("success", true);
			response.put("warnings", "null");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(response.toString()).build();
	}
	
	@Override
	public Response validate() {
		JSONObject response = new JSONObject();
		response.put("responseMessage", "SUCCESS");
		return Response.status(Response.Status.OK).entity(response.toString()).build();

	}

	private String sendClientVerificationCode(ClientInfo clientInfo, String deliveryMethod, boolean isClinicTrial)
			throws ParseException {
		String sid = "";
		if (clientInfo.getClientId() > 0) {
			int clientID = clientInfo.getClientId();
			ClientSMSCode clientSMSCode = migratedService.getClientSMSCodeByClientID(clientID);

			Calendar calendar = null;
			if (clientSMSCode.getAddDate() != null) {
				Date date = getDateStringToDate(dateFormat, clientSMSCode.getAddDate());// formatter.parse(clientSMSCode.getAddDate());
				calendar = Calendar.getInstance();
				calendar.setTimeInMillis(date.getTime());
				calendar.add(Calendar.DATE, 7);
			}

			Calendar currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

			if (clientSMSCode.getPetParentSMSCodeId() > 0 && calendar != null && calendar.compareTo(currentTime) >= 0) {
				if (deliveryMethod == "Email") {
					sid = sendClientEmailVerificationCode(clientInfo.getEmail(), clientSMSCode.getVerificationCode(),
							isClinicTrial);
				}
			} else {
				if (clientSMSCode.getPetParentSMSCodeId() > 0) {
					clientSMSCode.setExpired(true);
					clientSMSCode.setUserId(clientInfo.getUserId());
					migratedService.updateClientSMSCode(clientSMSCode);
				}

				Random random = new Random();
				clientSMSCode = new ClientSMSCode();
				clientSMSCode.setClientID(clientInfo.getClientId());
				clientSMSCode.setVerificationCode(Integer.toString(random.nextInt((1000000 - 100000) + 1) + 100000));
				clientSMSCode.setExpired(false);
				currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				Date utilDate = currentTime.getTime();
				String strDate = getStringDateFormat(dateFormat, utilDate);
				clientSMSCode.setAddDate(strDate);
				clientSMSCode.setUserId(clientInfo.getUserId());

				int result = migratedService.insertClientSMSCode(clientSMSCode);
				if (result > 0) {
					if (deliveryMethod == "Email") {
						sid = sendClientEmailVerificationCode(clientInfo.getEmail(),
								clientSMSCode.getVerificationCode(), isClinicTrial);
					}
				}
			}
		}
		return sid;
	}

	private String sendClientEmailVerificationCode(String email, String verificationCode, boolean isClinicTrial) {
		String sid = "";
		String emailSubject = "Verification Code";
		String content = EmailTemplate.getSMSCodeEmail(verificationCode, isClinicTrial);

		// Sending mail using SendGrid
		ThreadPoolExecutor threadPoolExecutor = EmailThreadPoolExecutor.getEmailThreadPoolExecutor();
		EmailSenderThread emailSenderThread = new EmailSenderThread(email, emailSubject, content);
		threadPoolExecutor.submit(emailSenderThread);

		sid = "OK";
		return sid;
	}

	private String getStringDateFormat(String dateFormat, Date date) {
		if (date != null) {
			java.text.SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.format(date);
		} else {
			return null;
		}
	}

	private Date getDateStringToDate(String dateFormat, String date) throws ParseException {
		if (date != null && dateFormat != null) {
			return new SimpleDateFormat(dateFormat).parse(date);
		} else {
			return null;
		}
	}

}
