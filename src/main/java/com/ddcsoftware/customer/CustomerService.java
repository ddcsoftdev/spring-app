package com.ddcsoftware.customer;

import org.springframework.stereotype.Service;

import java.util.List;

//Service acts as the Business layer, communicating the Controller and the DAO
//Marked as Service (aka Component) to set as Bean.
@Service
public class CustomerService {

    //this allows get data from DB and send it to Controller
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomerById(Integer customerId) {
        return customerDao.selectCustomerById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Customer with id[%d] not found".formatted(customerId)));
    }
}
