package com.practice.servicepractice.controller;

import com.practice.servicepractice.data.models.ApiResponseService;
import com.practice.servicepractice.exceptions.ValidationActorException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationActorException.class)
    public ResponseEntity<ApiResponseService<String>> handleValidationException(ValidationActorException ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponseService<>(
                        "validation_error",
                        ex.getMessage()
                ));
    }
}
