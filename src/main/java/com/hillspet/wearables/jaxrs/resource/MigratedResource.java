package com.hillspet.wearables.jaxrs.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.objects.common.response.CommonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/migrated")
@Api(value = "RESTful service that have all the .net migrated services", tags = { "Migrated Services Management" })
@Produces({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
@Consumes({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
public interface MigratedResource {

	@POST
	@Path("/ClientLogin")
	@ApiOperation(value = "Clent Login", notes = "Clent Login")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response clientLogin(@RequestBody String payload);

	@POST
	@Path("/SendEmailVerificationCode")
	@ApiOperation(value = "Send Email Verification Code", notes = "Send Email Verification Code")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response sendEmailVerificationCode(@RequestBody String payload);

	@POST
	@Path("/CheckClientSMSCode")
	@ApiOperation(value = "Check Client SMS Code", notes = "Check Client SMS Code")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response checkClientSMSCode(@RequestBody String payload);

	@POST
	@Path("/SetClientPasswordBySMSCode")
	@ApiOperation(value = "Set Client Password By SMS Code", notes = "Set Client Password By SMS Code")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response setClientPasswordBySMSCode(@RequestBody String payload);

	@POST
	@Path("/ChangePassword")
	@ApiOperation(value = "Change Password", notes = "Change Password")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response changePassword(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/GetClientInfo")
	@ApiOperation(value = "Get Client Info", notes = "Get Pet Parent Info By Pet Parent Id")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response getClientInfo(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/ChangeClientInfo")
	@ApiOperation(value = "Change Client Info", notes = "Change Client Info")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response changeClientInfo(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/ManageMobileAppScreensFeedback")
	@ApiOperation(value = "Manage Mobile Screens Feedback", notes = "Manage Mobile Screens Feedback")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response manageMobileAppScreensFeedback(@RequestBody String payload,
			@HeaderParam("ClientToken") String token);

	@POST
	@Path("/GetPetTimerLog")
	@ApiOperation(value = "Get Pet TimerLog", notes = "Get Pet TimerLog")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response getPetTimerLog(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/ManagePetTimerLog")
	@ApiOperation(value = "Manage Pet TimerLog", notes = "Manage Pet TimerLog")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response managePetTimerLog(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/UpdateSensorSetupStatus")
	@ApiOperation(value = "Update Sensor Setup Status", notes = "Update Sensor Setup Status")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response updateSensorSetupStatus(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/GetSensorSetupStatus")
	@ApiOperation(value = "Get Sensor Setup Status", notes = "Get Sensor Setup Status")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response getSensorStatus(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/ManageSensorChargingNotificationSettings")
	@ApiOperation(value = "Manage Mobile Screens Feedback", notes = "Manage Mobile Screens Feedback")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response manageSensorChargingNotificationSettings(@RequestBody String payload,
			@HeaderParam("ClientToken") String token);

	@POST
	@Path("/ValidateDeviceNumber")
	@ApiOperation(value = "Validate Device Number", notes = "Validate Device Number")
	@ApiResponses(value = { @ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response validateDeviceNumber(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/ManageClientInfo")
	@ApiOperation(value = "Manage Client Info", notes = "Manage Client Info")
	@ApiResponses(value = { 
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response manageClientInfo(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/CompleteOnboardingInfo")
	@ApiOperation(value = "Complete Onboarding Info", notes = "Complete Onboarding Info")
	@ApiResponses(value = { 
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response completeOnboardingInfo(@RequestBody String payload, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/logout")
	@ApiOperation(value = "Logout User", notes = "Logout the user from application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response"),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request"),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found"),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden"),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error") })
	public Response logoutUser(@RequestBody String payload, @HeaderParam("ClientToken") String token);
	
	@GET
	@Path("/validate")
	@ApiOperation(value = "Validate App", notes = "check application health")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response validate();

}
