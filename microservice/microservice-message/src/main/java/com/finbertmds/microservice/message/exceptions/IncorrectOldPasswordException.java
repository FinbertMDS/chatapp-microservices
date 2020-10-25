package com.finbertmds.microservice.message.exceptions;

public class IncorrectOldPasswordException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = -9141965984531749481L;

    public IncorrectOldPasswordException(String message) {
        super(message);
    }
}
