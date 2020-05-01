package com.kul.database.exceptions.handlers;

import lombok.Getter;

import java.util.List;

@Getter
public class ConstraintViolationErrorResponse extends EndpointError {

    private final List<ConstraintViolationError> violations;

    public ConstraintViolationErrorResponse(String message, String code, List<ConstraintViolationError> violations) {
        super(message, code);
        this.violations = violations;
    }
}
