package com.epam.exception;

public class BadCredentialsException extends RuntimeException {

    public BadCredentialsException() {
        this("Bad credentials");
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
