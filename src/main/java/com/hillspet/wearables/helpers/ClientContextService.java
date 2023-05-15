package com.hillspet.wearables.helpers;

import com.hillspet.wearables.common.dto.ClientContext;

/**
 * The purpose of this interface is to be able to use ScopedProxyMode.INTERFACES when creating a request scope bean for
 * holding the ClientContext object.
 * 
 * The other default scope would be ScopedProxyMode.TARGET_CLASS which would need CGLIB in our classpath. So basically to 
 * avoid dependency on CGLIB this interface was created.
 * 
 * Refer {@link https://docs.spring.io/spring/docs/3.0.0.M4/reference/html/ch03s05.html#beans-factory-scopes-other-injection-proxies}
 *  *  
 * 
 * @author hrangwal
 *
 */
public interface ClientContextService {
	
	public void setClientContext(ClientContext clientContext);

	public ClientContext getClientContext();
}
