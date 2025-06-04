package com.api.manager.common;


import com.api.manager.auth.UserDetailImpl;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.UserDb;
import com.api.manager.repository.RoleRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("inspectGrantedRole")
@Slf4j
public class InspectAccessUserProject {
    private final RoleRepository roleRepository;

    InspectAccessUserProject(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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

    public boolean hasSubSuperUser(@NonNull Long projectId) {
        return check(projectId, GrantedRole.SUB_SUPER_USER);
    }

    public boolean hasUserOnProject(@NonNull Long projectId) {
        return executeRequest(userDetail -> roleRepository.existsByUserDbAndProjectDb(new UserDb(userDetail.getId()), new ProjectDb(projectId)));
    }


}
