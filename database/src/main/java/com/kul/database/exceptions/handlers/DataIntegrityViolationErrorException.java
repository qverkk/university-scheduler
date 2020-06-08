package com.kul.database.exceptions.handlers;

public class DataIntegrityViolationErrorException extends EndpointError {
    public DataIntegrityViolationErrorException(String message, String code) {
        super(message, code);
    }
}
