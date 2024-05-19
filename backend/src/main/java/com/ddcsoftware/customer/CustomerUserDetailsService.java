package com.ddcsoftware.customer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerDao customerDAO;

    public CustomerUserDetailsService(@Qualifier("jpa") CustomerDao customerDao){
        this.customerDAO = customerDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerDAO.selectUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }
}
