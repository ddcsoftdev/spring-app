package com.ddcsoftware.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    //this encodes the password into a HASH
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    /*
    This just returns Spring's AuthenticationManager from 
    Spring's AuthenticationConfiguration class
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        //return the current AuthenticationManager
        return configuration.getAuthenticationManager();
    }

    /*
    This sends the user details and the encoded password into 
    Spring's DaoAuthenticationProvider class and returns an instance of it
    with the loaded data
     */
    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder){
        //Creating new instance of DaoAuthenticationProvider
        DaoAuthenticationProvider daoAuthenticationProvider =
                new DaoAuthenticationProvider();

        //set the User Details and hashed password
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }
}
