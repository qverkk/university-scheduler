package com.kul.database.exceptions.handlers;

import lombok.Value;

@Value
public class ConstraintViolationError {
    String cause;
    String field;
}
