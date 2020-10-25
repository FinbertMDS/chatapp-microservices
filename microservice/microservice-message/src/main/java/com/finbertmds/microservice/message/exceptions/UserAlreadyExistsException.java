package com.finbertmds.microservice.message.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 2905976559129774591L;

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
