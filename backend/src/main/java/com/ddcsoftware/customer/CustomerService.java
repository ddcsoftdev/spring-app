package com.ddcsoftware.customer;

import com.ddcsoftware.exception.DuplicateResourceException;
import com.ddcsoftware.exception.RequestValidationException;
import com.ddcsoftware.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//Service acts as the Business layer, communicating the Controller and the DAO
//Marked as Service (aka Component) to set as Bean.
@Service
public class CustomerService {

    /*
    injections
     */
    //this allows get data from DB and send it to Controller
    private final CustomerDao customerDao;
    private final CustomerDTOMapper customerDTOMapper;
    //password encoder
    private final PasswordEncoder passwordEncoder;

    public CustomerService(@Qualifier("jdbc") CustomerDao customerDao, CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers() {
        //Create CustomerDTO with data in Customer Object
        return customerDao.selectAllCustomers()
                .stream()
                .map(customerDTOMapper)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .map(customerDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id[%d] not found".formatted(customerId)));
    }

    public void addCustomer(CustomerRegistrationRequests request) {
        //check if email exists
        if (customerDao.existsCustomerWithEmail(request.email())) {
            throw new DuplicateResourceException("Email is already taken");
        }
        //add customer
        Customer customer = new Customer(
                request.name(),
                request.email(),
                passwordEncoder.encode(request.password()),//encode password
                request.age(),
                request.gender());

        customerDao.insertCustomer(customer);
    }

    public void removeCustomerById(Integer customerId) {

        if (!customerDao.existsCustomerWithId(customerId)) {
            throw new ResourceNotFoundException("Customer with id[%d] not found".formatted(customerId));
        }
        customerDao.deleteCustomer(customerId);
    }

    public void updateCustomerById(Integer customerId, CustomerUpdateRequest updateRequest) {

        boolean changes = false;
        Customer customer = customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id[%d] not found".formatted(customerId)));

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())) {
            customer.setName(updateRequest.name());
            changes = true;
        }
        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())) {
            if (customerDao.existsCustomerWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException("Email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }
        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())) {
            customer.setAge(updateRequest.age());
            changes = true;
        }
        if (updateRequest.gender() != null && !updateRequest.gender().equals(customer.getGender())) {
            customer.setGender(updateRequest.gender());
            changes = true;
        }
        if (!changes) {
            throw new RequestValidationException("No changes have been detected");
        }
        customerDao.updateCustomer(customer);
    }
}
