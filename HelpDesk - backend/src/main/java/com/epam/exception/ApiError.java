package com.epam.exception;

public class ApiError {

    private String title;
    private int status;
    private String message;
    private long timestamp;
    private String developerMessage;

    public ApiError() {
    }

    private ApiError(Builder builder) {
        this.title = builder.title;
        this.status = builder.status;
        this.message = builder.message;
        this.timestamp = builder.timestamp;
        this.developerMessage = builder.developerMessage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public static class Builder {

        private String title;
        private int status;
        private String message;
        private long timestamp;
        private String developerMessage;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTimestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setDeveloperMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ApiError build() {
            return new ApiError(this);
        }
    }
}
