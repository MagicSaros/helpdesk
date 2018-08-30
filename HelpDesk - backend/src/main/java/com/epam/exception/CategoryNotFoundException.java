package com.epam.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        this("Category not found");
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
