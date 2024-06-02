package com.ddcsoftware.auth;

import com.ddcsoftware.customer.CustomerDTO;

public record AuthenticationResponse(CustomerDTO customerDTO,
                                     String token) {
}
