package com.example.creditservice.util;

import com.example.creditservice.exception.CustomException;
import com.example.creditservice.exception.TimeOutException;
import com.example.creditservice.model.error.CustomError;
import com.example.creditservice.model.response.DataResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<DataResponseError> handleException(CustomException e) {
        DataResponseError response = new DataResponseError(
                new CustomError(
                        e.getCode(),
                        e.getMessage()
                )
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<DataResponseError> handleUsernameNotFoundException(
            UsernameNotFoundException e) {

        DataResponseError response = new DataResponseError(
                new CustomError(
                        "USER_NOT_FOUND",
                        "Пользователь не найден"
                )
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<DataResponseError> handleBadCredentialsException(
            BadCredentialsException e) {

        DataResponseError response = new DataResponseError(
                new CustomError(
                        "INVALID_CREDENTIALS",
                        "Неверный email или пароль"
                )
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataResponseError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Некорректные данные запроса");

        DataResponseError response = new DataResponseError(
                new CustomError(
                        "VALIDATION_ERROR",
                        message
                )
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeOutException.class)
    public ResponseEntity<DataResponseError> handleTimeOutException(
            TimeOutException e) {

        DataResponseError response = new DataResponseError(
                new CustomError(
                        e.getCode(),
                        e.getMessage()
                )
        );

        return new ResponseEntity<>(response, HttpStatus.REQUEST_TIMEOUT);
    }
}
