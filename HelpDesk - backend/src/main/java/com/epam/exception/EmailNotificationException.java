package com.epam.exception;

public class EmailNotificationException extends RuntimeException {

    public EmailNotificationException() {
        super("Email notification exception");
    }

    public EmailNotificationException(String message) {
        super(message);
    }

}
