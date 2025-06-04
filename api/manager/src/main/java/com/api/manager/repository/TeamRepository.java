package com.api.manager.repository;

import com.api.manager.entity.TaskDb;
import com.api.manager.entity.TeamDb;
import org.hibernate.validator.internal.engine.resolver.JPATraversableResolver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamDb, Long> {
}
