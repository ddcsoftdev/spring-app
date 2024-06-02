package com.ddcsoftware.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication-related HTTP requests.
 */
@RestController
@RequestMapping("api/v1/auth") // Base URL for authentication endpoints
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Constructor to inject the authentication service.
     *
     * @param authenticationService the service handling authentication logic
     */
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Endpoint for user login.
     *
     * @param request the authentication request containing username and password
     * @return a ResponseEntity containing the authentication response with a JWT token
     */
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        // Authenticate the user and get the response containing the JWT token
        AuthenticationResponse response = authenticationService.login(request);

        // Return the response entity with the JWT token in the headers
        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, response.token())
                .body(response);
    }
}
