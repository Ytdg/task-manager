package com.api.manager.subservice;

import com.api.manager.common.Mapping;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.RoleDb;
import com.api.manager.entity.UserDb;
import com.api.manager.exception_handler_contoller.NotSavedStoreUserException;
import com.api.manager.repository.RoleRepository;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

/**
 * SubService-class
 */
@Component
public class RoleSubService {
    private final RoleRepository roleRepository;

    RoleSubService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Stream<RoleDb> getAllByUser(@NonNull UserDb userDb) {
        return roleRepository.getAllByUserDb(userDb).stream();
    }

    public RoleDb create(@NonNull RoleDb roleDb) {

        if (!existsByUserDbAndProjectDb(roleDb.getUserDb(), roleDb.getProjectDb())) {
            return roleRepository.save(roleDb);
        }
        throw new NotSavedStoreUserException("There is already such a role on the project.", new Throwable());

    }

    public Stream<RoleDb> getAllRoleUsersByProject(@NonNull ProjectDb projectDb) {
        return roleRepository.getAllByProjectDb(projectDb).stream();

    }

    public boolean existsByUserDbAndProjectDb(@NonNull UserDb userDb, @NonNull ProjectDb projectDb) {
        return roleRepository.existsByUserDbAndProjectDb(userDb, projectDb);
    }


}
