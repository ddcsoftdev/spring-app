package com.ddcsoftware.security;

import com.ddcsoftware.jwt.JWTAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for setting up the Spring Security filter chain.
 */
@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    // Dependency injections
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    /**
     * Constructor for injecting dependencies.
     *
     * @param authenticationProvider the custom authentication provider
     * @param jwtAuthenticationFilter the JWT authentication filter
     * @param authenticationEntryPoint the custom authentication entry point
     */
    public SecurityFilterChainConfig(AuthenticationProvider authenticationProvider,
                                     JWTAuthenticationFilter jwtAuthenticationFilter,
                                     AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * Bean definition for configuring the security filter chain.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Disable Cross-Site Request Forgery (CSRF) protection
        // This is safe to disable as we are not using server-side rendered forms
        http.csrf(AbstractHttpConfigurer::disable);

        // Enable Cross-Origin Resource Sharing (CORS) with default settings
        http.cors(Customizer.withDefaults());

        // Configure authorization rules
        http.authorizeHttpRequests(req -> {
            // Allow unauthenticated POST requests to the specified endpoint
            req.requestMatchers(HttpMethod.POST, "/api/v1/customers")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/auth/login")
                    .permitAll()
                    // Any other request must be authenticated
                    .anyRequest()
                    .authenticated();
        });

        // Set session management to stateless as we are using JWT for session management
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Add the custom authentication provider
        // This allows the use of custom authentication logic
        http.authenticationProvider(authenticationProvider);

        // Add the JWT authentication filter before the default UsernamePasswordAuthenticationFilter
        // This allows us to handle JWT validation
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Configure exception handling to use the custom authentication entry point
        // This ensures proper response codes and messages for authentication errors
        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(authenticationEntryPoint));

        // Return the configured SecurityFilterChain
        return http.build();
    }
}
