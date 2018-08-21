package com.epam.exception;

public class FileLoadingException extends RuntimeException {

    public FileLoadingException() {
        super("File loading exception");
    }

    public FileLoadingException(String message) {
        super(message);
    }
}
