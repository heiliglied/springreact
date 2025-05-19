package com.heiliglied.app.extra;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}