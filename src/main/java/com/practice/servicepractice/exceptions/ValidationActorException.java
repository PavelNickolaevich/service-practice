package com.practice.servicepractice.exceptions;

public class ValidationActorException extends RuntimeException{

    public ValidationActorException(String message) {
        super(message);
    }

    public ValidationActorException(String message, Throwable cause) {
        super(message, cause);
    }
}
