package com.ddcsoftware.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    //Optional allows function to return null
    Optional<Customer> selectCustomerById(Integer customerId);
}
