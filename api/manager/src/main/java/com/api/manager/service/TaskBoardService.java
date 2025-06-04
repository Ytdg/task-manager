package com.api.manager.service;

import com.api.manager.auth.UserDetailImpl;
import com.api.manager.common.Mapping;
import com.api.manager.common.StatusObj;
import com.api.manager.dto.TaskDTO;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.RoleDb;
import com.api.manager.entity.TaskDb;
import com.api.manager.entity.UserDb;
import com.api.manager.repository.RoleRepository;
import com.api.manager.repository.SprintRepository;
import com.api.manager.repository.TaskBoardRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TaskBoardService {
    private final TaskBoardRepository taskBoardRepository;
    private final SprintRepository sprintRepository;
    private final RoleRepository roleRepository;

    TaskBoardService(TaskBoardRepository taskBoardRepository, SprintRepository sprintRepository, RoleRepository roleRepository) {
        this.taskBoardRepository = taskBoardRepository;
        this.sprintRepository = sprintRepository;
        this.roleRepository = roleRepository;
    }

    public TaskDTO create(@NonNull TaskDTO taskDTO, @NonNull Long idSpring) {
        TaskDb taskDb = new TaskDb(idSpring, StatusObj.TO_DO, taskDTO.getDetail());
        return Mapping.toTaskDTO(taskBoardRepository.save(taskDb));
    }

  /*  public List<TaskDTO> getAll(@NonNull Long idSpring, @NonNull UserDetailImpl userDetail, @NonNull Long idProject) {

    }*/
}


