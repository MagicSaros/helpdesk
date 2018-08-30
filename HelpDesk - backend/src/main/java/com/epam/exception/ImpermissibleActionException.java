package com.epam.exception;

public class ImpermissibleActionException extends RuntimeException {

    public ImpermissibleActionException() {
        this("Invalid action");
    }

    public ImpermissibleActionException(String message) {
        super(message);
    }
}
