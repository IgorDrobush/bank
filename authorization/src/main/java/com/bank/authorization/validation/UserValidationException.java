package com.bank.authorization.service.validation;

public class UserValidationException extends Exception {
    public UserValidationException(String message) {
        super(message);
    }
}
