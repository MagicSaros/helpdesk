package com.epam.handler;

import com.epam.exception.ApiError;
import com.epam.exception.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiError> handleUsernameNotFound(Exception e) {
        return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED , e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
