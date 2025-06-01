package com.api.manager.exception_handler_contoller;

import jakarta.annotation.Nullable;
import org.springframework.dao.DataAccessException;

import java.util.Objects;

public class NotSavedProject extends DataAccessException {
    public NotSavedProject(String message,  Throwable throwable) {
        super(message, Objects.requireNonNull(throwable));
    }
}
