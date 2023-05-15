package com.hillspet.wearables.common.utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import com.hillspet.wearables.common.constants.Constants;
import com.hillspet.wearables.common.constants.EnumInterface;
import com.hillspet.wearables.common.constants.Flags;
import com.hillspet.wearables.common.constants.StringLiterals;
import com.hillspet.wearables.common.constants.WearablesErrorCode;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.exceptions.InvalidConfigException;
import com.hillspet.wearables.common.validation.ValidationResult;
import com.hillspet.wearables.helpers.ClientContextService;

/**
 * Contains application level utility methods
 * 
 * @author vvodyaram
 *
 */
public class WearablesUtils {
	private static final Logger LOGGER = LogManager.getLogger(WearablesUtils.class);

	/**
	 * Utility method that builds a fixed length string for the given character.
	 * 
	 * @param length    - The desired length of the string
	 * @param character - The character that will be repeated in the string for the
	 *                  given length
	 * @return - A string with the desired sequence of character(s)
	 */
	public static final String buildLine(Integer length, String character) {
		StringBuilder builder = new StringBuilder();
		// Loop them and append given character to StringBuilder
		IntStream.range(0, length).forEach(x -> builder.append(character));
		return builder.toString();
	}

	/**
	 * Helper method that invokes a method on the given object using reflection &
	 * casts the output to the correct type
	 * 
	 * @param methodName   - Name of the method that needs to be invoked
	 * @param targetObject - The given object on which the method will be called
	 * @return Object - the value returned from the method invocation
	 */
	public static Object invokeMethod(String methodName, Object targetObject) {
		Object result = null;
		try {
			Method method = targetObject.getClass().getMethod(methodName);
			result = method.invoke(targetObject);
		} catch (Exception e) {
			LOGGER.error("The method {} is not found on {}", methodName, targetObject.getClass());
		}
		return result;
	}

	/**
	 * Generates random number using UUID
	 * 
	 * @return UUID
	 */

	public static UUID generateRandomNumber() {
		return UUID.randomUUID();
	}

	/**
	 * Helper method to extract the resource path of the API also called as the
	 * service name from the endpoint address
	 *
	 * Path will not include host, port and context path but it will include
	 * pathParams if the URL is
	 * http://localhost:8585/wearables/services/user/details/testuser path would
	 * contain /user/details/testuser
	 * 
	 * @param uriInfo
	 * @return
	 */
	public static String getResourceUrl(@NotNull UriInfo uriInfo) {
		String path = uriInfo.getPath();
		return path;
	}

	/**
	 * Checks input data is matching with given Pattern object or not.
	 * 
	 * @param pattern - Regex pattern to validate input data
	 * @param data    - input data to validate
	 * @return Boolean - true, If input data matches regular expression. - false, If
	 *         input data doesn't match with regular expression.
	 */

	public static Boolean isValidString(Pattern pattern, String data) {
		if (data != null) {
			return pattern.matcher(data).matches();
		}
		return false;
	}

	/**
	 * Checks the input string is matching with any pattern
	 * 
	 * @param data
	 * @param patterns
	 * @return
	 */

	public static Boolean isStringMatches(String data, List<Pattern> patterns) {
		Optional<Pattern> foundPattern = patterns.stream().filter(ptn -> isValidString(ptn, data)).findFirst();
		return foundPattern.isPresent();
	}

	/**
	 * Checks the input date string is matching with given date format
	 * 
	 * @param dateString
	 * @param dateFormat
	 * @return
	 */
	public static Boolean isStringMatchesWithDate(final String dateString, final List<SimpleDateFormat> dateFormat) {
		Optional<SimpleDateFormat> foundFormat = dateFormat.stream().filter(format -> {
			Optional<Date> dateOpt = getDateObj(dateString, format);
			return dateOpt.isPresent();
		}).findFirst();
		return foundFormat.isPresent();
	}

