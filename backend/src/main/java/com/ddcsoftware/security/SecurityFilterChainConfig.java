package com.ddcsoftware.security;

import com.ddcsoftware.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    // Constructor to inject the authentication provider and custom filter
    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                     JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable cross-site request forgery as we use no HTML forms
        http.csrf(AbstractHttpConfigurer::disable);

        // Set which requests we are allowing
        http.authorizeHttpRequests(req -> {
            // Allowing POST and GET requests to the specified URL
            req.requestMatchers(
                    // Allowing POST requests to the specified URL without authentication
                    HttpMethod.POST, "/api/v1/customers")
                    .permitAll()
                    // Allowing GET requests to the specified URL without authentication
                    .requestMatchers(HttpMethod.GET, "/api/v1/customers")
                    .permitAll()
                    .anyRequest()
                    .authenticated(); // All other requests must be authenticated
        });

        // Session management configuration
        http.sessionManagement(session -> session
                //JWT is managed with the token, so we don't need Session Policy
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Add the custom authentication provider
        // This is where you can configure your custom authentication logic
        http.authenticationProvider(authenticationProvider);

        // Add the custom filter before the UsernamePasswordAuthenticationFilter
        // This custom filter can handle JWT validation or other authentication mechanisms
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Return configured SecurityFilterChain
        return http.build();
    }
}
