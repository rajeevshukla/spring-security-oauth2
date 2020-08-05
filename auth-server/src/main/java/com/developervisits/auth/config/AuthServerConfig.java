package com.developervisits.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig  extends AuthorizationServerConfigurerAdapter{

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Bean
	PasswordEncoder  passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		// check_token URL should be authentication which is actually called from resource server to verify that the token is actually valid
		//tokenKeyAccess , /token_key is used to get public key by resource server on startup which will later validate the gwt signature 
		 // this avoids calling auth server again from resource server so that less number of calls made to auth server. 
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients.inMemory().withClient("client")
		.secret(passwordEncoder().encode("secret"))
		.authorizedGrantTypes("password","authorization_code")
		.scopes("user_info")
		.redirectUris("http://localhost:9050/oauth2Success")
		.autoApprove(true)
		.and()
		
		
		.withClient("client2")
		.secret(passwordEncoder().encode("secret"))
		.authorizedGrantTypes("password","authorization_code","client_credentials")
		.scopes("user_info")
		.redirectUris("http://localhost:9050/oauth2Success")
		.autoApprove(true);
	}
	
	// when client uses grant type as password you need to pass your auth manager to auth server so that auth server knows where to look for user name password 
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	  endpoints.authenticationManager(authenticationManager);
	}

}
