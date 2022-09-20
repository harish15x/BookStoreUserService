package com.bridgelabz.bookstore.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UserNotFoundException extends RuntimeException{
    private int statusCode;
    private String statusMessage;

    public UserNotFoundException(int statusCode, String statusMessage) {
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
