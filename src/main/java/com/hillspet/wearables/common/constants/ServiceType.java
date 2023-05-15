package com.hillspet.wearables.common.constants;

import java.util.Arrays;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.UriInfo;

import com.hillspet.wearables.common.utils.WearablesUtils;

/**
 * Enum contains a part of the resource url that can help in identifying the
 * type of service called by the user The part of URL when matched with the
 * request URL will identify the service type.
 * 
 * @author vvodyaram
 *
 */
public enum ServiceType implements EnumInterface<String> {
	/**
	 * management related services will have the below in the path
	 */
	MANAGEMENT("Management", "management/", Boolean.FALSE, Boolean.FALSE),

	/**
	 * swagger-ui will have the below in its path initially when opening the
	 * swagger.json
	 */
	SWAGGER_API_DOCS("SwaggerApiDocs", "api-docs", Boolean.FALSE, Boolean.FALSE),

	/**
	 * swagger-ui will have the below when it redirects to the swagger-ui to display
	 * the contract
	 */
	SWAGGER_UI("SwaggerServices", "swagger", Boolean.FALSE, Boolean.FALSE),

	/**
	 * Ideally the best way to identify if the service is an application level i.e
	 * getTheaters, getOrders etc is to check that url does not contain anything
	 * from the non-application services
	 */
	APPLICATION("ApplicationService", "/", Boolean.TRUE, Boolean.TRUE);

	private String serviceType;

	private String serviceUri;

	private boolean isRequestHeaderValidationRequired;

	private boolean isAuthNeeded;

	/**
	 * 
	 * @param serviceType                       - A user friendly name to identify
	 *                                          the service
	 * @param serviceUri                        - Part of the url that will be used
	 *                                          to match the service
	 * @param isRequestHeaderValidationRequired - If request header validations
	 *                                          needs to be triggered for the type
	 *                                          of service
	 * @param isAuthNeeded                      - If authentication is needed for
	 *                                          the service or not
	 */
	private ServiceType(String serviceType, String serviceUri, boolean isRequestHeaderValidationRequired,
			boolean isAuthNeeded) {
		this.serviceType = serviceType;
		this.serviceUri = serviceUri;
		this.isRequestHeaderValidationRequired = isRequestHeaderValidationRequired;
		this.isAuthNeeded = isAuthNeeded;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the serviceUri
	 */
	public String getServiceUri() {
		return serviceUri;
	}

	/**
	 * @param serviceUri the serviceUri to set
	 */
	public void setServiceUri(String serviceUri) {
		this.serviceUri = serviceUri;
	}

	public boolean isRequestHeaderValidationRequired() {
		return isRequestHeaderValidationRequired;
	}

	public boolean isAuthNeeded() {
		return isAuthNeeded;
	}

	@Override
	public String getCode() {
		return this.serviceType;
	}

	/**
	 * Helper method to determine the service type based on the request resource url
	 * 
	 * @param resourceUrl
	 * @return
	 */
	public static ServiceType getServiceTypeEnum(@NotNull final UriInfo uriInfo) {
		final String resourceUrl = WearablesUtils.getResourceUrl(uriInfo);
		// we will return Application serviceType only if it does not match anything
		// else.
		return Arrays.asList(values()).stream().filter(serviceType -> !ServiceType.APPLICATION.equals(serviceType)
				&& resourceUrl.contains(serviceType.serviceUri)).findFirst().orElse(APPLICATION);
	}

}
