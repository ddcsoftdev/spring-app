package com.ddcsoftware.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
