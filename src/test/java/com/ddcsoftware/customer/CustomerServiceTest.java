package com.ddcsoftware.customer;

import com.ddcsoftware.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//This annotation lets us skip the boilerplate Mockito code:
//  AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this)
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        underTest.getAllCustomers();
        Mockito.verify(customerDao).selectAllCustomers();
    }

    //Test by returning a random created customer since the mock does not really return anything
    @Test
    void getCustomerById() {
        int id = 3;
        Customer customer = new Customer(
                id, "James", "james@email.com", 34);

        //We tell it to return our customer when selectCustomerById with id 3 is invoked during the Mock
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //Expect to get the customer above so we assert
        Customer actual = underTest.getCustomerById(id);
        Assertions.assertThat(actual).isEqualTo(customer);
    }

    @Test
    void WillGetEmptyCustomerById() {
        int id = 3;

        //We tell it to return our customer when selectCustomerById with id 3 is invoked during the Mock
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //We check that we are getting the right type of Exception and the right message
        //As the one stated in the real method
        Assertions.assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Customer with id[%d] not found".formatted(id));
    }

    @Test
    void addCustomer() {
    }

    @Test
    void removeCustomerById() {
    }

    @Test
    void updateCustomerById() {
    }
}