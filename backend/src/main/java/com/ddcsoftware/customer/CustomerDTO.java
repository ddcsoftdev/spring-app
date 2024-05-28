package com.ddcsoftware.customer;

import java.util.List;

/**
 * Class that determines which data to expose from Customer to the client
 */
public record CustomerDTO(
        Integer id,
        String name,
        String email,
        Gender gender,
        Integer age,
        List<String> roles,
        String username
){
}
