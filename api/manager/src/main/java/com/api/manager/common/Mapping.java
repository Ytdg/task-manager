package com.api.manager.common;

import com.api.manager.auth.UserDetailImpl;
import com.api.manager.model.ProjectDb;
import com.api.manager.model.UserDb;
import com.api.manager.model.dto.ProjectDTO;
import com.api.manager.model.dto.UserDTO;
import jakarta.validation.constraints.NotNull;

public class Mapping {
    public static UserDTO toUserDto(@NotNull UserDetailImpl userDetail) {
        return new UserDTO(userDetail.getId(), userDetail.getUsername());
    }

    public static ProjectDTO toProjectDto(@NotNull ProjectDb projectDb) {
        return new ProjectDTO(projectDb.getId(), projectDb.getName(), toUserDto(projectDb.getCreator()), null);
    }

    public static UserDTO toUserDto(@NotNull UserDb userDb) {
        return new UserDTO(userDb.getId(), userDb.getName());
    }
}