	public static List<String> toList(String input, String delimiter) {
		List<String> elementList = new ArrayList<String>();
		if (StringUtils.isNotBlank(input)) {
			StringTokenizer tokenizer = new StringTokenizer(input, delimiter);
			while (tokenizer.hasMoreElements()) {
				elementList.add(StringUtils.trim(tokenizer.nextToken()));
			}
		}
		return elementList;
	}

	/**
	 * Return the date matches with given formats
	 * 
	 * @param dateString
	 * @param sdf
	 * @return
	 */
	public static Optional<Date> getDateObj(final String dateString, final SimpleDateFormat sdf) {
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(sdf.toPattern());
		Date date = null;
		try {
			// using LocalDate; validate that the date is matches with given format or not
			LocalDate ld = LocalDate.parse(dateString, fomatter);
			ld.format(fomatter);
			/*
			 * using SimpleDateFormat; validate that the date is a calendar date or not. ex:
			 * Leap year, months with 30/31 days
			 */
			sdf.setLenient(false);
			try {
				date = sdf.parse(dateString);
				LOGGER.info("Valid date {} with date format {} passed ", dateString, sdf.toPattern());
				return Optional.of(date);
			} catch (ParseException e) {
				LOGGER.error("Error while parsing date = {}", e, e.getMessage());
				return Optional.empty();
			}
		} catch (DateTimeParseException e) {
			LOGGER.error("Error while parsing date = {}", e, e.getMessage());
			return Optional.empty();
		}
	}

	/**
	 * Checks the input element exist in the input list or not
	 * 
	 * @param element   - String
	 * @param inputList - List
	 * @return True - If element exist (With case insensitive) in inputList, else
	 *         returns False
	 */
	public static Boolean isListContainsIgnoreCase(String element, List<String> inputList) {
		Boolean exist = false;
		if (inputList != null) {
			if (inputList.stream().filter(data -> data.equalsIgnoreCase(element)).count() > 0) {
				exist = true;
			}
		}
		return exist;
	}

	/**
	 * Get the end name from given url
	 * 
	 * @param address
	 * @return
	 */
	public static String getServiceNameFromUrl(String address) {
		String serviceName = "";
		if (StringUtils.isNotBlank(address)) {
			serviceName = StringUtils.substring(address,
					address.lastIndexOf(StringLiterals.FORWARD_SLASH.getCode()) + 1, address.length()).trim();
		}
		return serviceName;
	}

	/**
	 * Returns all the web service resources annotated with given annotation.
	 * 
	 * @return
	 */
	public static List<Object> getBeansOfAnnotationType(ApplicationContext applicationContext,
			Class<? extends Annotation> annotationType) {
		// Print list of beans here
		List<Object> serviceBeansList = applicationContext.getBeansWithAnnotation(annotationType).values().stream()
				.collect(Collectors.toList());
		LOGGER.debug("Spring components annotatated with {} : {}", annotationType, serviceBeansList);
		return serviceBeansList;
	}

	/**
	 * Returns all the web service resources annotated with given annotation.
	 * 
	 * @return
	 */
	public static <T extends Annotation> T getAnnotation(Class<?> srcClass, Class<T> annotationType) {
		T annotation = srcClass.getDeclaredAnnotation(annotationType);
		return annotation;
	}

	public static void cleanupClientContext(ApplicationContext applicationContext) {
		ConfigurableApplicationContext context = (ConfigurableApplicationContext) applicationContext;
		ClientContextService serviceImpl = context.getBean(ClientContextService.class);
		context.getBeanFactory().destroyBean(serviceImpl);

		LOGGER.info("{} bean removed from application context", serviceImpl);
	}

	/**
	 * 
	 * @param inputList - comma separated list
	 * @param delimiter - element separator
	 * @return formatted list
	 */
	public static String createDelimitedString(List<String> inputList, String delimiter) {
		return inputList.stream().collect(Collectors.joining(delimiter));
	}

