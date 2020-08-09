package com.developervisits.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.PostConstruct;

@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    Oauth2SuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests().antMatchers("/login","/test").permitAll()
               .anyRequest().authenticated()
               .and().formLogin()
               .and().oauth2Login()
               .successHandler(successHandler)
               .and().csrf().disable();

         }

    @PostConstruct
    public void init(){
        System.out.println("config init called");
    }
}
