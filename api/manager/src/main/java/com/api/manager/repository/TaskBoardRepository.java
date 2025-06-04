package com.api.manager.repository;

import com.api.manager.entity.TaskDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskBoardRepository extends JpaRepository<TaskDb, Long> {
}
