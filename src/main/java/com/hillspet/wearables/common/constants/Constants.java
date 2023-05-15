package com.hillspet.wearables.common.constants;

/**
 * 
 * @author vvodyaram
 *
 */
public final class Constants {

	public static final String WEARABLES_BASE_PACKAGE = "com.hillspet.wearables";
	public static final String WEARABLES_DAO_PACKAGE = "com.hillspet.wearables.dao";
	public static final String WEARABLES_TRANSACTION_MANAGER = "wearablesTransactionManager";
	public static final String DATA_SOURCE = "dataSource";
	public static final String CONTEXT_PATH = "/wearables_mobile_services";
	public static final String APPLICATION_PATH = "/app";
	public static final String SWAGGER_JSON_PATH = "/app/swagger.json";
	public static final String WEARABLES_RESOURCES_PACKAGE = "com.hillspet.wearables.jaxrs.resource";

	public static final int DELETE_STATUS_FLAG = 0;

	public static final int ROLE_ID_FUNCTIONAL_ADMINISTRATOR = 1;
	public static final int ROLE_ID_INVENTORY_ADMINISTRATOR = 2;
	public static final int ROLE_ID_FULFILLMENT_MANAGER = 3;
	public static final int ROLE_ID_SHIPPING_RECEIVING_MANAGER = 4;
	public static final int ROLE_ID_CUSTOMER = 5;

	public static final int APP_INDEX_ZERO = 0;
	public static final int APP_INDEX_ONE = 1;
	public static final int APP_INDEX_TWO = 2;
	public static final int APP_INDEX_THREE = 3;
	public static final int APP_INDEX_FOUR = 4;
	public static final int APP_INDEX_FIVE = 5;
	public static final int APP_INDEX_SIX = 6;
	public static final int APP_INDEX_SEVEN = 7;
	public static final int APP_INDEX_EIGHT = 8;
	public static final int APP_INDEX_NINE = 9;
	public static final int APP_INDEX_TEN = 10;
	public static final int APP_INDEX_ELEVEN = 11;
	public static final int APP_INDEX_TWELVE = 12;
	public static final int APP_INDEX_THIRTEEN = 13;
	public static final int APP_INDEX_FOURTEEN = 14;
	public static final int APP_INDEX_FIFTEEN = 15;

	public static final int STATUS_INACTIVE_FLAG = 0;
	public static final int STATUS_ACTIVE_FLAG = 1;
	public static final int STATUS_RESET_FLAG = 2;

	public static final int NO_OF_CAPS_ALPHA = 1;
	public static final int NO_OF_DIGITS = 1;
	public static final int NO_OF_SPL_CHARS = 1;
	public static final int PASSWORD_MIN_LENGTH = 8;
	public static final int PASSWORD_MAX_LENGTH = 15;

	public static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUM = "0123456789";
	public static final String SPL_CHARS = "!@#$&^";

	// Pattern regex Constants
	public static final String REGEX_CHECK_ONLY_DOT = "[.]";
	public static final String REGEX_PIPE_DELIMETER = "\\|";
	public static final String REGEX_HTML = "\\<.*?\\>";

