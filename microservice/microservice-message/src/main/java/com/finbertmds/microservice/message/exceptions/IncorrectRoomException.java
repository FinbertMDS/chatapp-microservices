package com.finbertmds.microservice.message.exceptions;

public class IncorrectRoomException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = -5040243748595870457L;

    public IncorrectRoomException(String message) {
        super(message);
    }
}
