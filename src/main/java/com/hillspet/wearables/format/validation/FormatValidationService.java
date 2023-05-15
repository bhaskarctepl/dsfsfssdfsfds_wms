package com.hillspet.wearables.format.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FormatValidationService {

	@Autowired	
	private ApplicationContext appContext;
	
	/**
	 * Convenience method to get instance of FormatValidationHelper class
	 * 
	 * @return
	 */
	public FormatValidationHelper getValidationHelper() {
		return appContext.getBean(FormatValidationHelper.class);
	}
}
