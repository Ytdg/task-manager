package com.api.manager.repository;

import com.api.manager.entity.TaskDb;
import com.api.manager.entity.TeamDb;
import lombok.NonNull;
import org.hibernate.validator.internal.engine.resolver.JPATraversableResolver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamDb, Long> {
    Optional<TeamDb> getTeamDbByTaskDb(@NonNull TaskDb taskDb);
}
