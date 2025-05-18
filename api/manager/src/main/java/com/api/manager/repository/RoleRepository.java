package com.api.manager.repository;

import com.api.manager.common.GrantedRole;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.RoleDb;
import com.api.manager.entity.UserDb;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleDb, Long> {
    List<RoleDb> getAllByUserDb(UserDb userDb);

    Boolean existsByGrantedAndUserDbAndProjectDb(@NonNull GrantedRole granted, @NonNull UserDb userDb, @NonNull ProjectDb projectDb);
}
