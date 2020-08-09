package com.developervisits.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.PostConstruct;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
        // check_token URL should be authentication which is actually called from resource server to verify that the token is actually valid
        //tokenKeyAccess , /token_key is used to get public key by resource server on startup which will later validate the gwt signature
        // this avoids calling auth server again from resource server so that less number of calls made to auth server.
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

      /*  clients
                .inMemory()
                .withClient("clientapp").secret(passwordEncoder().encode("123456"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("READ_ONLY_CLIENT")
                .scopes("read_profile_info")
                .resourceIds("oauth2-resource")
                .redirectUris("http://localhost:8080/login")
                .accessTokenValiditySeconds(120)
                .refreshTokenValiditySeconds(240000).autoApprove(true);*/

        clients.inMemory()
                .withClient("clientapp")
                .secret("{noop}123456")
                .authorizedGrantTypes("authorization_code")
                .scopes("user_info")
                .autoApprove(true)
                .redirectUris("http://localhost:8082/user/oauth2/code/");

    }

    // when client uses grant type as password you need to pass your auth manager to auth server so that auth server knows where to look for user name password
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
//                .tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter());
    }


    @PostConstruct
    public  void init() {
        System.out.println(passwordEncoder().encode("123456"));
    }

   /* @Bean
    TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // username for the key is tomcat/changeit
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mykeystore.jks"), "changeit".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("tomcat"));
        return jwtAccessTokenConverter;
    }*/
}
