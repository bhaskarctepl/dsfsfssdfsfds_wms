package com.hillspet.wearables.common.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.hillspet.wearables.common.constants.AuthenticationStatus;
import com.hillspet.wearables.common.constants.ServiceType;

/**
 * Represents a client's context i.e holds all the information pertaining to a
 * particular request. Each HTTP request will have its own ClientContext object
 * that will be instantiated before a request is processed.
 *
 * @author vvodyaram
 *
 */
public class ClientContext {

	private String appId;

	private AuthToken authToken;

	private AuthenticationStatus authStatus;

	private String appEnvironment;

	private String deviceId;

	private String acceptLanguage;

	private String appVersion;

	private String accept;

	private String contentType;

	private String theater;

	private String userId;

	private String wearablesCompanyId;

	// performance related parameters per request
	private String uuid;

	private long startTime;

	private String requestedHost;

	private Locale locale;

	// warnings added to client context so throughout the request life cycle it will
	// be available to the response builder for a request.
	private List<Warning> warnings;

	// service type
	private ServiceType serviceType;

	// resource URL for Management Services
	private String rawResourceUrl;

	private String xCorrelationId;

	/**
	 * To determine the Api Operation that was invoked
	 */
	private String apiOperation;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ClientContext [").append("appId=").append(appId).append(", authStatus=").append(authStatus)
				.append(", appEnvironment=").append(appEnvironment).append(", deviceId=").append(deviceId)
				.append(", acceptLanguage=").append(acceptLanguage).append(", appVersion=").append(appVersion)
				.append(", accept=").append(accept).append(", contentType=").append(contentType).append(", theater=")
				.append(theater).append(", userId=").append(userId).append(", wearablesCompanyId=").append(wearablesCompanyId)
				.append(", uuid=").append(uuid).append(", startTime=").append(startTime).append(", locale=")
				.append(locale).append(", warnings=").append(warnings).append(", serviceType=").append(serviceType)
				.append(", rawResourceUrl=").append(rawResourceUrl).append(", requestedHost=").append(requestedHost)
				.append(", xCorrelationId=").append(xCorrelationId).append(", apiOperation=").append(apiOperation)
				.append("]");
		return builder.toString();
	}

	/**
	 * Parameterized constructor can be private because only the ClientContext
	 * internal builder can call & provide an instance to clients.
	 *
	 * @param appId          - AppId
	 * @param jwt            - Optional<String>
	 * @param appEnvironment - String
	 * @param                deviceId- String
	 * @param acceptLanguage - Optional<String>
	 * @param appVersion     - String
	 * @param accept         -String
	 * @param theater        - Optional<String>
	 * @param uuid           - String
	 * @param startTime      - long
	 * @param locale         - Locale
	 */
	private ClientContext(String appId, AuthToken authToken, AuthenticationStatus authStatus, String appEnvironment,
			String deviceId, String acceptLanguage, String appVersion, String accept, String contentType,
			String theater, String userId, String wearablesCompanyId, String rc2Auth, String uuid, long startTime,
			Locale locale, List<Warning> warnings, ServiceType serviceType, String rawResourceUrl, String requestedHost,
			String xCorrelationId, String apiOperation) {

		super();
		this.appId = appId;
		this.authToken = authToken;
		this.authStatus = authStatus;
		this.appEnvironment = appEnvironment;
		this.deviceId = deviceId;
		this.acceptLanguage = acceptLanguage;
		this.appVersion = appVersion;
		this.accept = accept;
		this.theater = theater;
		this.userId = userId;
		this.wearablesCompanyId = wearablesCompanyId;
		this.uuid = uuid;
		this.startTime = startTime;
		this.locale = locale;
		this.warnings = warnings;
		this.serviceType = serviceType;
		this.rawResourceUrl = rawResourceUrl;
		this.requestedHost = requestedHost;
		this.apiOperation = apiOperation;
		this.contentType = contentType;
		this.xCorrelationId = xCorrelationId;
	}

	public String getAppId() {
		return appId;
	}

	public Optional<AuthToken> getAuthToken() {
		return Optional.ofNullable(authToken);
	}

