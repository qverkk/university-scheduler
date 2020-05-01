package com.kul.database.exceptions.handlers;

import com.kul.database.lecturerpreferences.domain.exceptions.InsufficientPermissionsToUpdateLecturerPreferences;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToDeleteUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToEnableUsersException;
import com.kul.database.usermanagement.domain.exceptions.InsufficientPersmissionsToGetAllUserData;
import com.kul.database.usermanagement.domain.exceptions.NoSuchUserException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CommonEndpointExceptionAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ConstraintViolationErrorResponse> handle(ConstraintViolationException exception, WebRequest webRequest) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ConstraintViolationErrorResponse(
                        "Validation failed for some classes",
                        exception.getClass().getSimpleName(),
                        exception.getConstraintViolations()
                                .stream()
                                .map(e -> new ConstraintViolationError(e.getMessage(), e.getPropertyPath().toString()))
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<InvalidArgumentError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x ->
                        new InvalidArgumentError(x.getDefaultMessage(), x.getField())
                )
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new InvalidEndpointArgumentsException(
                                "Invalid method arguments",
                                ex.getClass().getSimpleName(),
                                errors
                        )
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

    @ExceptionHandler(InsufficientPermissionsToUpdateLecturerPreferences.class)
    public ResponseEntity<EndpointError> handle(InsufficientPermissionsToUpdateLecturerPreferences exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

}
