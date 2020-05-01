package com.kul.database.exceptions.handlers;

import lombok.Value;

@Value
public class InvalidArgumentError {
    String cause;
    String field;
}
