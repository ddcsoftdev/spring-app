package com.ddcsoftware.customer;

import com.ddcsoftware.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

//This class testes CustomerJDBCDataAccessService
//Since AbstractTestcontainers has no @SpringBootTest we need to set up dependencies manually
//As we are not using Application Context to make the test faster (We don't need all the Beans)

//We could use @DataJDBCTest to load Beans for JDBC. But by doing it manually we load fewer beans
class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

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

    @BeforeEach
    void setUp() {
        //Needs Jdbc Template so it can connect to DB
        //the getJdbcTemplate() gets a JdbcTemplate from Abstract class
        //The JdbcTemplate comes preloaded with the DataSource of our DB Container(Check method)
        underTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper);
    }

    @Test
    void selectAllCustomers() {
        //Given
        Customer customer = createRandomCustomer();
        underTest.insertCustomer(customer);

        //When
        List<Customer> actual = underTest.selectAllCustomers();
        //Then
        assertThat(actual).isNotEmpty();
    }

    @Test
    void selectCustomerById() {

        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = createRandomCustomer(email);
        underTest.insertCustomer(customer);

        //Find valid ID since it's generated randomly
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //When
        Optional<Customer> actual = underTest.selectCustomerById(id);

        //Then check if all values are okay
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getEmail()).isEqualTo(customer.getEmail());
            assertThat(c.getAge()).isEqualTo(customer.getAge());
            assertThat(c.getGender()).isEqualTo(customer.getGender());
        });
    }

    @Test
    void returnEmptyWhenSelectCustomerById() {
        int id = -1;
        var actual = underTest.selectCustomerById(id);
        assertThat(actual).isEmpty();
    }

    @Test
    void existsCustomerWithEmail() {
        //Create and add customer
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = createRandomCustomer(email);
        underTest.insertCustomer(customer);

        boolean actual = underTest.existsCustomerWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithId() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = createRandomCustomer(email);
        underTest.insertCustomer(customer);

        //Find used id
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        boolean actual = underTest.existsCustomerWithId(id);
        assertThat(actual).isTrue();

    }

    @Test
    void insertCustomer() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer  = createRandomCustomer(email);
        underTest.insertCustomer(customer);

        boolean actual = underTest.existsCustomerWithEmail(email);
        assertThat(actual).isTrue();
    }

    @Test
    void deleteCustomer() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer  = createRandomCustomer(email);
        underTest.insertCustomer(customer);

        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();

        underTest.deleteCustomer(id);
        var actual = underTest.existsCustomerWithId(id);

        assertThat(actual).isFalse();
    }

    @Test
    void updateCustomer() {
        //Create and insert customer
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer  = createRandomCustomer(email);
        underTest.insertCustomer(customer);

        //Get id to get later
        int id = underTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(Customer::getId)
                .findFirst()
                .orElseThrow();
        //Customer update
        String update_name = FAKER.name().fullName();
        String update_email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Integer update_age = FAKER.random().nextInt(18, 99);
        Gender update_gender = FAKER.random().nextInt(0,1) == 1 ? Gender.MALE : Gender.FEMALE;

        customer = underTest.selectCustomerById(id).orElseThrow();
        customer.setName(update_name);
        customer.setEmail(update_email);
        customer.setAge(update_age);
        customer.setGender(update_gender);

        underTest.updateCustomer(customer);

        //Get Customer
        Optional<Customer> actual = underTest.selectCustomerById(id);
        //Assert
        assertThat(actual).isPresent().hasValueSatisfying(c -> {
            assertThat(c.getName()).isEqualTo(update_name);
            assertThat(c.getEmail()).isEqualTo(update_email);
            assertThat(c.getAge()).isEqualTo(update_age);
            assertThat(c.getGender()).isEqualTo(update_gender);
        });
    }
}