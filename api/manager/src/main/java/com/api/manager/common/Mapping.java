package com.api.manager.common;

import com.api.manager.auth.UserDetailImpl;
import com.api.manager.dto.*;
import com.api.manager.entity.*;
import jakarta.validation.constraints.NotNull;

public class Mapping {


    public static ProjectDTO toProjectDto(@NotNull ProjectDb projectDb) {
        return new ProjectDTO(projectDb.getId(), projectDb.getName(), toUserDto(projectDb.getCreator()), null);
    }

    public static UserDTO toUserDto(@NotNull UserDb userDb) {
        return new UserDTO(userDb.getId(), userDb.getName(), userDb.getEmail());
    }

    public static RoleDTO toRoleDTO(@NotNull RoleDb roleDb) {
        return new RoleDTO(toUserDto(roleDb.getUserDb()), roleDb.getGranted());
    }

    public static SprintDTO toSprintDTO(@NotNull SprintDb sprintDb) {
        return new SprintDTO(sprintDb.getId(), sprintDb.getTimeExpired(), sprintDb.getDays(), sprintDb.getIdProject(), sprintDb.getStatus(),
                sprintDb.getPriority(), sprintDb.getPurpose());
    }

    public static TaskDTO toTaskDTO(@NotNull TaskDb taskDb) {
        return new TaskDTO(taskDb.getId(), taskDb.getIdSprint(), taskDb.getStatus(), taskDb.getDetail());
    }

}