	public String getAppEnvironment() {
		return appEnvironment;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public Optional<String> getAcceptLanguage() {
		return Optional.ofNullable(acceptLanguage);
	}

	public String getAppVersion() {
		return appVersion;
	}

	public String getAccept() {
		return accept;
	}

	public Optional<String> getContentType() {
		return Optional.ofNullable(contentType);
	}

	public String getRequestedHost() {
		return requestedHost;
	}

	public Optional<String> getTheater() {
		return Optional.ofNullable(theater);
	}

	public Optional<String> getUserId() {
		return Optional.ofNullable(userId);
	}

	public Optional<String> getWEARABLESCompanyId() {
		return Optional.ofNullable(wearablesCompanyId);
	}

	public String getUuid() {
		return uuid;
	}

	public long getStartTime() {
		return startTime;
	}

	public AuthenticationStatus getAuthStatus() {
		return authStatus;
	}

	public Locale getLocale() {
		return locale;
	}

	// needed in this class as authentication is post successful clientcontext
	// creation
	public void setAuthStatus(AuthenticationStatus authStatus) {
		this.authStatus = authStatus;
	}

	// needed as warnings can be set throughout the request lifecycle & has to be
	// available to the responsebuilder when building a response
	public void addWarning(Warning warning) {
		if (this.warnings == null) {
			this.warnings = new ArrayList<>();
		}
		this.warnings.add(warning);
	}

	/**
	 * List of warnings
	 * 
	 * @return
	 */
	public List<Warning> getWarnings() {
		return warnings;
	}

	/**
	 * @return the serviceType
	 */
	public ServiceType getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * WEARABLESCompanyId
	 * 
	 * @param companyId
	 */
	public void setWEARABLESCompanyId(String companyId) {
		this.wearablesCompanyId = companyId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setTheater(String theater) {
		this.theater = theater;
	}

	public String getxCorrelationId() {
		return xCorrelationId;
	}

	/**
	 * @return the rawResourceUrl
	 */
	public String getRawResourceUrl() {
		return rawResourceUrl;
	}

	/**
	 * @param rawResourceUrl the rawResourceUrl to set
	 */
	public void setRawResourceUrl(String managementServiceUrl) {
		this.rawResourceUrl = managementServiceUrl;
	}

	public void setApiOperationDefinition(String apiOperation) {
		this.apiOperation = apiOperation;
	}

	public Optional<String> getApiOperationDefinition() {
		return Optional.ofNullable(apiOperation);
	}

	public static class ClientContextBuilder {
		private String appId;

		private AuthToken authToken;

		private AuthenticationStatus authStatus;

		private String appEnvironment;

		private String deviceId;

		private String acceptLanguage;

		private String appVersion;

		private String accept;

		private String contentType;

		private String theater;

		private String userId;

		private String wearablesCompanyId;

		private String rc2Auth;

		// performance related parameters per request
		private String uuid;

		private long startTime;

		private Locale locale;

		private List<Warning> warnings = new ArrayList<>();

		// service type
		private ServiceType serviceType;

		// resource URL for Services
		private String rawResourceUrl;

		private String requestedHost;

		private String apiOperationInvoked;

		private String xCorrelationId;

		public ClientContextBuilder accept(final String accept) {
			this.accept = accept;
			return this;
		}

		public ClientContextBuilder contentType(final String contentType) {
			this.contentType = contentType;
			return this;
		}

		public ClientContextBuilder appVersion(final String appVersion) {
			this.appVersion = appVersion;
			return this;
		}

		public ClientContextBuilder deviceId(final String deviceId) {
			this.deviceId = deviceId;
			return this;
		}

		public ClientContextBuilder appEnvironment(final String appEnvironment) {
			this.appEnvironment = appEnvironment;
			return this;
		}

		public ClientContextBuilder appId(final String appId) {
			this.appId = appId;
			return this;
		}

		public ClientContextBuilder authToken(final AuthToken authToken) {
			this.authToken = authToken;
			return this;
		}

		public ClientContextBuilder authStatus(final AuthenticationStatus authStatus) {
			this.authStatus = authStatus;
			return this;

		}

		public ClientContextBuilder language(final String acceptLanguage) {
			this.acceptLanguage = acceptLanguage;
			return this;
		}

		public ClientContextBuilder theater(final String theater) {
			this.theater = theater;
			return this;
		}

		public ClientContextBuilder uuid(final String uuid) {
			this.uuid = uuid;
			return this;
		}

		public ClientContextBuilder userId(final String userId) {
			this.userId = userId;
			return this;
		}

		public ClientContextBuilder wearablesCompanyId(final String wearablesCompanyId) {
			this.wearablesCompanyId = wearablesCompanyId;
			return this;
		}

		public ClientContextBuilder rc2Auth(final String rc2Auth) {
			this.rc2Auth = rc2Auth;
			return this;
		}

		public ClientContextBuilder startTime(final long startTime) {
			this.startTime = startTime;
			return this;
		}

		public ClientContextBuilder locale(final Locale locale) {
			this.locale = locale;
			return this;
		}

		public ClientContextBuilder warnings(final List<Warning> warnings) {
			this.warnings = warnings;
			return this;
		}

		public ClientContextBuilder serviceType(final ServiceType serviceType) {
			this.serviceType = serviceType;
			return this;
		}

		public ClientContextBuilder rawResourceUrl(final String serviceUrl) {
			this.rawResourceUrl = serviceUrl;
			return this;
		}

		public ClientContextBuilder requestedHost(final String requestedHost) {
			this.requestedHost = requestedHost;
			return this;
		}

		public ClientContextBuilder xCorrelationId(final String xCorrelationId) {
			this.xCorrelationId = xCorrelationId;
			return this;
		}

		public ClientContextBuilder apiOperation(final String apiOperationInvoked) {
			this.apiOperationInvoked = apiOperationInvoked;
			return this;
		}

		public ClientContext build() {
			return new ClientContext(appId, authToken, authStatus, appEnvironment, deviceId, acceptLanguage, appVersion,
					accept, contentType, theater, userId, wearablesCompanyId, rc2Auth, uuid, startTime, locale, warnings,
					serviceType, rawResourceUrl, requestedHost, xCorrelationId, apiOperationInvoked);
		}

	}

}
