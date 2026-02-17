package com.daniel_tickets.accounts_validation.adapter.out.user.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
