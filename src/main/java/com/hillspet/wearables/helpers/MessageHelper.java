package com.hillspet.wearables.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hillspet.wearables.common.constants.WarningCodeInterface;
import com.hillspet.wearables.common.dto.Warning;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.ErrorDetailInterface;
import com.hillspet.wearables.common.response.Message;
import com.hillspet.wearables.common.utils.I18NResourceUtils;

/**
 * A helper class to construct the {@code Message} API from the {@code Error} and {@code Warning}
 * API's.
 * 
 * @author hrangwal
 *
 */
@Component
public class MessageHelper {

    @Autowired
    private I18NResourceUtils i18NResourcceUtils;

    @Value("${wearables.maxPrintableCharLength}")
    private int maxPrintableCharLength;

    private Function<Warning, Message> warningFunction = this::getLocalizedMessage;

    private Function<WearablesError, Message> errorFunction = this::getLocalizedMessage;

    /**
     * Helper method to create the localized Message API from an Error object
     * 
     * @param error
     * @return
     */
    public Message buildErrorMessage(WearablesError error) {
        return getLocalizedMessage(error);
    }

    /**
     * Helper method to create the localized Message API from a Warning object
     * 
     * @param warning
     * @return
     */
    public Message buildWarningMessage(Warning warning) {
        return getLocalizedMessage(warning);
    }


    /**
     * Helper method to create a list of localized Message API from the Error's object
     * 
     * @param errors
     * @return
     */
    public List<Message> buildErrorMessages(List<WearablesError> errors) {
        List<Message> msgList = errors.stream().map(errorFunction).collect(Collectors.toList());

        return msgList;

    }

    /**
     * Helper method to create a list of localized Message API from the Warning's object
     * 
     * @param warnings
     * @return
     */
    public List<Message> buildWarningMessages(List<Warning> warnings) {
        List<Message> msgList = warnings.stream().map(warningFunction).collect(Collectors.toList());

        return msgList;

    }

    private Message getLocalizedMessage(Warning warning) {
        final WarningCodeInterface warningCode = warning.getWarningDetail();
        return getLocalizedMessage(warningCode.getWarningCode(), warningCode.getKey().getValue(), warning.getValues());
    }

    private Message getLocalizedMessage(WearablesError error) {
        final ErrorDetailInterface errorCode = error.getErrorDetail();
        return getLocalizedMessage(errorCode.getErrorCode(), errorCode.getKey().getValue(), error.getValues());
    }

    /**
     * This method will add ellipses if the length of value in values object is more than
     * maxPrtinableCharLength value
     * 
     * @param code - message code
     * @param key - message key
     * @param values - list of values to be added to the message
     * @return
     */
    private Message getLocalizedMessage(final String code, final String key, final List<Object> values) {

        List<Object> abbreValues = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(values)) {
            abbreValues.addAll(values.stream()
                    .map(message -> StringUtils.abbreviate(String.valueOf(message), maxPrintableCharLength))
                    .collect(Collectors.toList()));
        }
        final String localizedMessage = i18NResourcceUtils.getMessage(code, abbreValues.toArray());
        Message msg = new Message(code, localizedMessage, key);
        return msg;
    }

}
