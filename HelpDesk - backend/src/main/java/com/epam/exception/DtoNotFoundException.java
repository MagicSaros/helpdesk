package com.epam.exception;

public class DtoNotFoundException extends RuntimeException {

    public DtoNotFoundException() {
        this("DTO not found");
    }

    public DtoNotFoundException(String message) {
        super(message);
    }
}
