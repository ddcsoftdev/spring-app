package com.ddcsoftware.journey;

import com.ddcsoftware.auth.AuthenticationRequest;
import com.ddcsoftware.auth.AuthenticationResponse;
import com.ddcsoftware.customer.CustomerDTO;
import com.ddcsoftware.customer.CustomerRegistrationRequests;
import com.ddcsoftware.customer.Gender;
import com.ddcsoftware.jwt.JWTUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * Integration test class for testing authentication functionality.
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AuthenticationIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private JWTUtil jwtUtil;

    private static final String AUTHENTICATION_PATH = "/api/v1/auth";
    private static final String CUSTOMER_PATH = "/api/v1/customers";

    /**
     * Test to verify the login functionality.
     */
    @Test
    void canLogin() {
        // Create a fake user using Faker library
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = String.format("%s%s@example.com", name, UUID.randomUUID());
        String password = "password";
        Integer age = faker.random().nextInt(18, 99);
        Gender gender = faker.random().nextInt(0, 1) == 1 ? Gender.MALE : Gender.FEMALE;

        // Create customer registration request
        CustomerRegistrationRequests customerRequest = new CustomerRegistrationRequests(
                name, email, password, age, gender);

        // Create authentication request
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);

        // Try to login before registering the customer, expect unauthorized status
        webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized();

        // Register the customer
        webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(customerRequest), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        // Try to login again after registering the customer, expect OK status
        EntityExchangeResult<AuthenticationResponse> result = webTestClient.post()
                .uri(AUTHENTICATION_PATH + "/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(authenticationRequest), AuthenticationRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                })
                .returnResult();

        // Extract the JWT token from the response headers
        String jwtToken = result.getResponseHeaders()
                .get(HttpHeaders.AUTHORIZATION)
                .get(0);

        // Extract the customer DTO from the response body
        CustomerDTO customerDTO = result.getResponseBody().customerDTO();

        // Verify the token is valid and the customer details match the registration request
        assertThat(jwtUtil.isTokenValid(jwtToken, customerDTO.username())).isTrue();
        assertThat(customerDTO.name()).isEqualTo(name);
        assertThat(customerDTO.email()).isEqualTo(email);
        assertThat(customerDTO.age()).isEqualTo(age);
        assertThat(customerDTO.gender()).isEqualTo(gender);
        assertThat(customerDTO.username()).isEqualTo(email);
    }
}
