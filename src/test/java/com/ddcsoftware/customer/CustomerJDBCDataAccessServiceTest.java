package com.ddcsoftware.customer;

import com.ddcsoftware.AbstractTestcontainers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

//This class testes CustomerJDBCDataAccessService
//Since AbstractTestcontainers has no @SpringBootTest we need to set up dependencies manually
//As we are not using Application Context to make the test faster (We don't need all the Beans)
class CustomerJDBCDataAccessServiceTest extends AbstractTestcontainers {

    private CustomerJDBCDataAccessService underTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

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
    }

    @Test
    void selectCustomerById() {
    }

    @Test
    void existsCustomerWithEmail() {
    }

    @Test
    void existsCustomerWithId() {
    }

    @Test
    void insertCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    @Test
    void updateCustomer() {
    }
}