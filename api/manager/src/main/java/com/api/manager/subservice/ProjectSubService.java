package com.api.manager.subservice;

import com.api.manager.auth.UserDetailImpl;
import com.api.manager.common.Mapping;
import com.api.manager.dto.ProjectDTO;
import com.api.manager.entity.MetaEntity;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.RoleDb;
import com.api.manager.entity.UserDb;
import com.api.manager.exception_handler_contoller.NotSavedResource;
import com.api.manager.repository.MetaRepository;
import com.api.manager.repository.ProjectRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.logging.log4j.util.InternalException;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * SubService-class
 */
@Component
@Slf4j
public class ProjectSubService {
    private final MetaRepository metaRepository;
    private final ProjectRepository projectRepository;

    ProjectSubService(MetaRepository metaRepository, ProjectRepository projectRepository) {
        this.metaRepository = metaRepository;
        this.projectRepository = projectRepository;
    }

    public Stream<Pair<ProjectDb, Optional<MetaEntity>>> getAllByRole(@NonNull Stream<RoleDb> roleDbStream) {

        return roleDbStream
                .map(r -> new Pair<>(r.getProjectDb(), metaRepository.findById(r.getId())));

    }

    public Pair<ProjectDb, Optional<MetaEntity>> get(long idProject) {
        return new Pair<>(projectRepository.findById(idProject).orElseThrow(), metaRepository.findById(idProject));

    }

    //time format
    public Pair<ProjectDb, MetaEntity> create(@NonNull ProjectDb projectDb) {

        ProjectDb projectDbSave = projectRepository.save(projectDb);
        MetaEntity metaEntity = metaRepository.save(new MetaEntity(projectDbSave.getId(), LocalDateTime.now()));
        return new Pair<>(projectDbSave, metaEntity);

    }


    public void delete(long idProject) {
        if (exist(idProject)) {
            projectRepository.deleteById(idProject);
            return;
        }
        throw new NoSuchElementException("Project not Found");
    }

    public Pair<ProjectDb, MetaEntity> save(@NonNull ProjectDb projectDb) {
        if (exist(projectDb.getId())) {
            projectDb = projectRepository.save(projectDb);
            MetaEntity metaEntity = metaRepository.findById(projectDb.getId()).orElseThrow();
            return new Pair<>(projectDb, metaEntity);
        }
        throw new NoSuchElementException("Project not found");
    }

    public boolean exist(long idProject) {
        return projectRepository.existsById(idProject);
    }
}
