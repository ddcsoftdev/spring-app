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
        //set which request are we allowing
        http.authorizeHttpRequests(req -> {
                    //allowing only POST request from this url
                    //You can allow all if you take off first argument
                    req.requestMatchers(HttpMethod.POST,"/api/v1/customers")
                            .permitAll()
                            .anyRequest()
                            .authenticated();//all requests must be authenticated
                });

        //return built http
        return http.build();
    }
}
