package com.hillspet.wearables.configuration;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.hillspet.wearables.common.constants.Constants;

/**
 * This Utility will read the resource property/multilingual properties and
 * fetch the error message and codes, this will be used by MessageBuilder
 * utility class to generate the error message.
 * 
 * @author vvodyaram
 */
@Configuration
public class MessageResourceConfiguration {

    /**
     * This method will read properties from the resource using
     * ReloadableResourceBundleMessageSource which will be cached for every 3600 seconds.
     * 
     * @return MessageSource
     * @throws IOException
     */
    @Bean
    public MessageSource messageSource() throws IOException {
		final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(Constants.APP_RESOURCE_BUNDLE_NAME, Constants.CORE_RESOURCE_BUNDLE_NAME);
        messageSource.setCacheSeconds(Constants.RESOURCE_BUNDLE_CACHE_INTERVAL_SEC);
        return messageSource;
    }

}
