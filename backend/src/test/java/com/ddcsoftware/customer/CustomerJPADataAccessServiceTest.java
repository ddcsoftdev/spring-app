package com.ddcsoftware.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    //autoClosable allows to close the mock after we are done testing
    private AutoCloseable autoCloseable;
    //Marks this instance of class as a Mock from Mockito
    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        //Initialize the Mock and save to autoClosable to manage
        autoCloseable = MockitoAnnotations.openMocks(this);
        //Init underTest with our mocked customerRepositry
        underTest = new CustomerJPADataAccessService(customerRepository);
    }

    //Teardown is launched after each test
    @AfterEach
    void tearDown() throws Exception {
        //this closes and resets the Mock after each test
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        //We just need to make sure that .findAll() is invoked in the main function
        //For these tests is about checking the right functions are invoked instead of the data itself
        underTest.selectAllCustomers();
        //This is what verify what function is called in the original function "selectAllCustomers()"
        Mockito.verify(customerRepository).findAll();
    }

    @Test
    void selectCustomerById() {
        int id = 1;
        underTest.selectCustomerById(id);
        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void insertCustomer() {
        Customer customer = new Customer(
                "Test",
                "test@testing.com",
                14);
        underTest.insertCustomer(customer);
        Mockito.verify(customerRepository).save(customer);
    }

    @Test
    void deleteCustomer() {
        int id = 1;
        underTest.deleteCustomer(id);
        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        Customer customer = new Customer(
                "Test",
                "test@testing.com",
                14);
        underTest.updateCustomer(customer);
        Mockito.verify(customerRepository).save(customer);
    }

    @Test
    void existsCustomerWithEmail() {
        String email = "Email@email.com";
        underTest.existsCustomerWithEmail(email);
        Mockito.verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsCustomerWithId() {
        int id = 1;
        underTest.existsCustomerWithId(id);
        Mockito.verify(customerRepository).existsCustomerById(id);
    }

}