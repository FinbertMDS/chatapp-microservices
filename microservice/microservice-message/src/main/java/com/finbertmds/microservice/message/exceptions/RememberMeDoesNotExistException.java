package com.finbertmds.microservice.message.exceptions;

public class RememberMeDoesNotExistException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 3774582061300698544L;

    public RememberMeDoesNotExistException(String message) {
        super(message);
    }
}
