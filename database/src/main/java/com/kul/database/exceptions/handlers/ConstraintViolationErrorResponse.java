package com.kul.database.exceptions.handlers;

import lombok.Getter;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;

import javax.validation.ConstraintViolation;
import java.util.List;

@Getter
public class ConstraintViolationErrorResponse extends EndpointError {

    private final List<ConstraintViolation<?>> violations;

    public ConstraintViolationErrorResponse(String message, String code, List<ConstraintViolation<?>> violations) {
        super(message, code);
        this.violations = violations;
    }
}
