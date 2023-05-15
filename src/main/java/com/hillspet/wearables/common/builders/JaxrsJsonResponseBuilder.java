package com.hillspet.wearables.common.builders;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.response.BaseJsonResponse;

/**
 * A helper class to build a JAX-RS response based on the JsonResponse object provided.
 * 
 * @author vvodyaram
 */
@Component
public class JaxrsJsonResponseBuilder {

	/**
	 * A helper method to build a JAX-RS response that can be sent back to the caller
	 * @param jsonResponse
	 * @return
	 */
	public Response buildResponse(final BaseJsonResponse jsonResponse) {
		//V.Imp to include MediaType below else exception response does not work as expected
		return Response.ok(jsonResponse, MediaType.APPLICATION_JSON).status(jsonResponse.getStatus().getHttpStatus()).build();
	}
	
	
}
