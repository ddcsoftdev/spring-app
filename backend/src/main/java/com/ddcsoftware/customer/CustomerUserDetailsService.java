package com.ddcsoftware.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Class that get the current Customer User Login Information.
 * This implements UserDetailsService from Spring Security module
 */
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerDao customerDAO;

    //get jpa DAO (this needs to be updated for JDBC and List too)
    public CustomerUserDetailsService(@Qualifier("jpa") CustomerDao customerDao){
        this.customerDAO = customerDao;
    }

    /*
    Get the User Details from username (in our case is email).
    User Details is Spring's Security module for identifying users
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerDAO.selectUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }
}
