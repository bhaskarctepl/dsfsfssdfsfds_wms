package com.hillspet.wearables.common.utils;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

/**
 * Builds Message based on the codes and locale from the messages properties and the request header.
 * 
 * @author vvodyaram
 * 
 * 
 */
@Component
public class I18NResourceUtils  {
  
    private static final Logger LOGGER = LogManager.getLogger(I18NResourceUtils.class);

    @Autowired
    private MessageSource bundle;
    
    /**
     * This method will return error message according to error code provided
     * 
     * @param code
     * @return String
     */
    public String getMessage(String code, Object... arguments) {
        String message = "";
        try {
            Locale localeCode = Locale.ENGLISH; // localeHelper.getLocaleFromClientContext();
            message = bundle.getMessage(code, arguments, localeCode);
        } catch (NoSuchMessageException e) {
            LOGGER.error("Message not available for the given code : {} : {}", code, e.getMessage());
        }
        return message;
    }
    
}
