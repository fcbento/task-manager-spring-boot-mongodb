package org.task.resource.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.ObjectNotFoundException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.task.service.exception.DataIntegrityException;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), Instant.now().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), Instant.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ValidationError error = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation error", Instant.now().toString());
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()) {
            error.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public ResponseEntity<StandardError> emailValidation(IncorrectResultSizeDataAccessException exception, HttpServletRequest request) {
        StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), Instant.now().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
