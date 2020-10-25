package com.finbertmds.microservice.message.resource.exception;

import java.util.List;

import com.finbertmds.microservice.message.exceptions.ChatRoomAlreadyExistsException;
import com.finbertmds.microservice.message.exceptions.ChatRoomDoesNotExistException;
import com.finbertmds.microservice.message.exceptions.IncorrectOldPasswordException;
import com.finbertmds.microservice.message.exceptions.IncorrectRoomException;
import com.finbertmds.microservice.message.exceptions.RememberMeDoesNotExistException;
import com.finbertmds.microservice.message.exceptions.UserAlreadyExistsException;
import com.finbertmds.microservice.message.exceptions.UserNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    public @ResponseBody
    String handleBindingFailure(BindException exception) {
        return convertErrorMessage(exception.getBindingResult().getAllErrors());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    public @ResponseBody
    String handleBindingFailure(MethodArgumentNotValidException exception) {
        return convertErrorMessage(exception.getBindingResult().getAllErrors());
    }

    @ExceptionHandler(value = {
            UserAlreadyExistsException.class,
            UserNotFoundException.class,
            IncorrectOldPasswordException.class,
            ChatRoomAlreadyExistsException.class,
            ChatRoomDoesNotExistException.class,
            IncorrectRoomException.class
    })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String functionalExceptions(Exception exception) {
        return exception.getMessage();
    }


    @ExceptionHandler(RememberMeDoesNotExistException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String rememberMeException(RememberMeDoesNotExistException exception) {
        return exception.getMessage();
    }

    private String convertErrorMessage(List<ObjectError> errors) {
        final StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError)error;
                errorMsg.append("\n\t")
                        .append(" Error on field '")
                        .append(fieldError.getField()).append("'")
                        .append(". Failed validation : '")
                        .append(fieldError.getDefaultMessage()).append("'")
                        .append(". Rejected value : '")
                        .append(fieldError.getRejectedValue()).append("'");
            } else {
                errorMsg.append("\n\t")
                        .append("Error in object '")
                        .append(error.getObjectName()).append("' ")
                        .append(error.getDefaultMessage());
            }
        }
        return errorMsg.toString();
    }
}
