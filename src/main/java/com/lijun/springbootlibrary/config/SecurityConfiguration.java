package com.lijun.springbootlibrary.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

// TODO S20 11 Create class SecurityConfiguration
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  // TODO S20 12 Create a filterChain Bean
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    // TODO S20 13 Config filter chain

    // Disable Cross Site Request Forgery
    httpSecurity.csrf().disable();

    // Protect endpoints at /api/<type>/secure
    httpSecurity.authorizeRequests(configurer ->
                    configurer
                            .antMatchers("/api/v1/books/secure/**")
                            .authenticated())  // only authenticated user can access the routes match
            .oauth2ResourceServer()  // spring boot act as an oauth2ResourceServer
            .jwt(); // spring boot use jwt

    // Add CORS filters
    httpSecurity.cors();

    // Add content negotiation strategy
    httpSecurity.setSharedObject(ContentNegotiationStrategy.class,
            new HeaderContentNegotiationStrategy());

    // Force a non-empty response body for 401's to make the response friendly
    Okta.configureResourceServer401ResponseBody(httpSecurity);

    return httpSecurity.build();
  }
}







