package com.booking.tickets.exception;

public class WrongCredentialsAuthenticationException extends Exception {
    public WrongCredentialsAuthenticationException(String message) {
        super(message);
    }
}