	/**
	 * 
	 * @param index    - position of the desired parameter in DEVICE_ID
	 * @param deviceId - Request header device id
	 * @return desired parameter value
	 */
	public static String getDeviceIdParam(Integer index, String deviceId) {
		String[] deviceId_split = deviceId.split(Constants.REGEX_PIPE_DELIMETER);
		String paramValue = (deviceId_split.length > index) ? deviceId_split[index] : StringUtils.EMPTY;
		return paramValue;
	}

	/**
	 * @throws URISyntaxException
	 * 
	 */
	public static String getUrl(String protocol, String host, Integer port, String basePath) {
		try {
			return new URI(protocol, null, host, port, basePath, null, null).toString();
		} catch (Exception e) {
			LOGGER.error("Error encountered while getting the url ", e);
			StringBuilder builder = new StringBuilder();
			builder.append("protocol=").append(protocol).append(", host=").append(host).append(", port=").append(port)
					.append(", basePath=").append(basePath).append("]");
			throw getInvalidConfigException(Constants.ERROR_INTEGRATION_SERVICE_URL_CONFIG, builder.toString());
		}
	}

	public static InvalidConfigException getInvalidConfigException(String errorMessage, Object... values) {
		return new InvalidConfigException(errorMessage, Status.INTERNAL_SERVER_ERROR,
				new WearablesError(WearablesErrorCode.WEARABLES_INTERNAL_SRVR_ERROR, values));

	}

	/**
	 * A helper method to determine if 2 resources have the same path
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 */
	public static boolean isResourcesMatch(String arg0, String arg1) {
		boolean isMatched = false;
		try {
			isMatched = Paths.get(arg0).equals(Paths.get(arg1));
		} catch (InvalidPathException e) {
			LOGGER.error("Exception occurred {} for paths arg0 = {}, arg1 = {}", e, arg0, arg1);
		}
		return isMatched;
	}

	/**
	 * @param args
	 * @return
	 */
	public static Boolean isAllBlank(String... args) {
		// Handle NPE as @NotNull won't work at the varargs level
		if (args != null) {
			for (String s : args) {
				if (StringUtils.isNotBlank(s))
					return false;
			}
		}
		return true;
	}

	/**
	 * Check all values are null or not. If any value is not null, then it returns
	 * false.
	 * 
	 * @param args
	 * @return
	 */
	public static Boolean isAllNull(Object... args) {
		// Handle NPE as @NotNull won't work at the varargs level
		if (args != null) {
			for (Object s : args) {
				if (s != null)
					return false;
			}
		}
		return true;
	}

	/**
	 * Check any value is null or not. If any value is not null, then it returns
	 * true.
	 * 
	 * @param args
	 * @return
	 */
	public static Boolean isAnyNotNull(Object... args) {
		return !isAllNull(args);
	}

	public static Boolean isAllBlank(Collection<String> collections) {
		// Collection is not empty then check for each
		if (CollectionUtils.isNotEmpty(collections)) {
			for (String c : collections) {
				if (StringUtils.isNotBlank(c))
					return false;
			}
		}
		return true;
	}

	public static ValidationResult addError(ValidationResult valResult, ErrorDetailInterface errorCode,
			Object... errorData) {
		valResult.addError(new WearablesError(errorCode, errorData));
		valResult.setStatusOnError(errorCode.getStatus());
		return valResult;
	}

	/**
	 * This can be removed and moved code to {@link AbstractWearablesDozerMapper}
	 * 
	 * @param apiOperationName
	 * @return
	 */

	public static String getRequestBeanMappingNameForApiOperation(final String apiOperationName) {
		return apiOperationName + StringLiterals.UNDERSCORE.getCode() + Constants.REQUEST_MAPPING;
	}

	/**
	 * A convenient method to get the api operation name that can be used as
	 * response bean mapping id
	 * 
	 * This can be removed and moved code to {@link AbstractWearablesDozerMapper}
	 * 
	 * @return
	 */

