package com.api.manager.common;

import lombok.Getter;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.mapping.Any;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.sql.SQLException;

public abstract class BaseService<R extends JpaRepository<T, ID>, T, ID extends Serializable> {
    @Getter
    private final R repository;

    protected BaseService(R repository) {
        this.repository = repository;
    }

    protected void save(T obj) throws DataAccessException {
        repository.save(obj);
    }


}

