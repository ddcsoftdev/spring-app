package com.ddcsoftware.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //disable cross side request forgery as we use no HTML forms
        http.csrf(AbstractHttpConfigurer::disable);
        //set which request we are allowing
        http.authorizeHttpRequests(req -> {
            // Allowing POST and GET requests to the specified URL
            req.requestMatchers(HttpMethod.POST, "/api/v1/customers")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/customers")
                    .permitAll()
                    .anyRequest()
                    .authenticated(); // All other requests must be authenticated
        });

        //return built http
        return http.build();
    }
}
