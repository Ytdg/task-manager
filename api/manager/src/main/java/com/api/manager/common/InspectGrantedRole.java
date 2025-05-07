package com.api.manager.common;


import com.api.manager.auth.UserDetailImpl;
import com.api.manager.model.ProjectDb;
import com.api.manager.model.RoleDb;
import com.api.manager.model.UserDb;
import com.api.manager.repository.RoleRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("inspectGrantedRole")
@Slf4j
public class InspectGrantedRole {
    private final RoleRepository roleRepository;

    InspectGrantedRole(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private boolean check(Long projectId, GrantedRole grantedRole) {
        try {
            UserDetailImpl userDetail = SecurityUtils.getCurrentUserDetail();
            return roleRepository.existsByGrantedAndUserDbAndProjectDb(grantedRole, new UserDb(userDetail.getId()), new ProjectDb(projectId));
        } catch (Exception ex) {

            log.error(ex.getMessage());
            return false;
        }
    }

    public boolean hasSuperUser(@NonNull Long projectId) {

        return check(projectId, GrantedRole.SUPER_USER);
    }

    public boolean hasSubSuperUser(@NonNull Long projectId) {
        return check(projectId, GrantedRole.SUB_SUPER_USER);
    }

}
