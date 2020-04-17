package com.kul.database.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EndpointError {
    private final String message;
    private final String code;
}
