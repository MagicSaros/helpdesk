package com.epam.handler;

import com.epam.exception.ApiError;
import com.epam.exception.AttachmentNotFoundException;
import com.epam.exception.BadCredentialsException;
import com.epam.exception.CategoryNotFoundException;
import com.epam.exception.CommentNotFoundException;
import com.epam.exception.DtoNotFoundException;
import com.epam.exception.FileLoadingException;
import com.epam.exception.ImpermissibleActionException;
import com.epam.exception.TicketNotFoundException;
import com.epam.exception.UserNotFoundException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<ApiError> handleUsernameNotFound(Exception e) {
        ApiError apiError = new ApiError.Builder()
            .setTitle("Bad credentials")
            .setStatus(HttpStatus.UNAUTHORIZED.value())
            .setMessage(e.getMessage())
            .setTimestamp(new Date().getTime())
            .setDeveloperMessage(e.getClass().getName())
            .build();
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UserNotFoundException.class, CategoryNotFoundException.class,
        TicketNotFoundException.class, AttachmentNotFoundException.class,
        CommentNotFoundException.class})
    public ResponseEntity<ApiError> handleEntitiesNotFound(Exception e) {
        ApiError apiError = new ApiError.Builder()
            .setTitle("Resource not found")
            .setStatus(HttpStatus.NOT_FOUND.value())
            .setMessage(e.getMessage())
            .setTimestamp(new Date().getTime())
            .setDeveloperMessage(e.getClass().getName())
            .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({DtoNotFoundException.class})
    public ResponseEntity<ApiError> handleDtoNotFound(Exception e) {
        ApiError apiError = new ApiError.Builder()
            .setTitle("DTO not found")
            .setStatus(HttpStatus.BAD_REQUEST.value())
            .setMessage(e.getMessage())
            .setTimestamp(new Date().getTime())
            .setDeveloperMessage(e.getClass().getName())
            .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleValidationError(Exception e) {
        ApiError apiError = new ApiError.Builder()
            .setTitle("Validation failed")
            .setStatus(HttpStatus.BAD_REQUEST.value())
            .setMessage(e.getMessage())
            .setTimestamp(new Date().getTime())
            .setDeveloperMessage(e.getClass().getName())
            .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({FileLoadingException.class})
    public ResponseEntity<ApiError> handleFileLoadingError(Exception e) {
        ApiError apiError = new ApiError.Builder()
            .setTitle("File loading error")
            .setStatus(HttpStatus.BAD_REQUEST.value())
            .setMessage(e.getMessage())
            .setTimestamp(new Date().getTime())
            .setDeveloperMessage(e.getClass().getName())
            .build();
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ImpermissibleActionException.class})
    public ResponseEntity<ApiError> handleImpermissibleActionException(Exception e) {
        ApiError apiError = new ApiError.Builder()
            .setTitle("Impermissible action")
            .setStatus(HttpStatus.FORBIDDEN.value())
            .setMessage(e.getMessage())
            .setTimestamp(new Date().getTime())
            .setDeveloperMessage(e.getClass().getName())
            .build();
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }
}
