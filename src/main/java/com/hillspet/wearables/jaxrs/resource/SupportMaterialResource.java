package com.hillspet.wearables.jaxrs.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.response.MaterialCategoriesResponse;
import com.hillspet.wearables.response.MaterialTypesResponse;
import com.hillspet.wearables.response.SupportMaterialsResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * This class providing Support Document details.
 * 
 * @author sgorle
 * @since w2.0
 * @version w2.0
 * @version Dec 8, 2021
 */
@Path("/supportDocs")
@Api(value = "RESTful service that performs support document related operations", tags = { "Support Documents Management" })
@Produces({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
@Consumes({ MediaType.APPLICATION_JSON_VALUE, Constants.MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 })
public interface SupportMaterialResource {
	
	@GET
	@Path("/getMaterialTypeList")
	@ApiOperation(value = "Get Campaign List", notes = "Gets the Campaign List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = MaterialTypesResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getMaterialTypeList(@HeaderParam("ClientToken") String token);
	
	@GET
	@Path("/getMaterialCategoryList")
	@ApiOperation(value = "Get Campaign List", notes = "Gets the Campaign List")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = MaterialCategoriesResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getMaterialCategoryList(@QueryParam("categoryId") int categoryId, @HeaderParam("ClientToken") String token);
	
	@GET
	@Path("/getDeviceSupportDocs/{deviceType}/{deviceModel}")
	@ApiOperation(value = "Get Sensor support documents by deviceType and device model", notes = "Get Sensor support documents by deviceType and device model")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = SupportMaterialsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getDeviceSupportDocs(@PathParam("deviceType") String deviceType,
			@PathParam("deviceModel") String deviceModel, @HeaderParam("ClientToken") String token);

	@GET
	@Path("/getAppSupportDocs/{subCategoryType}")
	@ApiOperation(value = "Get App support documents by sub categroy type", notes = "Get App support documents by sub categroy type")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = SupportMaterialsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getAppSupportDocs(@PathParam("subCategoryType") int subCategoryType,
			@HeaderParam("ClientToken") String token);
	
	@GET
	@Path("/getSupportDocs")
	@ApiOperation(value = "Get App support documents by sub categroy type", notes = "Get App support documents by sub categroy type")
	@ApiResponses(value = {
			@ApiResponse(code = HttpStatus.SC_OK, message = "Successful Response", response = SupportMaterialsResponse.class),
			@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "Bad Request", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_NOT_FOUND, message = "Not Found", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_FORBIDDEN, message = "Forbidden", response = Message.class),
			@ApiResponse(code = HttpStatus.SC_INTERNAL_SERVER_ERROR, message = "Runtime Error or Internal Server Error", response = Message.class) })
	public Response getSupportDocs(@HeaderParam("ClientToken") String token);

}
