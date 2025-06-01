package com.api.manager.repository;

import com.api.manager.entity.MetaDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaRepository extends JpaRepository<MetaDB, Long> {

}
