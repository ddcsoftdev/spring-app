package com.ddcsoftware.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//Adding @Configuration so it auto instantiates
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //This is a variable form application.yml
    //Within application .yml add urls separated by coma
    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    //This is a variable form application.yml
    //Within application .yml add urls separated by coma
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;

    //This allows to add new https origins and avoid CORS error
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //Within mapping the non base url for the api
        /*
        Within Origins the base urls we want to allow like:
            https://localhost
            https://www.ddcsoftware.com
         */
        //We can also determine which methods we want to allow (GET, POST) or all *

        //Allow all mappings
        CorsRegistration corsRegistration = registry.addMapping("/api/**");

        //Add all the allowed origins from allowedOrigins List with lambda or method ref
        //  allowedOrigins.forEach(origin -> corsRegistration.allowedOrigins(origin));
        allowedOrigins.forEach(corsRegistration::allowedOrigins);

        //Add all the allowed methods from allowedMethods List with lambda or method ref
        allowedOrigins.forEach(corsRegistration::allowedMethods);

    }
}
