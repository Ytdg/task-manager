package com.api.manager.repository;

import com.api.manager.entity.SprintDb;
import com.api.manager.entity.TaskDb;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskBoardRepository extends JpaRepository<TaskDb, Long> {
    List<TaskDb> getAllBySprintDb(@NonNull SprintDb sprintDb);
}

