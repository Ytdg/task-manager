package com.api.manager.repository;

import com.api.manager.model.ProjectDb;
import com.api.manager.model.UserDb;
import com.api.manager.model.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDb, Long> {
}
