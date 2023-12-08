package com.ddcsoftware.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//DataAccess Service classes handle the DB.
// Need to annotate as Repository(aka Component) to set as Bean
@Repository
public class CustomerDataAccessService implements CustomerDao {

    //Temp fake database
    public static List<Customer> customers;

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer customerId) {
        return customers.stream().
                filter(customer -> customer.getId().equals(customerId))
                .findFirst();
    }
}
