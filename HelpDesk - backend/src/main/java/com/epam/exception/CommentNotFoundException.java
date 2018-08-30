package com.epam.exception;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException() {
        this("Comment not found");
    }

    public CommentNotFoundException(String message) {
    }
}
