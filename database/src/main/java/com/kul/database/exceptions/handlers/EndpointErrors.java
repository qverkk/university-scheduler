package com.kul.database.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class EndpointErrors {
    private final List<EndpointError> errors;
}
