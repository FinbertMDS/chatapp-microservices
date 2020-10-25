package com.finbertmds.microservice.message.exceptions;

public class UserNotFoundException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = -6009284869321288375L;

    public UserNotFoundException(String message) {
        super(message);
    }
}
