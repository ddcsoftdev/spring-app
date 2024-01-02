package com.ddcsoftware.customer;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//IMPORTANT: This class is used just for testing

//DataAccess Service classes handle the DB.
//Need to annotate as Repository(aka Component) to set as Bean
@Repository("list")
public class CustomerListDataAccessService implements CustomerDao {

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

    @Override
    public void insertCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream()
                .anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        customers.stream().filter(
                c -> c.getId().equals(customerId)).findFirst().ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer update) {
        customers.add(update);
    }

    @Override
    public boolean existsCustomerWithId(Integer customerId) {
        return customers.stream().anyMatch(c -> c.getId().equals(customerId));
    }
}
