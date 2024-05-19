package com.ddcsoftware.customer;

public record CustomerRegistrationRequests(
        String name,
        String email,
        String password,
        Integer age,
        Gender gender) {
}
