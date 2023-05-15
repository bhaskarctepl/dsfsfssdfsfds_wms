package com.hillspet.wearables.helpers;

import com.hillspet.wearables.common.dto.ClientContext;

/**
 * A wrapper to hold the ClientContext Note this is not & should not have any
 * spring annotations at class level
 * 
 * 
 * @author vvodyaram
 *
 */
public class ClientContextServiceImpl implements ClientContextService {

	private ClientContext clientContext;

	public ClientContext getClientContext() {
		return clientContext;
	}

	/**
	 * This method should be called from the JAXRS Filter to set the ClientContext
	 * The ClientContext will be registered at request scope which means each
	 * request will have a unique ClientContext object. Refer
	 * {@link ClientContextConfig}
	 * 
	 * 
	 * @param clientContext
	 */
	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}

}
