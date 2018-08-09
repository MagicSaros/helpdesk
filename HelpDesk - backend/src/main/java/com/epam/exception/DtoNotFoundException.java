package com.epam.exception;

public class DtoNotFoundException extends RuntimeException {

    public DtoNotFoundException() {
        super("DTO not found");
    }

    public DtoNotFoundException(String message) {
        super(message);
    }
}
