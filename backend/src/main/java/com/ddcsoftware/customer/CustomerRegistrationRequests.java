package com.ddcsoftware.customer;

public record CustomerRegistrationRequests(
        String name,
        String email,
        Integer age,
        Gender gender) {
}
