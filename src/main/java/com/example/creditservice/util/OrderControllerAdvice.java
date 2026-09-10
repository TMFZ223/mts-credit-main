package com.example.creditservice.util;

import com.example.creditservice.exception.CustomException;
import com.example.creditservice.exception.TimeOutException;
import com.example.creditservice.model.error.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid request");

        return ResponseEntity.badRequest()
                .body(new CustomError("err", message));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomError> handleException(CustomException e) {
        return ResponseEntity.badRequest()
                .body(new CustomError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(TimeOutException.class)
    public ResponseEntity<CustomError> handleTimeOutException(TimeOutException e) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
                .body(new CustomError(e.getCode(), e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomError> handleMissingRequestParameter(MissingServletRequestParameterException e) {
        return ResponseEntity.badRequest()
                .body(new CustomError("err", e.getParameterName() + " is required"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomError> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.badRequest()
                .body(new CustomError("err", "Invalid request"));
    }
}
