package com.kul.database.exceptions.handlers;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidEndpointArgumentsException extends EndpointError {

    private final List<InvalidArgumentError> violations;

    public InvalidEndpointArgumentsException(String message, String code, List<InvalidArgumentError> errorList) {
        super(message, code);
        this.violations = errorList;
    }
}
