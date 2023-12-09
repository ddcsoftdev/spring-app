package com.ddcsoftware.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


//Request mapping sets the default mapping to this url @RequestMapping("api/v1/customers")
//This means that by default @GetMapping reads that url and so needs no parenthesis
//Also we can access other children from the root url by @GetMapping("{customerId}")
//@RequestMapping("api/v1/customers")

//Controller classes handle all the HTTP requests
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("api/v1/customers")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("api/v1/customers/{customerId}")
    public Customer getClientById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    //@RequestBody requests de body of the HTTP POST message
    @PostMapping("api/v1/customers")
    public void registerCustomer(@RequestBody CustomerRegistrationRequests request) {
        customerService.addCustomer(request);
    }

    @DeleteMapping("api/v1/customers/{customerId}")
    public void removeCustomerById(@PathVariable("customerId") Integer customerId) {
        customerService.removeCustomerById(customerId);
    }

    @PutMapping("api/v1/customers/{customerId}")
    public void updateCustomerById(@PathVariable("customerId") Integer customerId,
                               @RequestBody CustomerUpdateRequest updateRequest) {
        customerService.updateCustomerById(customerId, updateRequest);
    }
}
