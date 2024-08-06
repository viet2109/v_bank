package com.v_bank.v_bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getError().getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, error -> {
            String defaultMessage = error.getDefaultMessage();
            return defaultMessage != null ? defaultMessage : "Validation error";
        }));
        return ResponseEntity.badRequest().body(errors);
    }

}
