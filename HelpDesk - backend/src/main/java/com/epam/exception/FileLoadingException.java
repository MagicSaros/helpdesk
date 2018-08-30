package com.epam.exception;

public class FileLoadingException extends RuntimeException {

    public FileLoadingException() {
        this("File loading exception");
    }

    public FileLoadingException(String message) {
        super(message);
    }
}
