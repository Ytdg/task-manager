package com.api.manager.common;

import jakarta.annotation.Nullable;
import org.springframework.dao.DataAccessException;

import java.util.Objects;


public  class  NotSavedException extends  DataAccessException{
    public NotSavedException(String message, @Nullable Throwable throwable) {
        super(message, Objects.requireNonNull(throwable));
    }
}


