package com.epam.exception;

public class BadCredentialsException extends RuntimeException {
    private String message = "";

    public BadCredentialsException() {
        super("Bad credentials");
    }

    public BadCredentialsException(String message) {
        super(message);
    }
}
