package com.kul.database.exceptions.handlers;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidEndpointArgumentsException extends EndpointError {

    private final List<InvalidArgumentError> errors;

    public InvalidEndpointArgumentsException(String message, String code, List<InvalidArgumentError> errorList) {
        super(message, code);
        this.errors = errorList;
    }
}
