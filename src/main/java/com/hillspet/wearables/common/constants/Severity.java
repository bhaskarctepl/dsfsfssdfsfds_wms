package com.hillspet.wearables.common.constants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hillspet.wearables.common.alerting.SeverityHandler;
import com.hillspet.wearables.common.dto.WearablesError;
import com.hillspet.wearables.common.exceptions.InvalidConfigException;
import com.hillspet.wearables.common.exceptions.SeverityInterface;

/**
 * Specifies the severity defined by the application & also defines an action to
 * be taken when the severity happens
 * 
 * @author vvodyaram
 *
 */
public enum Severity implements SeverityInterface {

	ONE(1, "Fatal error, system down condition, high priority that needs an ON-CALL ALERT"),
	TWO(2, "Application error for a particular condition that needs an EMAIL ALERT"),
	THREE(3, "A warning condition that needs to be looked at but does not need an ALERT");

	private Integer severity;

	private String message;

	protected static final Logger LOGGER = LogManager.getLogger(Severity.class);

	private Class<? extends SeverityHandler> severityHandler = SeverityHandler.class;

	private Severity(int severity, String message) {
		this.severity = severity;
		this.message = message;
	}

	private Severity(int severity, Class<? extends SeverityHandler> handler, String message) {
		this.severity = severity;
		this.message = message;
		this.severityHandler = handler;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public Integer getSeverity() {
		return this.severity;
	}

	@Override
	public SeverityHandler getHandler() {
		SeverityHandler handler = null;
		try {
			handler = this.severityHandler.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("An exception has occurred while instantiating SeverityHandler for {}", this.getSeverity(), e);
			throw new InvalidConfigException(
					"An exception has occurred while instantiating SeverityHandler with exception" + e,
					new WearablesError(WearablesErrorCode.WEARABLES_SYSTEM_ERROR));
		}
		return handler;
	}

}
