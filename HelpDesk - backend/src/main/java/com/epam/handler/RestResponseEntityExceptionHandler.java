package com.epam.handler;

import com.epam.exception.ApiError;
import com.epam.exception.BadCredentialsException;
import com.epam.exception.CategoryNotFoundException;
import com.epam.exception.DtoNotFoundException;
import com.epam.exception.TicketNotFoundException;
import com.epam.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiError> handleUsernameNotFound(Exception e) {
        return new ResponseEntity<>(new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage()),
            HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserNotFoundException.class, CategoryNotFoundException.class,
        TicketNotFoundException.class})
    public ResponseEntity<ApiError> handleEntitiesNotFound(Exception e) {
        return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DtoNotFoundException.class})
    public ResponseEntity<ApiError> handleDtoNotFound(Exception e) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
