package com.hillspet.wearables.jaxrs.resource;

import java.io.IOException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.dto.PetFeedingEnthusiasmScale;
import com.hillspet.wearables.dto.PetObservation;
import com.hillspet.wearables.objects.common.response.CommonResponse;
import com.hillspet.wearables.request.AddPetWeight;
import com.hillspet.wearables.request.PetAddFeedingPreferences;
import com.hillspet.wearables.request.PetAddImageScoring;
import com.hillspet.wearables.request.UpdatePet;
import com.hillspet.wearables.request.UpdatePetWeight;
import com.hillspet.wearables.response.EatingEnthusiasmScaleResponse;
import com.hillspet.wearables.response.ImageScoringScalesResponse;
import com.hillspet.wearables.response.PetBehaviorsResponse;
import com.hillspet.wearables.response.PetBreedResponse;
import com.hillspet.wearables.response.PetFeedingEnthusiasmScaleResponse;
import com.hillspet.wearables.response.PetFeedingPreferenceResponse;
import com.hillspet.wearables.response.PetFeedingTimeResponse;
import com.hillspet.wearables.response.PetMobileAppConfigResponse;
import com.hillspet.wearables.response.PetObservationResponse;
import com.hillspet.wearables.response.PetObservationsResponse;
import com.hillspet.wearables.response.PetSpeciesResponse;
import com.hillspet.wearables.response.PetWeightHistoryResponse;
import com.hillspet.wearables.response.PetWeightResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class providing Pet details.
 * 
 * @author sgorle
 * @since w2.0
 * @version w2.0
 * @version Dec 8, 2021
 */
@Path("/pets")
@Api(value = "RESTful service that performs pets related operations", tags = { "Pet Management" })
@Produces({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
@Consumes({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
public interface PetResource {

	@POST
	@Path("/addWeight")
	@ApiOperation(value = "Add pet weight", notes = "Add pet weight in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetWeightResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addWeight(@Valid @ApiParam(name = "addPetWeight", required = true) AddPetWeight addPetWeight,
			@HeaderParam("ClientToken") String token);

	@POST
	@Path("/updateWeight")
	@ApiOperation(value = "Update pet weight", notes = "Update pet weight in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetWeightResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updateWeight(
			@Valid @ApiParam(name = "updatePetWeight", required = true) UpdatePetWeight updatePetWeight,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("/weightHistory/{petId}")
	@ApiOperation(value = "Get pet weight", notes = "get pet weight by pet id from the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetWeightHistoryResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetWeightHistory(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token)
			throws IOException;

	@GET
	@Path("/getPetSpecies")
	@ApiOperation(value = "Get Pet Speecies List Lookup", notes = "Gets the Pet Speecies List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetSpeciesResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetSpecies(@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetBreeds/{speciesId}")
	@ApiOperation(value = "Get Pet Breed List By species ", notes = "Gets the Pet Breed List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetBreedResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetBreeds(@PathParam("speciesId") int speciesId, @HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetBehaviors/{speciesId}")
	@ApiOperation(value = "Get Pet Behaviors List By species and behavior type", notes = "Get Pet Behaviors List By species and behavior type")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetBehaviorsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetBehaviors(@PathParam("speciesId") int speciesId, @HeaderParam("ClientToken") String token,
			@QueryParam("behaviorTypeId") int behaviorTypeId);

	@GET
	@Path("/getPetEatingEnthusiasmScale")
	@ApiOperation(value = "Get Pet Speecies List Lookup", notes = "Gets the Pet Eating Enthusiasm Scale List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = EatingEnthusiasmScaleResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetEatingEnthusiasmScale(@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetFeedingTime")
	@ApiOperation(value = "Get Pet feeding preferences List Lookup", notes = "Gets the Pet Feeding Time List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetFeedingTimeResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetFeedingTime(@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getMobileAppConfigs/{petId}")
	@ApiOperation(value = "Get mobile app configs for a pet", notes = "get pet mobile app configs for a pet by petId from the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetMobileAppConfigResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getMobileAppConfigs(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token)
			throws IOException;

	@POST
	@Path("/addPetFeedingTime")
	@ApiOperation(value = "Add pet Feeding time with eating enthusiasm scale", notes = "Add pet Feeding time with eating enthusiasm scale in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetFeedingEnthusiasmScaleResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addPetFeedingTime(
			@Valid @ApiParam(name = "addPetFeedingTime", required = true) PetFeedingEnthusiasmScale petFeedingEnthusiasmScale,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("getPetObservations/{petId}")
	@ApiOperation(value = "Get observations by pet id", notes = "Gets observations by pet id in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetObservationsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetObservationsByPetId(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/savePetObservation")
	@ApiOperation(value = "Add or update pet observation", notes = "Add or update pet observation details in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetObservationResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response savePetObservation(
			@Valid @ApiParam(name = "savePetObservation", required = true) PetObservation savePetObservation,
			@HeaderParam("ClientToken") String token);

	@DELETE
	@Path("/{observationId}/{petId}/{petParentId}")
	@ApiOperation(value = "Delete pet observation", notes = "Deletes a pet observation in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response deletePetObservation(@PathParam("observationId") int observationId, @PathParam("petId") int petId,
			@PathParam("petParentId") int petParentId, @HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetImageScoringScales/{petId}")
	@ApiOperation(value = "Get Pet Image Scoring By pet id", notes = "Gets Pet Image Scoring By pet")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = ImageScoringScalesResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetImageScoringScales(@PathParam("petId") int petId, @HeaderParam("ClientToken") String token);

	@POST
	@Path("/addPetImageScoring")
	@ApiOperation(value = "Add pet Image Scoring", notes = "Add Pet Image Scoring details in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addPetImageScoring(
			@Valid @ApiParam(name = "addPetImageScoring", required = true) PetAddImageScoring addPetImageScorings,
			@HeaderParam("ClientToken") String token);

	@GET
	@Path("/getPetFeedingPreferences")
	@ApiOperation(value = "Get Pet feeding preferences List Lookup", notes = "Gets the Pet Feeding Preference List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetFeedingPreferenceResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getPetFeedingPreferences(@HeaderParam("ClientToken") String token);

	@POST
	@Path("/addPetFeedingPreferences")
	@ApiOperation(value = "Add Pet feeding preferences", notes = "Adds the Pet Feeding Preference")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = CommonResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response addPetFeedingPreferences(
			@Valid @ApiParam(name = "petAddFeedingPreferences", required = true) PetAddFeedingPreferences petAddFeedingPreferences,
			@HeaderParam("ClientToken") String token);
	
	@PUT
	@Path("/updatePet")
	@ApiOperation(value = "Update pet details", notes = "Update pet details in the application")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = PetWeightResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response updatePet(
			@Valid @ApiParam(name = "updatePet", required = true) UpdatePet updatePet,
			@HeaderParam("ClientToken") String token);
}
