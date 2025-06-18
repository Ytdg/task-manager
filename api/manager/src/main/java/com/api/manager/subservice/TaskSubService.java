package com.api.manager.subservice;

import com.api.manager.dto.TaskDTO;
import com.api.manager.entity.SprintDb;
import com.api.manager.entity.TaskDb;
import com.api.manager.repository.TaskBoardRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TaskSubService {
    private final TaskBoardRepository taskBoardRepository;

    TaskSubService(TaskBoardRepository taskBoardRepository) {
        this.taskBoardRepository = taskBoardRepository;
    }

    public Stream<TaskDb> getTasksOnSprint(@NonNull Long idSprint) {
        SprintDb sprintDb = new SprintDb(idSprint);
        return taskBoardRepository.getAllBySprintDb(sprintDb).stream();
    }
}
