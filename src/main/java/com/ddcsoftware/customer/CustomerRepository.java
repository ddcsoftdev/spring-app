package com.ddcsoftware.customer;

import org.springframework.data.jpa.repository.JpaRepository;

//Takes the name of model class and the type for the ID
//Does not need @Repository as it's implicit with JpaRepository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    //Automatically search by email thanks to Jpa functionality
    boolean existsCustomerByEmail(String email);

    boolean existsCustomerById(Integer customerId);
}
