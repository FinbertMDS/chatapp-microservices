package com.finbertmds.microservice.message.exceptions;

public class ChatRoomAlreadyExistsException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = -2262174827083711141L;

    public ChatRoomAlreadyExistsException(String message) {
        super(message);
    }
}
