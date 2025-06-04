package com.api.manager.common;

import com.api.manager.auth.UserDetailImpl;
import com.api.manager.dto.RoleDTO;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.RoleDb;
import com.api.manager.entity.UserDb;
import com.api.manager.dto.ProjectDTO;
import com.api.manager.dto.UserDTO;
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
}
