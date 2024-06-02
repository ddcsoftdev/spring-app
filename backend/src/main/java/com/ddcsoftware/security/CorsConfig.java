package com.ddcsoftware.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration class to set up CORS (Cross-Origin Resource Sharing) settings for the application.
 */
@Configuration // Indicates that this class contains configuration settings for the Spring application context
public class CorsConfig {

    /**
     * List of allowed origins for CORS, loaded from the application.yml file.
     * Example: cors.allowed-origins=http://example1.com,http://example2.com
     */
    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    /**
     * List of allowed methods for CORS, loaded from the application.yml file.
     * Example: cors.allowed-methods=GET,POST,PUT,DELETE
     */
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;

    /**
     * List of allowed headers for CORS, loaded from the application.yml file.
     * Example: cors.allowed-headers=Content-Type,Authorization
     */
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;

    /**
     * List of exposed headers for CORS, loaded from the application.yml file.
     * Example: cors.exposed-headers=Location
     */
    @Value("#{'${cors.exposed-headers}'.split(',')}")
    private List<String> exposedHeaders;

    /**
     * Bean definition for configuring CORS settings.
     *
     * @return CorsConfigurationSource object with the configured CORS settings.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Setting the allowed origins for CORS
        configuration.setAllowedOrigins(allowedOrigins);

        // Setting the allowed methods for CORS
        configuration.setAllowedMethods(allowedMethods);

        // Setting the allowed headers for CORS
        configuration.setAllowedHeaders(allowedHeaders);

        // Setting the exposed headers for CORS
        configuration.setExposedHeaders(exposedHeaders);

        // Creating a URL-based CORS configuration source and registering the CORS configuration for all paths (/**)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
