package com.ddcsoftware.customer;

import java.util.List;
import java.util.Optional;

//Interface used by the DataAccessService class
public interface CustomerDao {
    List<Customer> selectAllCustomers();

    //Optional allows function to return null
    Optional<Customer> selectCustomerById(Integer customerId);

    boolean existsCustomerWithEmail(String email);

    boolean existsCustomerWithId(Integer customerId);

    void insertCustomer(Customer customer);

    void deleteCustomer(Integer customerId);

    void updateCustomer(Customer update);

    Optional<Customer> selectUserByEmail(String email);


}
