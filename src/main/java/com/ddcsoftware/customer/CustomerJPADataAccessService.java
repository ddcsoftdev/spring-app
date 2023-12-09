package com.ddcsoftware.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//DataAccess Service classes handle the DB.
//Need to annotate as Repository(aka Component) to set as Bean
@Repository("jpa")
public class CustomerJPADataAccessService implements CustomerDao {

    private final CustomerRepository customerRepository;

    public CustomerJPADataAccessService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customerRepository.findById(customerId);
    }
}
