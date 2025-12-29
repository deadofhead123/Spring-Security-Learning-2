package com.sweet.acl_jwt.exception;

public class GrantExistedException extends RuntimeException {
    public GrantExistedException(String message) {
        super(message);
    }
}
