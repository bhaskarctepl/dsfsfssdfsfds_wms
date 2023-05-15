
/**
 * Created Date: 07-Jan-2021
 * Class Name  : QuestionnaireResource.java
 * Description : Description of the package.
 *
 * © Copyright 2020 Cambridge Technology Enterprises(India) Pvt. Ltd.,All rights reserved.
 *
 * * * * * * * * * * * * * * * Change History *  * * * * * * * * * * *
 * <Defect Tag>        <Author>        <Date>        <Comments on Change>
 * ID                sgorle          07-Jan-2021        Mentions the comments on change, for the new file it's not required.
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */
package com.hillspet.wearables.jaxrs.resource;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AssignSensorRequest;
import com.hillspet.wearables.request.QuestionAnswerRequest;
import com.hillspet.wearables.response.CampaignListResponse;
import com.hillspet.wearables.response.DeviceModelsResponse;
import com.hillspet.wearables.response.DeviceTypesResponse;
import com.hillspet.wearables.response.LeaderBoardResponse;
import com.hillspet.wearables.response.MobileAppFeedbackResponse;
import com.hillspet.wearables.response.PetCampaignListResponse;
import com.hillspet.wearables.response.PetCampaignResponse;
import com.hillspet.wearables.response.PetDevicesResponse;
import com.hillspet.wearables.response.PetRedemptionHistoryResponse;
import com.hillspet.wearables.response.QuestionnaireListResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Enter detailed explanation of the class here..
 * <p>
 * This class implementation of the <tt>Interface or class Name</tt> interface
 * or class. In addition to implementing the <tt>Interface Name</tt> interface,
 * this class provides methods to do other operations. (Mention other methods
 * purpose)
 *
 * <p>
 * More Description about the class need to be entered here.
 *
 * @author vvodyaram
 * @version Wearables Portal Mobile Release Version..
 * @since Available Since Wearables Portal Version.
 * @see New line seperated Classes or Interfaces related to this class.
 */
@Path("/")
@Api(value = "RESTful service that performs Mobile App Services operations", tags = { "Mobile App Services" })
@Produces({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
@Consumes({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
public interface MobileAppResource {

	@GET
	@Path("/getFeedbackQuestionnaireByPetId/{petId}")
	@ApiOperation(value = "Get the Questionnaire by Pet Id", notes = "Get the Questionnaire details by Pet Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getFeedbackQuestionnaireByPetId(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);
	
	@GET
	@Path("/getQuestionnaireByPetId/{petId}")
	@ApiOperation(value = "Get the Questionnaire by Pet Id", notes = "Get the Questionnaire details by Pet Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireByPetId(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);

	@GET
	@Path("/getQuestionnaireAnswers/{petId}/{questionnaireId}")
	@ApiOperation(value = "Get the Questionnaire by Id", notes = "Get the Questionnaire details by Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = QuestionnaireListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getQuestionnaireAnswers(@PathParam("petId") int petId,
			@PathParam("questionnaireId") int questionnaireId, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/saveQuestionAnswers")
	@ApiOperation(value = "Save Question Answers", notes = "Save Question Answers")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response saveQuestionAnswers(
			@Valid @ApiParam(name = "questionAnswerRequest", required = true) QuestionAnswerRequest questionAnswerRequest,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetCampaignPoints/{petId}")
	@ApiOperation(value = "Get Pet Campaign all points By Id", notes = "Get a Pet Campaign all points")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetCampaignResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetCampaignPoints(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetCampaignPointsList/{petId}")
	@ApiOperation(value = "Get Pet Campaign points list By Id", notes = "Get a Pet Campaign points list By")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetCampaignListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetCampaignPointsList(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);

	@GET
	@Path("/getCampaignListByPet/{petId}")
	@ApiOperation(value = "Get Campaign List By Pet Id", notes = "Get the Campaign List By Pet Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CampaignListResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getCampaignListByPet(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);

	@GET
	@Path("/getLeaderBoardByCampaignId/{campaignId}/{petId}")
	@ApiOperation(value = "Get Leader board By campaign Id", notes = "Get the Leader board By campaign Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = LeaderBoardResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getLeaderBoardByCampaignId(@PathParam("campaignId") int campaignId, @PathParam("petId") int petId,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetRedemptionHistory/{petId}")
	@ApiOperation(value = "Get Pet Redemption History By Pet Id", notes = "Get the Pet Redemption History By Pet Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetRedemptionHistoryResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetRedemptionHistory(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/assignSensorToPet")
	@ApiOperation(value = "Assign Sensor To Pet", notes = "Assign Sensor To Pet")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response assignSensorToPet(
			@Valid @ApiParam(name = "assignSensorRequest", required = true) AssignSensorRequest assignSensorRequest,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetDevicesByPetParent/{petParentId}")
	@ApiOperation(value = "Get Pet Devices By Pet Parent Id", notes = "Get Pet Devices By Pet Parent Id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetDevicesResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetDevicesByPetParent(@PathParam("petParentId") int petParentId,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getFeedbackByPetParent/{petParentId}")
	@ApiOperation(value = "Get Feedback by pet parent id", notes = "Get Feedback by pet parent id")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = MobileAppFeedbackResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getFeedbackByPetParent(@PathParam("petParentId") int petParentId,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getDeviceTypes")
	@ApiOperation(value = "Get Device Type Lookup", notes = "Gets the Device Type List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = DeviceTypesResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getDeviceTypes(@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getDeviceModels/{deviceType}")
	@ApiOperation(value = "Get Device Model Lookup", notes = "Gets the Device Model List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = DeviceModelsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getDeviceModels(@PathParam("deviceType") String deviceType, @HeaderParam("ClientToken") String token);

}
