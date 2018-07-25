package com.epam.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;

public class ApiError {
    private String status;
    private String message;

    public ApiError(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiError(HttpStatus status, String message) {
        this.status = status.toString();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            json = "{ \"status\":\"" + status + "\", \"message\":\"" + message + "\" }";
        }
        return json;
    }
}
