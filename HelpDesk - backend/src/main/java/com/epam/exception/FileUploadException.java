package com.epam.exception;

public class FileUploadException extends RuntimeException {

    public FileUploadException() {
        super("File upload exception");
    }

    public FileUploadException(String message) {
        super(message);
    }
}
