package com.ddcsoftware.customer;

import com.ddcsoftware.exception.DuplicateResourceException;
import com.ddcsoftware.exception.RequestValidationException;
import com.ddcsoftware.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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
        verify(customerDao).selectAllCustomers();
    }

    //Test by returning a random created customer since the mock does not really return anything
    @Test
    void getCustomerById() {
        int id = 3;
        Customer customer = new Customer(
                id, "James", "james@email.com", 34);

        //We tell it to return our customer when selectCustomerById with id 3 is invoked during the Mock
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //Expect to get the customer above so we assert
        Customer actual = underTest.getCustomerById(id);
        assertThat(actual).isEqualTo(customer);
    }

    @Test
    void WillGetEmptyCustomerById() {
        int id = 3;

        //We tell it to return our customer when selectCustomerById with id 3 is invoked during the Mock
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //We check that we are getting the right type of Exception and the right message
        //As the one stated in the real method
        assertThatThrownBy(() -> underTest.getCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id[%d] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        String email = "james@email.com";
        //first mock that we check for email
        when(customerDao.existsCustomerWithEmail(email)).thenReturn(false);

        //The request is required by original addCustomer
        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                "James", email, 34);
        underTest.addCustomer(request);

        //ArgumentCapture captures an argument from the method.
        //In this case it captures the Client passed within the method addCustomer to insertCustomer
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);

        //now capture this customer from the mocked method
        //by verifying the call of insertCustomer(customer) within addCustomer
        //we can extract the value "customer" that was passed to insertCustomer
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        //Assert each field
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void WillDenyRequestAddCustomer() {
        String email = "james@email.com";
        //first mock that we check for email and it already exists
        when(customerDao.existsCustomerWithEmail(email)).thenReturn(true);

        //The request is required by original addCustomer
        CustomerRegistrationRequests request = new CustomerRegistrationRequests(
                "James", email, 34);

        //Check right Exception thrown
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email is already taken");

        //now verify that it will never insert a customer
        //since no more lines of code should execute after email check
        verify(customerDao, never()).insertCustomer(Mockito.any());

    }

    @Test
    void removeCustomerById() {
        int id = 3;

        //Make sure when this check is done that it returns true
        when(customerDao.existsCustomerWithId(id)).thenReturn(true);
        underTest.removeCustomerById(id);

        ArgumentCaptor<Integer> idArgumentCapture = ArgumentCaptor.forClass(Integer.class);
        verify(customerDao).deleteCustomer(idArgumentCapture.capture());
        int capturedId = idArgumentCapture.getValue();

        assertThat(capturedId).isEqualTo(id);
    }

    @Test
    void WillDenyRequestRemoveCustomerById() {
        int id = 3;
        //Make sure when this check is done that it returns false (customer does not exist)
        when(customerDao.existsCustomerWithId(id)).thenReturn(false);

        //Check town exception
        assertThatThrownBy(() -> underTest.removeCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id[%d] not found".formatted(id));

        verify(customerDao, never()).deleteCustomer(id);
    }

    @Test
    void updateAllCustomerProperties() {
        int id = 3;
        Customer customer = new Customer(
                id, "James", "james@email.com", 34);
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //Create new customer Request
        String email = "alice@example.com";
        CustomerUpdateRequest request = new CustomerUpdateRequest(
                "Alice", email, 24);

        //simulate email check
        when(customerDao.existsCustomerWithEmail(email)).thenReturn(false);
        //call function
        underTest.updateCustomerById(id, request);
        //verify
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        //Assert
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
    }

    @Test
    void canUpdateOnlyCustomerName() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                id, "James", "James@gmail.com", 19
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alice", null, null);

        // When
        underTest.updateCustomerById(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(updateRequest.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                id, "James", "James@gmail.com", 19
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "Alice@example.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(false);

        // When
        underTest.updateCustomerById(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(capturedCustomer.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void canUpdateOnlyCustomerAge() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                id, "James", "James@gmail.com", 19
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, 22);

        // When
        underTest.updateCustomerById(id, updateRequest);

        // Then
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();

        assertThat(capturedCustomer.getName()).isEqualTo(customer.getName());
        assertThat(capturedCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(customer.getEmail());
    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                id, "James", "James@gmail.com", 19
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "Alice@example.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null);

        when(customerDao.existsCustomerWithEmail(newEmail)).thenReturn(true);

        // When
        assertThatThrownBy(() -> underTest.updateCustomerById(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        // Given
        int id = 10;
        Customer customer = new Customer(
                id, "James", "James@gmail.com", 19
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                customer.getName(), customer.getEmail(), customer.getAge());

        // When
        assertThatThrownBy(() -> underTest.updateCustomerById(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("No changes have been detected");

        // Then
        verify(customerDao, never()).updateCustomer(any());
    }
}