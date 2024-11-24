package com.lacak.backend.assesment.exceptions;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.lacak.backend.assesment.models.dtos.api.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException e) {

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        HttpStatus status = HttpStatus.BAD_REQUEST;

        log.warn("Validation error: {}", errorMessage);

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(
            Exception e) {

        String errorMessage = "Failed to process request";

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.warn("{}: {}", errorMessage, e.getMessage());

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status.value(), errorMessage));
    }
}
