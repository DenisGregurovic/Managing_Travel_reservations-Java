package com.example.javafxproject.production.exceptions.uncheckedExceptions;

public class AccommodationNameAlreadyExistsException extends RuntimeException{
    public AccommodationNameAlreadyExistsException() {
    }

    public AccommodationNameAlreadyExistsException(String message) {
        super(message);
    }

    public AccommodationNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccommodationNameAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public AccommodationNameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
