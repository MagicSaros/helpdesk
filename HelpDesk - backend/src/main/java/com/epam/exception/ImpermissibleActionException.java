package com.epam.exception;

public class ImpermissibleActionException extends RuntimeException {

    public ImpermissibleActionException() {
        super("Invalid action");
    }

    public ImpermissibleActionException(String message) {
        super(message);
    }
}
