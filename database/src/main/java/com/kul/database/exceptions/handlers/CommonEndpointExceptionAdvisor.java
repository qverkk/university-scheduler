package com.kul.database.exceptions.handlers;

import com.kul.database.classrooms.domain.exceptions.*;
import com.kul.database.lecturerlessons.domain.exceptions.*;
import com.kul.database.lecturerpreferences.domain.exceptions.InsufficientPermissionsToUpdateLecturerPreferences;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceAlreadyExists;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceDoesntExist;
import com.kul.database.lecturerpreferences.domain.exceptions.LecturerPreferenceInvalidTime;
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        List<ConstraintViolationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x ->
                        new ConstraintViolationError(x.getDefaultMessage(), x.getField())
                )
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ConstraintViolationErrorResponse(
                                "Invalid method arguments",
                                ex.getClass().getSimpleName(),
                                errors
                        )
                );
    }

    @ExceptionHandler({
            NoSuchUserException.class,
            LecturerPreferenceAlreadyExists.class,
            LecturerPreferenceDoesntExist.class,
            LecturerPreferenceInvalidTime.class,
            NoSuchLecturerLesson.class,
            NoSuchLessonType.class,
            NoSuchAreaOfStudy.class,
            ClassroomTypeDoesntExist.class,
            ClassroomTypeAlreadyExists.class,
            ClassroomDoesntExist.class,
            CannotAddClassroomWithEmptyType.class,
            NoClassroomTypes.class
    })
    public ResponseEntity<EndpointError> handleUnprocessable(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

    @ExceptionHandler({
            InsufficientPermissionsToUpdateLecturerPreferences.class,
            InsufficientPersmissionsToEnableUsersException.class,
            InsufficientPersmissionsToDeleteUsersException.class,
            InsufficientPersmissionsToGetAllUserData.class,
            InsufficientPermissionsToDeleteLesson.class,
            InsufficientPermissionsToUpdateLesson.class,
            UserCannotHaveLessons.class
    })
    public ResponseEntity<EndpointError> handleForbidden(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }

    @ExceptionHandler({
            LessonTypeAlreadyExists.class
    })
    public ResponseEntity<EndpointError> handleConflict(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        new EndpointError(exception.getMessage(), exception.getClass().getSimpleName())
                );
    }
}
