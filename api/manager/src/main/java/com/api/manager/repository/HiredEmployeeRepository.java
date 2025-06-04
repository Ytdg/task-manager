package com.api.manager.repository;

import com.api.manager.entity.HiredEmployeeDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiredEmployeeRepository extends JpaRepository<HiredEmployeeDB, Long> {
}
