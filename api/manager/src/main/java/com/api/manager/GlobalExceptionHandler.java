package com.api.manager;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleResourceNotParseException(HttpMessageNotReadableException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleResourceNotValidException(MethodArgumentNotValidException ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }
}


