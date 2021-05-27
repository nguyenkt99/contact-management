package com.ptit.contact.exception;

public class AccountAlreadyExistException extends RuntimeException{
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
