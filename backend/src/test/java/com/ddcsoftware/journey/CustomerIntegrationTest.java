package com.ddcsoftware.journey;

import com.ddcsoftware.customer.Customer;
import com.ddcsoftware.customer.CustomerRegistrationRequests;
import com.ddcsoftware.customer.CustomerUpdateRequest;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

//This class wants to access directly the API through a web server
//We will not use the CustomerController class directly
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    //uri we can ignore the localhost part, it takes a relative path
    final private static String CUSTOMER_URI = "/api/v1/customers";

    @Test
    void canRegisterCustomer() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        Integer age = faker.random().nextInt(18, 99);
        String gender = faker.random().nextInt(0,1) == 1 ? "male" : "female";

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, age, gender);

        //send post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        //make sure customer is present and ignore field ID as we don't know it
        Customer expectedCustomer = new Customer(
                name, email, age, gender);
        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);

        //get customer by id
        assert allCustomers != null;
        int id = allCustomers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst().
                orElseThrow();

        expectedCustomer.setId(id);

        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectedCustomer);
    }

    @Test
    void canDeleteCustomer() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        Integer age = faker.random().nextInt(18, 99);
        String gender = faker.random().nextInt(0,1) == 1 ? "male" : "female";

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, age, gender);

        //send post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
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

        //delete customer
        webTestClient.delete()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
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
        Integer age = faker.random().nextInt(18, 99);
        String gender = faker.random().nextInt(0,1) == 1 ? "male" : "female";

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, age, gender);

        //send post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
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
        String updateEmail = "%s%s@example.com".formatted(name, UUID.randomUUID());
        Integer updateAge = faker.random().nextInt(18, 99);
        String updateGender = faker.random().nextInt(0,1) == 1 ? "male" : "female";
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                updateName, updateEmail, updateAge, updateGender);

        webTestClient.put()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(update), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
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

    @Test
    void canUpdateCustomerOnlyName() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        Integer age = faker.random().nextInt(18, 99);
        String gender = faker.random().nextInt(0,1) == 1 ? "male" : "female";

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, age, gender);

        //send post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
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
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(update), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
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

    @Test
    void canUpdateCustomerOnlyEmail() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        Integer age = faker.random().nextInt(18, 99);
        String gender = faker.random().nextInt(0,1) == 1 ? "male" : "female";

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, age, gender);

        //send post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
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
        String updateEmail = "%s%s@example.com".formatted(name, UUID.randomUUID());
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                name, updateEmail, age, gender);

        webTestClient.put()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(update), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
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

    @Test
    void canUpdateCustomerOnlyNameAndEmail() {
        //create registration request
        Faker faker = new Faker();
        String name = faker.name().firstName();
        String email = "%s%s@example.com".formatted(name, UUID.randomUUID());
        Integer age = faker.random().nextInt(18, 99);
        String gender = faker.random().nextInt(0,1) == 1 ? "male" : "female";

        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                name, email, age, gender);

        //send post request
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get all customers
        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
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
        String updateEmail = "%s%s@example.com".formatted(name, UUID.randomUUID());
        CustomerUpdateRequest update = new CustomerUpdateRequest(
                updateName, updateEmail, age, gender);

        webTestClient.put()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(update), CustomerRegistrationRequests.class)
                .exchange()
                .expectStatus()
                .isOk();

        //get customer by id and should get 404 as is non-existent anymore
        Customer updatedCustomer = webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Customer.class)
                .returnResult()
                .getResponseBody();

        Customer expected = new Customer(
                id, updateName, updateEmail, age, gender);
        assertThat(updatedCustomer).isEqualTo(expected);
    }
}
