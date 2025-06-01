package com.api.manager.exception_handler_contoller;


import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/* relate to storage*/
@ControllerAdvice
public class ExceptionStoreHandler {
    @ExceptionHandler(NotSavedStoreUserException.class)
    public ErrorResponse handleResourceNotSaveStoreUserException(NotSavedStoreUserException ex) {
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, ex.getLocalizedMessage());
    }

    @ExceptionHandler(NotSavedProject.class)
    public ErrorResponse handleResourceNotSaveProjectStoreException(NotSavedProject ex) {
        return ErrorResponse.create(ex, HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleResourceNotFoundStoreException(NoSuchElementException ex) {
        return ErrorResponse.create(ex, HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }
}