	public static String getResponseBeanMappingNameForApiOperation(final String apiOperationName) {
		return apiOperationName + StringLiterals.UNDERSCORE.getCode() + Constants.RESPONSE_MAPPING;
	}

	public static String generateBase64BasicAuth(String id, String secret) {
		if (!StringUtils.isAnyBlank(id, secret)) {
			final String basicAuthHeader = id + StringLiterals.COLON.getCode() + secret;
			final String encodedBasicAuthHeader = WearablesUtils.encode(basicAuthHeader);
			return Constants.BASIC_AUTH_HEADER_VALUE + encodedBasicAuthHeader;
		}
		return null;
	}

	/**
	 * Encodes the given string using Base64 encoder.
	 * 
	 */
	public static String encode(String arg) {
		if (StringUtils.isNotBlank(arg)) {
			return Base64.getEncoder().encodeToString(arg.getBytes(Charset.forName(Constants.DEFAULT_CHARSET)));
		}
		return arg;
	}

	/**
	 * Decodes the given encoded string using Base64 decoder.
	 * 
	 */
	public static String decode(String encodedString) {
		if (StringUtils.isNotBlank(encodedString)) {
			return new String(Base64.getDecoder().decode(encodedString), Charset.forName(Constants.DEFAULT_CHARSET));
		}
		return encodedString;
	}

	/**
	 * This utility method will check the passed args value is true or false only,
	 * any value apart from that should be false. Additionally in some cases for api
	 * operations, some parameters can be optional and null or blank can be
	 * acceptable.
	 * 
	 * @param arg0
	 * @return
	 */
	public static boolean isBooleanVal(String inputBooelanString) {
		return StringUtils.isBlank(inputBooelanString)
				|| isStringMatchesWithAnyIgnoreCase(inputBooelanString, Flags.TRUE.getCode(), Flags.FALSE.getCode());
	};

	public static Boolean isStringMatchesWithAnyIgnoreCase(String inputString, String... availableValues) {
		return Arrays.stream(availableValues).anyMatch(availableValue -> availableValue.equalsIgnoreCase(inputString));
	}

