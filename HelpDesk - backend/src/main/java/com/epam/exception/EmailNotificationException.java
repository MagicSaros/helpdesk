package com.epam.exception;

public class EmailNotificationException extends RuntimeException {

    public EmailNotificationException() {
        this("Email notification exception");
    }

    public EmailNotificationException(String message) {
        super(message);
    }

}
