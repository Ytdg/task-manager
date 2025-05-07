package com.api.manager.repository;

import com.api.manager.model.MetaDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface MetaRepository extends JpaRepository<MetaDB, Long> {
}
