package com.api.manager.service;

import com.api.manager.common.Mapping;
import com.api.manager.common.StatusObj;
import com.api.manager.dto.SprintDTO;
import com.api.manager.entity.SprintDb;
import com.api.manager.entity.TaskDb;
import com.api.manager.exception_handler_contoller.NotSavedResource;
import com.api.manager.repository.SprintRepository;
import com.api.manager.repository.TaskBoardRepository;
import com.api.manager.subservice.SprintSubService;
import com.api.manager.subservice.TaskSubService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SprintBoardService {

    private final SprintSubService sprintSubService;
    private final TaskSubService taskSubService;

    SprintBoardService(SprintSubService sprintSubService, TaskSubService taskBoardService) {
        this.sprintSubService = sprintSubService;
        this.taskSubService = taskBoardService;
    }


    @SneakyThrows
    @Transactional
    public SprintDTO create(@NonNull SprintDTO sprintDTO, long idProject) {
        return Mapping.toSprintDTO(sprintSubService.create(sprintDTO, idProject));
    }

    public List<SprintDTO> getAll(long idProject) {
        return sprintSubService.getAll(idProject, s -> taskSubService.getTasksOnSprint(s.getId()).toList()).map(
                Mapping::toSprintDTO
        ).toList();

    }

    @Transactional
    @SneakyThrows
    public void delete(long idSprint) {
        sprintSubService.delete(idSprint);
    }
}
