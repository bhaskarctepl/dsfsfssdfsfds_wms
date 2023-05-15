package com.hillspet.wearables.common.constants;

/**
 * 
 * @author vvodyaram
 *
 */
public interface WarningCodeInterface {
	
	/**
	 * Application specific error codes i.e WEARABLES_APP_001
	 * @return
	 */
	public String getWarningCode();

	/**
	 * A user friendly error key representing the error scenario 
	 * @return
	 */
	public MessageKeyInterface getKey();
	
}
