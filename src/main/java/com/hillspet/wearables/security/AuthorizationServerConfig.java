package com.hillspet.wearables.security;

/*@Configuration
@EnableAuthorizationServer*/
public class AuthorizationServerConfig {
/*public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private static final String CLIEN_ID = "adminClientId";
	private static final String CLIENT_SECRET = "wearablesAdmin";
	private static final String GRANT_TYPE_PASSWORD = "password";
	private static final String AUTHORIZATION_CODE = "authorization_code";
	private static final String REFRESH_TOKEN = "refresh_token";
	private static final String IMPLICIT = "implicit";
	private static final String SCOPE_READ = "read";
	private static final String SCOPE_WRITE = "write";
	private static final String TRUST = "trust";
	private static final String USER_INFO = "user_info";
	private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1 * 30 * 60;
	private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6 * 60 * 60;

	private static final Logger LOGGER = LogManager.getLogger(AuthorizationServerConfig.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	TokenStore tokenStore;

	@Autowired
	private UserApprovalHandler userApprovalHandler;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	private static String REALM = "MY_OAUTH_REALM";

	private static String redirectURLs = "http://localhost:8082/wearables/login/oauth2/code/";

	@Override
	@CrossOrigin
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.inMemory().withClient(CLIEN_ID).secret(passwordEncoder.encode(CLIENT_SECRET))
				.authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
				.scopes(SCOPE_READ, SCOPE_WRITE, TRUST, USER_INFO).autoApprove(true).redirectUris(redirectURLs)
				.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
				.refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").realm(REALM + "/client");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).userApprovalHandler(userApprovalHandler)
				.tokenEnhancer(tokenEnhancer());
		endpoints.tokenStore(tokenStore);
	}

	@EventListener
	public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent) {
		// write custom code here for login success audit
		LOGGER.info("User Oauth2 login success");
		LOGGER.info(authorizedEvent.getAuthentication().getPrincipal());
	}

	@EventListener
	public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent) {
		// write custom code here login failed audit.
		LOGGER.info("User Oauth2 login Failed");
		LOGGER.info(oAuth2AuthenticationFailureEvent.getAuthentication().getPrincipal());
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return (accessToken, authentication) -> {
			CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
			Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
			if (user.getUserId() != null)
				info.put("userId", user.getUserId());
			if (user.getUsername() != null)
				info.put("username", user.getUsername());
			if (user.getFirstName() != null)
				info.put("firstName", user.getFirstName());
			if (user.getLastName() != null)
				info.put("lastName", user.getLastName());
			if (user.getStatus() != null)
				info.put("status", user.getStatus());

			DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
			customAccessToken.setAdditionalInformation(info);

			LOGGER.info(user.getUserId());
			LOGGER.info(user.getUsername());

			return customAccessToken;
		};
	}*/

}
