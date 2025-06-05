package com.api.manager.common;


import com.api.manager.auth.UserDetailImpl;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.UserDb;
import com.api.manager.repository.RoleRepository;
import com.api.manager.repository.SprintRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("inspectGrantedRole")
@Slf4j
public class InspectAccessUserProject {
    private final RoleRepository roleRepository;
    private final SprintRepository sprintRepository;

    InspectAccessUserProject(RoleRepository roleRepository, SprintRepository sprintRepository
    ) {
        this.roleRepository = roleRepository;
        this.sprintRepository = sprintRepository;
    }

    private boolean check(Long projectId, GrantedRole grantedRole) {
        return executeRequest(userDetail -> roleRepository.existsByGrantedAndUserDbAndProjectDb(grantedRole, new UserDb(userDetail.getId()), new ProjectDb(projectId)));
    }

    //outside
    interface FunctionEx<T, K> {
        T apply(K key) throws Exception;
    }

    private boolean executeRequest(FunctionEx<Boolean, UserDetailImpl> function) {
        try {
            return function.apply(SecurityUtils.getCurrentUserDetail());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }

    }

    public boolean hasSuperUser(@NonNull Long projectId) {
        return check(projectId, GrantedRole.SUPER_USER);
    }

    public boolean hasSuperUserOrSubSuperUser(@NonNull Long projectId) {
        return check(projectId, GrantedRole.SUPER_USER) || check(projectId, GrantedRole.SUB_SUPER_USER);
    }

    public boolean hasSuperUserOrSubSuperUserOrUser(@NonNull Long projectId) {
        return hasSuperUserOrSubSuperUser(projectId) || hasUserOnProject(projectId);
    }

    public boolean hasUserOnProject(@NonNull Long projectId) {
        return check(projectId, GrantedRole.USER);
    }

    public boolean isAccessSprintOnProjectAndSuperOrSubUser(@NonNull Long idProject, @NonNull Long sprintId) {
        return sprintRepository.existsByIdAndIdProject(sprintId, idProject) && hasSuperUserOrSubSuperUser(idProject);
    }



}
