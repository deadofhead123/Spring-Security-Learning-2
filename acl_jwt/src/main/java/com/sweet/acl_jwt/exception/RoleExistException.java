package com.sweet.acl_jwt.exception;

public class RoleExistException extends RuntimeException {
    public RoleExistException(String message) {
        super(message);
    }
}