	public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
	public static final String REGEX_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$&^])[A-Za-z\\d!@#$&^]{8,}$";
	public static final String REGEX_MOBILE = "^[0-9]{10}$";
	public static final String REGEX_PINCODE = "^[0-9]{5}$";
	public static final String REGEX_ALPHANUM_DOT = "[a-zA-Z0-9.]+";
	public static final String REGEX_FEDEX_TRACKING_NO = "\\d{12}";

	public static final String NEW_LINE = System.lineSeparator();
	public static final String PAY_LOAD = "Payload:";

	// Constants for ReloadbaleResourceBundle
	public static final Integer RESOURCE_BUNDLE_CACHE_INTERVAL_SEC = 3600;

	public static final String APP_RESOURCE_BUNDLE_NAME = "classpath:messages";
	public static final String CORE_RESOURCE_BUNDLE_NAME = "classpath:messages-core";

	public static final String MEDIA_TYPE_APPLICATION_JSON_INITIAL_VERSION1 = "application/vnd.wearables.v1.0+json";

	public static final String APP_RESOURCE_PROPERTY_SOURCE = "classpath:appinfo.properties";
	public static final String APP_RESOURCE_CONFIG_PROPERTY_NAME = "info";
	public static final String API_CONFIG = "apiConfig";
	public static final String INTEGRATION_SVCS_CONFIG = "integrationServicesConfig";
	public static final String MOCKDATA_CONFIG = "mockdataconfig";
	public static final String COMMON_CONFIG = "Common";

	public static final String ACTIVITY_DATA_CONFIG = "activities";
	public static final String TIMEOUT_CONFIG_DATA_CONFIG = "network";
	public static final String RETRY_CONFIG_DATA_CONFIG = "retry";
	public static final String SFTP_CONFIG_ROOT = "sftpclient";

	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String OPERATION_NAME_AUTHENTICATE = "authenticate";

	public static final Integer MASKING_LENGTH = 15;
	public static final String ROUTE_ID_DEFAULT_VALUE = "00";

	public static final String USER_ID_UNAUTH = "UNAUTH";

	public static final String GETUSERDETAILS_SEARCH_ON = "UserId";
	public static final String APP_CONFIG_MAP_ORDERTRACKING_DATE_FORMAT = "yyyy-MM-dd HH:mm a";
	public static final String ZONE_DATE_TIME_OFFSET_WITH_ZERO = "yyyy-MM-dd'T'HH:mm:ssxxx";
	public static final String ZONE_DATE_TIME_OFFSET_WITH_Z = "dd/MM/yyyy 'at' hh:mma z";
	public static final String EASTERN_TIME_ZONE_LOCALE = "America/New_York";

	public static final String ERROR_INTEGRATION_SERVICE_CONFIG = "IntegrationService enum is not configured for operation {} which is present in application.yml";
	public static final String ERROR_INTEGRATION_APP_CONFIG = "IntegrationServiceType enum is not configured for app {} which is present in application.yml";
	public static final String ERROR_API_OPERATION_CONFIG = "ApiOperationDefinition enum is not configured for operation {} which is present in application.yml";
	public static final String ERROR_INTEGRATION_SERVICE_URL_CONFIG = "Error in constructing url ";

	/**
	 * The below constants cannot be defined in application.yml as they are needed
	 * too early in spring lifecycle
	 */
	public static final String RESOURCE_URL_PLACEHOLDER_REGEX = "(.*?)(\\{.*\\})(.*)";

	public static final String MATCH_ANYTHING_REGEX = "(.*)";

	public static final String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT";

	public static final String READ_TIMEOUT = "READ_TIMEOUT";

	public static final String SOAP_CONTEXT_PACKAGE = "SOAP_CONTEXT_PACKAGE";
	public static final String SOAP_HEADERS = "SOAP_HEADERS";
	public static final String REST_HEADERS = "REST_HEADERS";
	public static final String APP_VERSION = "APP_VERSION";

	public static final String REQUEST_MAPPING = "request";
	public static final String RESPONSE_MAPPING = "response";
	public static final String SOAP_BASEPATH_KEY = "SOAP";

	public static final String AT_PARAM_USERNAME = "username";

	public static final String AT_PARAM_DISTRICT_CODE = "marketCode";

	public static final Integer NUMBER_ZERO = 0;
	public static final Integer NUMBER_ONE = 1;
	public static final Integer NUMBER_TWO = 2;
	public static final Integer NUMBER_THREE = 3;
	public static final Integer NUMBER_FOUR = 4;
	public static final Integer NUMBER_NINE = 9;
	public static final Integer NUMBER_TWELVE = 12;

	// RC2 related constants
	public static final String BASIC_AUTH_HEADER_VALUE = "Basic ";

	// Constants for Cache regions
	public static final String CACHE_FILE = "ehcache.xml";
	public static final String CACHE_24_HRS = "24HrsCache";
	public static final String CACHE_KEY_GENERATOR = "cacheKeyGenerator";
	public static final String CACHE_MANAGER = "cacheManager";
	public static final String CACHE_MANAGER_FACTORY = "cacheManagerFactory";

	// Quartz configuration information
	public static final String PROPERTIES_FILE = "config.properties";

	public static final String AUTHORIZATION = "Authorization";

	// Constants for Global and theater specific languages

	public static final String GLOBAL_LANG_ID = "global";

	public static final String WEARABLES_SUPPORTED_LOCALES = "core.locales";

	public static final String REPLICATION_CONFIG_DATA_ROOT = "replication";

	public static final String APP_CONFIG_MAP_KEY_FOR_CUSTOMER_ID_LENGTH = "customerIdMaxLength";

	public static final String BUILDING_ID_LENGTH = "buildingIdLength";

	public static final String DISTRICT_ID_MAX_LENGTH = "districtIdMaxLength";

	public static final String DISTRICT_ID_MIN_LENGTH = "districtIdMinLength";

	public static final Integer BUILDING_ID_MAX_LENGTH = 2;

	public static final String APP_NAME_ENV_PROP_NAME = "appName";

	// cypher provider constants
	public static final String ALGORITHM_AES = "AES";
	public static final String AES_PADDING = "AES/ECB/PKCS5Padding";
	public static final String ALGORITHM_RSA = "RSA";
	public static final String RSA_PADDING = "RSA/ECB/PKCS1Padding";

	public static final Integer ACTIVITY_RESULTS_BOX_TO_SCAN_MIN_LENGTH = 1;
	public static final Integer ACTIVITY_RESULTS_DOWNLOAD_BATCH_ID_MIN_LENGTH = 1;
	public static final Integer ACTIVITY_RESULTS_BOX_TO_SCAN_MAX_LENGTH = 99;

	public final static String HANDLER_SUFFIX = "Handler";
	public static final String PROCESSOR_SUFFIX = "Processor";

	public static final String ERROR_DETAIL_BEAN = "ErrorDetailBean";

	public static final int DEFAULT_MAX_RESULTS = 1000;

	public static final int MANDRILL_MAX_ATTEMPTS = 3;
	public static final int MANDRILL_BACKOFF_VALUE = 5000;
	public static final int MANDRILL_BACKOFF_MULTIPLIER = 2;

	public static final int CUSTOMER_TESTIMONIALS_COUNT = 3;

	public static final int APP_SOURCE_CODE_WEBSITE = 1;
	public static final int APP_SOURCE_CODE_ADMIN_PORTAL = 2;

	public static final String GCP_IMAGE_SCORING_PATH = "ImageScoring";

	public static final String GCP_PET_PHOTO_PATH = "PetPhoto";
	public static final String GCP_OBSERVATION_PHOTO_PATH = "ObservationPhoto";
	public static final String GCP_OBSERVATION_VIDEO_PATH = "ObservationVideo";
	public static final String GCP_OBSERVATION_VIDEO_THUMBNAIL_PATH = "ObservationVideoThumbnail";
	
	public static final String GCP_SUPPORT_MATERIAL_FILE_PATH = "SupportMaterial";
	public static final String GCP_SUPPORT_MATERIAL_THUMBNAIL_PATH = "SupportMaterialThumbnail";

	public static final String INVALID_TOKEN = "{\"success\": \"false\",\r\n" + "	\"errors\": [{\r\n"
			+ "		\"Code\": \"ERROR\",\r\n" + "		\"Message\": \"Forbidden! Unauthorized request.\"\r\n"
			+ "	}],\r\n" + "	\"warnings\": \"null\",\r\n" + "	\"responseCode\": \"1\",\r\n"
			+ "	\"responseMessage\": \"ERROR\",\r\n" + "	\"result\": \"null\"\r\n" + "}";
}
