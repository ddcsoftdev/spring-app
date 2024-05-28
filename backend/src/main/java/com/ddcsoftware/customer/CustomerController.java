package com.ddcsoftware.customer;

import com.ddcsoftware.jwt.JWTUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//Request mapping sets the default mapping to this url @RequestMapping("api/v1/customers")
//This means that by default @GetMapping reads that url and so needs no parenthesis
//Also we can access other children from the root url by @GetMapping("{customerId}")
//@RequestMapping("api/v1/customers")

//Controller classes handle all the HTTP requests
@RestController
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil; //used for token registration

    public CustomerController(CustomerService customerService, JWTUtil jwtUtil) {
        this.customerService = customerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("api/v1/customers")
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("api/v1/customers/{customerId}")
    public CustomerDTO getClientById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);
    }

    //@RequestBody requests de body of the HTTP POST message
    @PostMapping("api/v1/customers")
    //Response Entity allows to attach Key to the header
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRegistrationRequests request) {
        customerService.addCustomer(request);
        //gives a unique identifier with Role of a USER or for example could be ADMIN
        //TODO DD: Change request.email to the username of user, and with his role within app
        String jwtToken = jwtUtil.issueToken(request.email(), "ROLE_USER");
        //Adding token to header
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
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
