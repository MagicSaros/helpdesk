package com.epam.exception;

public class AttachmentNotFoundException extends RuntimeException {

    public AttachmentNotFoundException() {
        super("Attachment not found");
    }

    public AttachmentNotFoundException(String message) {
        super(message);
    }
}
