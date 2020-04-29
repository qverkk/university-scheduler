package com.kul.database.exceptions.handlers;

import com.kul.database.exceptions.*;
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

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<EndpointError> handle(NoSuchUserException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

    @ExceptionHandler(InsufficientPersmissionsToGetAllUserData.class)
    public ResponseEntity<EndpointError> handle(InsufficientPersmissionsToGetAllUserData exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

    @ExceptionHandler(InsufficientPersmissionsToDeleteUsersException.class)
    public ResponseEntity<EndpointError> handle(InsufficientPersmissionsToDeleteUsersException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

    @ExceptionHandler(InsufficientPersmissionsToEnableUsersException.class)
    public ResponseEntity<EndpointError> handle(InsufficientPersmissionsToEnableUsersException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

    @ExceptionHandler(LecturerPreferenceAlreadyExists.class)
    public ResponseEntity<EndpointError> handle(LecturerPreferenceAlreadyExists exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

    @ExceptionHandler(LecturerPreferenceDoesntExist.class)
    public ResponseEntity<EndpointError> handle(LecturerPreferenceDoesntExist exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

}
