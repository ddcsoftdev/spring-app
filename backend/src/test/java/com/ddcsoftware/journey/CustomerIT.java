package com.ddcsoftware.journey;

import com.ddcsoftware.customer.*;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//This class wants to access directly the API through a web server
//We will not use the CustomerController class directly
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT {
    @Autowired
    private WebTestClient webTestClient;

    //uri we can ignore the localhost part, it takes a relative path
    private static final String CUSTOMER_PATH = "/api/v1/customer";

    @Test
    void canRegisterCustomer() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        String password = "password";
        Integer age = faker.random().nextInt(18, 99);
        Gender gender = faker.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, password, age, gender);

        //send post request
        String jwtToken = webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Void.class)
                .getResponseHeaders()//try to get the response header to get token
                .get(HttpHeaders.AUTHORIZATION)//get the authorization
                .get(0);//we currently only have one

        //get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))//add jwtToken
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        //get id
        int id = allCustomers.stream()
                .filter(customer -> customer.email().equals(email))
                .map(CustomerDTO::id)
                .findFirst().
                orElseThrow();

        //make sure customer is present and ignore field ID as we don't know it
        //create dto from customer
        CustomerDTO expectedCustomer = new CustomerDTO(
                id,
                name,
                email,
                gender,
                age,
                List.of("ROLE_USER"),
                email
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))//add jwtToken
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteCustomer() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        String password = "password";
        Integer age = faker.random().nextInt(18, 99);
        Gender gender = faker.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, password, age, gender);

        CustomerRegistrationRequests requestTwo = new CustomerRegistrationRequests(
                name, email + ".us", password, age, gender);

//send post request to create customer 1
        webTestClient.post()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //send post request to create customer 2
        String jwtToken = Objects.requireNonNull(webTestClient.post()
                        .uri(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(requestTwo), CustomerRegistrationRequests.class)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .returnResult(Void.class)//Void allows to put a "placeholder"
                        .getResponseHeaders()//try to get the response header to get token
                        .get(HttpHeaders.AUTHORIZATION))//get the authorization
                .get(0);//we currently only have one

        //get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        //get customer by id
        assert allCustomers != null;
        int id = allCustomers.stream()
                .filter(customer -> customer.email().equals(email))
                .map(CustomerDTO::id)
                .findFirst().
                orElseThrow();

        //delete customer
        webTestClient.delete()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void canUpdateCustomerAllParams() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        String password = "password";
        Integer age = faker.random().nextInt(18, 99);
        Gender gender = faker.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, password, age, gender);

        //send post request
        String jwtToken = Objects.requireNonNull(webTestClient.post()
                        .uri(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(request), CustomerRegistrationRequests.class)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .returnResult(Void.class)
                        .getResponseHeaders()
                        .get(HttpHeaders.AUTHORIZATION))
                .get(0);

        //get all customers
        List<CustomerDTO> allCustomers = webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
                })
                .returnResult()
                .getResponseBody();

        //get customer by id
        assert allCustomers != null;
        int id = allCustomers.stream()
                .filter(customer -> customer.email().equals(email))
                .map(CustomerDTO::id)
                .findFirst().
                orElseThrow();

        //update customer
        String updateName = faker.name().firstName();
        String updateEmail = "%s%s@example.com".formatted(name, UUID.randomUUID());
        Integer updateAge = faker.random().nextInt(18, 99);
        Gender updateGender = faker.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE;
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                updateName, email, updateAge, updateGender);

        webTestClient.put()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(update), CustomerUpdateRequest.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        CustomerDTO updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(CustomerDTO.class)
                .returnResult()
                .getResponseBody();

        assert updatedCustomer != null;
        assertThat(updatedCustomer.name()).isEqualTo(update.name());
        assertThat(updatedCustomer.email()).isEqualTo(update.email());
        assertThat(updatedCustomer.age()).isEqualTo(update.age());
        assertThat(updatedCustomer.gender()).isEqualTo(update.gender());
    }

    @Test
    void canUpdateCustomerOnlyName() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        String password = "password";
        Integer age = faker.random().nextInt(18, 99);
        Gender gender = faker.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE;

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, password, age, gender);

        //send post request
        String jwtToken = Objects.requireNonNull(webTestClient.post()
                        .uri(CUSTOMER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(request), CustomerRegistrationRequests.class)
                        .exchange()
                        .expectStatus()
                        .isOk()
                        .returnResult(Void.class)
                        .getResponseHeaders()
                        .get(HttpHeaders.AUTHORIZATION))
                .get(0);

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        //get customer by id
        assert allCustomers != null;
        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().
                orElseThrow();

        //update customer
        String updateName = faker.name().firstName();
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                updateName, email, age, gender);

        webTestClient.put()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(update), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_PATH + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtToken))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        assert updatedCustomer != null;
        assertThat(updatedCustomer.getName()).isEqualTo(update.name());
        assertThat(updatedCustomer.getEmail()).isEqualTo(update.email());
        assertThat(updatedCustomer.getAge()).isEqualTo(update.age());
        assertThat(updatedCustomer.getGender()).isEqualTo(update.gender());
    }
}
