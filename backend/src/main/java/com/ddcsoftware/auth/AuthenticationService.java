package com.ddcsoftware.auth;

import com.ddcsoftware.customer.Customer;
import com.ddcsoftware.customer.CustomerDTO;
import com.ddcsoftware.customer.CustomerDTOMapper;
import com.ddcsoftware.jwt.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user authentication.
 */
@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final CustomerDTOMapper customerDTOMapper;
    private final JWTUtil jwtUtil;

    /**
     * Constructor to inject dependencies.
     *
     * @param authenticationManager the authentication manager
     * @param customerDTOMapper     the mapper to convert Customer to CustomerDTO
     * @param jwtUtil               the utility for handling JWT tokens
     */
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 CustomerDTOMapper customerDTOMapper,
                                 JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.customerDTOMapper = customerDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticates the user with the given login request and issues a JWT token.
     *
     * @param request the authentication request containing username and password
     * @return the authentication response containing the customer DTO and JWT token
     */
    public AuthenticationResponse login(AuthenticationRequest request) {
        // Authenticate the user using the authentication manager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        // Retrieve the authenticated customer principal
        Customer principal = (Customer) authentication.getPrincipal();

        // Convert the Customer entity to a CustomerDTO
        CustomerDTO customerDTO = customerDTOMapper.apply(principal);

        // Issue a JWT token for the authenticated user
        String token = jwtUtil.issueToken(customerDTO.username(), customerDTO.roles());

        // Return the authentication response containing the customer DTO and JWT token
        return new AuthenticationResponse(customerDTO, token);
    }
}
