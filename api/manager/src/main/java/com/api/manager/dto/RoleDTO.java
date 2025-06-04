package com.api.manager.dto;

import com.api.manager.common.GrantedRole;
import com.api.manager.common.GrantedRoleConverter;
import com.api.manager.entity.UserDb;
import jakarta.persistence.Convert;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NonNull;

@Data
public class RoleDTO {
    @NonNull
    private UserDTO user;

    @NonNull
    private GrantedRole granted;
}
