package com.finbertmds.microservice.message.exceptions;

public class ChatRoomDoesNotExistException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 863897265071373150L;

    public ChatRoomDoesNotExistException(String message) {
        super(message);
    }
}
