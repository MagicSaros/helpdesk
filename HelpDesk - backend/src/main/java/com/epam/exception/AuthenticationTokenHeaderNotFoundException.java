package com.epam.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationTokenHeaderNotFoundException extends AuthenticationException {

    public AuthenticationTokenHeaderNotFoundException(String message) {
        super(message);
    }

    public AuthenticationTokenHeaderNotFoundException(String message, Throwable exception) {
        super(message, exception);
    }
}
