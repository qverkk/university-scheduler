package com.kul.database.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class CommonEndpointExceptionAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<EndpointErrors> handle(ConstraintViolationException exception, WebRequest webRequest) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new EndpointErrors(
                        exception.getConstraintViolations().stream()
                                .map(e -> new EndpointError(e.getMessage(), exception.getClass().getSimpleName()))
                                .collect(Collectors.toList())
                ));
    }
}
