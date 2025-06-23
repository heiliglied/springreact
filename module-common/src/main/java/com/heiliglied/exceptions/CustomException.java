package com.heiliglied.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final String status;
    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String status, String message, HttpStatus httpStatus) {
        super(message);
        this.status = status;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