	/**
	 * Loads the quartz properties from given file with location.
	 * 
	 * @param fileName
	 * @return
	 */
	public static Properties loadProperties(String fileName) {
		Properties prop = new Properties();
		try {
			ClassLoader classLoader = new WearablesUtils().getClass().getClassLoader();
			prop.load(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			LOGGER.error("Exception while loading properties", e);
		}
		LOGGER.debug("Properties = {}", prop);
		return prop;
	}

	/**
	 * Returns batch id for assets call
	 * 
	 * @return
	 */
	public static String generateBatchId() {
		return Integer.toString(Math.abs((int) (System.nanoTime())));
	}

	/**
	 * Returns true if enum passed is part of list of enums
	 * 
	 * @param inputEnum
	 * @param inputEnumArray
	 * @return
	 */
	@SafeVarargs
	public static boolean isEnumMatchesWithAnyEnumerations(EnumInterface<String> inputEnum,
			EnumInterface<String>... inputEnumArray) {
		boolean flag = false;
		if (inputEnum != null && ArrayUtils.isNotEmpty(inputEnumArray)) {
			flag = Arrays.stream(inputEnumArray).anyMatch(input -> input.equals(inputEnum));
		}
		return flag;
	}

	/**
	 * Common utility method to encode a string
	 * 
	 * @param plantext
	 * @return
	 */
	public static String encodeText(String plantext) {
		return Base64.getEncoder().encodeToString(plantext.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * common utility method to decode the string
	 * 
	 * @param encodedText
	 * @return
	 */
	public static String decodedText(String encodedText) {
		return new String(Base64.getDecoder().decode(encodedText), StandardCharsets.UTF_8);
	}

	public static String abbreviate(String str, int maxWidth) {
		return StringUtils.abbreviate(str, maxWidth);
	}

	public static Integer getIntValue(Map<String, Object> map, String key) {
		return Integer.valueOf(String.valueOf(map.get(key)));
	}

	/**
	 * common utility method to get the default if given String is null
	 * 
	 * @param val
	 * @param defaultVal
	 * @return
	 */
	public static String getOrDefault(String val, String defaultVal) {
		return StringUtils.isBlank(val) ? defaultVal : val;
	}

	/**
	 * common utility method to get the default if given object is null
	 * 
	 * @param val
	 * @param defaultVal
	 * @return
	 */
	public static <T> T getOrDefault(T val, T defaultVal) {
		return val == null ? defaultVal : val;
	}

	/**
	 * common utility method to check any object is null in given var args
	 * 
	 * @param args
	 * @return
	 */
	public static Boolean isAnyNull(Object... args) {
		// Handle NPE as @NotNull won't work at the varargs level
		if (args != null) {
			for (Object s : args) {
				if (s == null)
					return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * common utility method to check all list is null or empty in given var args
	 * 
	 * @param args
	 * @return
	 */
	public static Boolean isAllListEmpty(List... args) {
		// Handle NPE as @NotNull won't work at the varargs level
		if (args != null) {
			for (List s : args) {
				if (CollectionUtils.isNotEmpty(s))
					return false;
			}
		}
		return true;
	}

	public static List<List<Long>> createBatchFromList(List<Long> procHistIds, int n) {
		List<List<Long>> al = new ArrayList<>();
		while (CollectionUtils.isNotEmpty(procHistIds)) {
			if (procHistIds.size() >= n) {
				List<Long> lis = procHistIds.subList(0, n);
				al.add(lis);
				procHistIds = (List<Long>) CollectionUtils.removeAll(procHistIds, lis);
			} else {
				al.add(procHistIds);
				procHistIds = new ArrayList<>();
			}
		}
		return al;
	}

	public static void addError(ValidationResult varRes, Status status, ErrorDetailInterface errorCode,
			Object... values) {
		varRes.addError(new WearablesError(errorCode, values));
		varRes.setStatusOnError(status.getStatusCode());
	}

	public static String generatePassword() {
		int noOfCAPSAlpha = Constants.NO_OF_CAPS_ALPHA;
		int noOfDigits = Constants.NO_OF_DIGITS;
		int noOfSplChars = Constants.NO_OF_SPL_CHARS;
		if ((noOfCAPSAlpha + noOfDigits + noOfSplChars) > Constants.PASSWORD_MIN_LENGTH)
			throw new IllegalArgumentException(
					"Min. Length should be atleast sum of (CAPS, DIGITS, SPL CHARS) Length!");
		Random rnd = new Random();
		int len = rnd.nextInt(Constants.PASSWORD_MAX_LENGTH - Constants.PASSWORD_MIN_LENGTH + 1)
				+ Constants.PASSWORD_MIN_LENGTH;
		char[] pswd = new char[len];
		int index = 0;
		for (int i = 0; i < noOfCAPSAlpha; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = Constants.ALPHA_CAPS.charAt(rnd.nextInt(Constants.ALPHA_CAPS.length()));
		}
		for (int i = 0; i < noOfDigits; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = Constants.NUM.charAt(rnd.nextInt(Constants.NUM.length()));
		}
		for (int i = 0; i < noOfSplChars; i++) {
			index = getNextIndex(rnd, len, pswd);
			pswd[index] = Constants.SPL_CHARS.charAt(rnd.nextInt(Constants.SPL_CHARS.length()));
		}
		for (int i = 0; i < len; i++) {
			if (pswd[i] == 0) {
				pswd[i] = Constants.ALPHA.charAt(rnd.nextInt(Constants.ALPHA.length()));
			}
		}
		return new String(pswd);
	}

	private static int getNextIndex(Random rnd, int len, char[] pswd) {
		int index = rnd.nextInt(len);
		while (pswd[index = rnd.nextInt(len)] != 0)
			;
		return index;
	}

}
