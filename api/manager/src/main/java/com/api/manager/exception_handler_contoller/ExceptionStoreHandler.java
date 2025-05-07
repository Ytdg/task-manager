package com.api.manager.exception_handler_contoller;


import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* relate to storage*/
@ControllerAdvice
public class ExceptionStoreHandler {
    @ExceptionHandler(NotSavedException.class)
    public ErrorResponse handleResourceNotSaveStoreException(NotSavedException ex) {
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, ex.getLocalizedMessage());
    }
    @ExceptionHandler(NotGetObjException.class)
    public ErrorResponse handleResourceNotGetObjStoreException(NotGetObjException ex) {
        return ErrorResponse.create(ex, HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    }
}
