package com.ddcsoftware.customer;

import com.ddcsoftware.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//DataJpaTest just loads the Beans needed to run JPA
@DataJpaTest
//AutoConfigureTestDatabase: This disables te embedded database (the one in Docker in this case)
//We want to connect one that we create for testing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestcontainers {

    //@Autowired allows to inject beans without adding them to a constructor of this class
    @Autowired
    private CustomerRepository underTest;

    @BeforeEach
    void setUp() {
        //In Main function we always add a random customer when running the program
        //this allows us to have a "fresh" database when testing
        underTest.deleteAll();
    }

    @Test
    void existsCustomerByEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = createRandomCustomer(email);
        underTest.save(customer);

        var actual = underTest.existsCustomerByEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByEmailWhenNonPresent() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = createRandomCustomer(email);

        var actual = underTest.existsCustomerByEmail(email);
        assertThat(actual).isFalse();
    }

    @Test
    void existsCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = createRandomCustomer(email);
        underTest.save(customer);

        //Find used id
        int id = underTest.findAll()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        var actual = underTest.existsCustomerById(id);
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerByIdWhenNonPresent() {
        //Find used id
        int id = -1;

        var actual = underTest.existsCustomerById(id);
        assertThat(actual).isFalse();
    }

    private Customer createRandomCustomer(){
        return new Customer(
                FAKER.name().fullName(),
                //Make this always unique to avoid errors
                FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID(),
                FAKER.random().nextInt(18, 99),
                FAKER.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE);
    }

    private Customer createRandomCustomer(String email){
        return new Customer(
                FAKER.name().fullName(),
                email,
                FAKER.random().nextInt(18, 99),
                FAKER.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE);
    }
}