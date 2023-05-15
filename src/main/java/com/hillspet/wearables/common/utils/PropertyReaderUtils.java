package com.hillspet.wearables.common.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Contains dataaccess level utility methods
 * 
 * @author vvodyaram
 *
 */
public class PropertyReaderUtils {
	private static final Logger LOGGER = LogManager.getLogger(PropertyReaderUtils.class);

	/**
	 * Loads the hibernate properties from given file with location.
	 * 
	 * @param fileName
	 * @return Properties
	 */
	public static Properties loadProperties(String fileName) {
		Properties prop = new Properties();
		try {
			ClassLoader classLoader = new PropertyReaderUtils().getClass().getClassLoader();
			prop.load(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			LOGGER.error("Exception while loading Hibernate properties", e);
		}
		LOGGER.debug("Hibernate properties = {}", prop);
		return prop;
	}

}
