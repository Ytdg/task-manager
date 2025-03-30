package com.api.manager.exception_handler_contoller;


import com.api.manager.common.NotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* relate to storage*/
@ControllerAdvice
public class ExceptionStoreHandler{
    @ExceptionHandler(NotSavedException.class)
    public ErrorResponse handleResourceNotSaveStoreException(NotSavedException ex) {
        System.out.println("g");
        return ErrorResponse.create(ex, HttpStatus.CONFLICT, ex.getLocalizedMessage());
    }
}
