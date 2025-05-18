package com.api.manager.service;

import com.api.manager.auth.UserDetailImpl;
import com.api.manager.common.GrantedRole;
import com.api.manager.common.Mapping;
import com.api.manager.exception_handler_contoller.NotGetObjException;
import com.api.manager.exception_handler_contoller.NotSavedProject;
import com.api.manager.exception_handler_contoller.NotSavedStoreUserException;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.RoleDb;
import com.api.manager.entity.UserDb;
import com.api.manager.dto.ProjectDTO;
import com.api.manager.repository.MetaRepository;
import com.api.manager.repository.ProjectRepository;
import com.api.manager.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class ProjectService {
    private final RoleRepository roleRepository;
    private final ProjectRepository projectRepository;

    private final MetaRepository metaRepository;

    ProjectService(ProjectRepository projectRepository, RoleRepository roleRepository, MetaRepository repository) {
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
        this.metaRepository = repository;
    }

    //ReWrite
    public List<ProjectDTO> getAll(@NonNull UserDetailImpl userDetail) {
        try {
            return roleRepository.getAllByUserDb(new UserDb(userDetail.getId())).stream().map(r ->
                    {
                        ProjectDTO projectDto = Mapping.toProjectDto(r.getProjectDb());
                        try {
                            projectDto.setMetaDB(metaRepository.findById(r.getId()).orElseThrow());
                        } catch (Exception ex) {
                            log.error(ex.getMessage());
                        }
                        return projectDto;
                    }
            ).toList();
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new InternalException(ex.getMessage());
        }
    }

    @Transactional
    public ProjectDTO create(ProjectDTO projectDTO, @NonNull UserDetailImpl userDetail) {
        try {
            UserDb userDb = new UserDb(userDetail.getId());
            userDb.setName(userDetail.getName());
            ProjectDb projectDb = projectRepository.save(new ProjectDb(projectDTO.getName(), userDb));
            RoleDb roleDb = new RoleDb(userDb, GrantedRole.SUPER_USER, projectDb);
            roleRepository.save(roleDb);
            return Mapping.toProjectDto(projectDb);
        } catch (Exception ex) {
            throw new InternalException(ex.getMessage());
        }
    }

    @Transactional
    public ProjectDTO save(ProjectDTO projectDTO, Long idProject) {
        try {
            ProjectDb projectDb = projectRepository.findById(idProject).orElseThrow();
            projectDb.setName(projectDTO.getName());
            projectDb = projectRepository.save(projectDb);
            return Mapping.toProjectDto(projectDb);
        } catch (Exception ex) {
            if (ex instanceof NoSuchElementException) {
                throw new NotSavedProject(ex.getMessage(), ex);
            }
            throw new InternalException(ex.getMessage());
        }
    }


}
