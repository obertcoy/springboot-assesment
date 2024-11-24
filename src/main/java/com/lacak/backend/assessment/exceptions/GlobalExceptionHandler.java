package com.lacak.backend.assessment.exceptions;

import java.util.stream.Collectors;

import org.hibernate.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.lacak.backend.assessment.models.dtos.api.ApiResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for the application. This class intercepts exceptions thrown during
 * the execution of the application and returns appropriate responses with meaningful error messages.
 * 
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @ExceptionHandler({ TypeMismatchException.class, MethodArgumentTypeMismatchException.class })
        public ResponseEntity<ApiResponse<Void>> handleTypeMismatchException(Exception e) {

                String errorMessage = "Invalid parameter type: " + e.getMessage();
                HttpStatus status = HttpStatus.BAD_REQUEST;

                log.warn("Type mismatch error: {}", errorMessage);

                return ResponseEntity
                                .status(status)
                                .body(ApiResponse.error(status.value(), errorMessage));
        }


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

                log.error("{}: {}", errorMessage, e.getMessage());

                return ResponseEntity
                                .status(status)
                                .body(ApiResponse.error(status.value(), errorMessage));
        }
}
