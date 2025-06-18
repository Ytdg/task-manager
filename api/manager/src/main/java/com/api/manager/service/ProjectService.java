package com.api.manager.service;

import ch.qos.logback.core.util.StringUtil;
import com.api.manager.auth.UserDetailImpl;
import com.api.manager.common.CryptMeta;
import com.api.manager.common.GrantedRole;
import com.api.manager.common.Mapping;
import com.api.manager.common.SharedURLField;
import com.api.manager.dto.RoleDTO;
import com.api.manager.entity.MetaEntity;
import com.api.manager.exception_handler_contoller.NotSavedResource;
import com.api.manager.exception_handler_contoller.NotSavedStoreUserException;
import com.api.manager.entity.ProjectDb;
import com.api.manager.entity.RoleDb;
import com.api.manager.entity.UserDb;
import com.api.manager.dto.ProjectDTO;
import com.api.manager.repository.MetaRepository;
import com.api.manager.repository.ProjectRepository;
import com.api.manager.repository.RoleRepository;
import com.api.manager.subservice.ProjectSubService;
import com.api.manager.subservice.RoleSubService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;


@Service
@Slf4j
public class ProjectService {

    private final ProjectSubService projectSubService;
    private final RoleSubService roleSubService;

    ProjectService(ProjectSubService projectSubService, RoleSubService roleSubService) {
        this.projectSubService = projectSubService;
        this.roleSubService = roleSubService;
    }

    @SneakyThrows
    public List<ProjectDTO> getAll(@NonNull UserDetailImpl userDetail) {

        return projectSubService.getAllByRole(roleSubService.getAllByUser(new UserDb(userDetail.getId()))).map(s -> {
            ProjectDTO projectDTO = Mapping.toProjectDto(s.a, s.b.orElse(null));
            s.b.ifPresent(projectDTO::setMetaEntity);
            return projectDTO;
        }).toList();
    }

    @SneakyThrows
    public ProjectDTO get(long idProject) {
        Pair<ProjectDb, Optional<MetaEntity>> val = projectSubService.get(idProject);
        ProjectDTO projectDTO = Mapping.toProjectDto(val.a, val.b.orElse(null));
        val.b.ifPresent(projectDTO::setMetaEntity);
        return projectDTO;
    }


    @Transactional
    @SneakyThrows
    public ProjectDTO create(@NonNull ProjectDTO projectDTO, @NonNull UserDetailImpl userDetail) {


        UserDb userDb = new UserDb(userDetail.getId());
        userDb.setName(userDetail.getName());
        Pair<ProjectDb, MetaEntity> projectDb = projectSubService.create(new ProjectDb(projectDTO.getName(), userDb));
        RoleDb roleDb = new RoleDb(userDb, GrantedRole.SUPER_USER, projectDb.a);
        roleSubService.create(roleDb);
        return Mapping.toProjectDto(projectDb.a, projectDb.b);

    }

    @Transactional
    public void delete(long idProject) {
        projectSubService.delete(idProject);
    }

    @Transactional
    public ProjectDTO save(@NonNull ProjectDTO projectDTO, long idProject) {
        Pair<ProjectDb, Optional<MetaEntity>> projectDb = projectSubService.get(idProject);
        projectDb.a.setName(projectDTO.getName());
        Pair<ProjectDb, MetaEntity> saveProject = projectSubService.save(projectDb.a);
        return Mapping.toProjectDto(saveProject.a, saveProject.b);
    }

    @SneakyThrows
    public String getUrlShared(@NonNull GrantedRole requiredRole, long idProject) {
        if (!projectSubService.exist(idProject)) {
            throw new NoSuchElementException("Project not Found");
        }
        return CryptMeta.encryptMap((Map.of(SharedURLField.ROLE.name(), requiredRole.name(), SharedURLField.PROJECT_ID.name(),
                String.valueOf(idProject))));
    }

    @SneakyThrows
    public List<RoleDTO> getUsersRoleOnProject(@NonNull Long idProject) {
        return roleSubService.getAllRoleUsersByProject(new ProjectDb(idProject)).map(Mapping::toRoleDTO).toList();
    }

    //refactoring
    @Transactional
    public long assignToProject(@NonNull String token, @NonNull UserDetailImpl userDetail) {

        Optional<Map<String, String>> param = CryptMeta.decryptMap(token);
        if (param.isEmpty()) {
            throw new NoSuchElementException("Incorrect param");
        }

        Function<Map<String, String>, Optional<Map.Entry<GrantedRole, Long>>> extract = m -> {
            if (m.size() != 2) {
                return Optional.empty();
            }
            try {
                return Optional.of(new AbstractMap.SimpleEntry<>(GrantedRole.valueOf(m.get(SharedURLField.ROLE.name())),
                        Long.parseLong(m.get(SharedURLField.PROJECT_ID.name()))));
            } catch (Exception e) {
                return Optional.empty();
            }
        };
        Map.Entry<GrantedRole, Long> values = extract.apply(param.get()).orElseThrow();
        UserDb userDb = new UserDb(userDetail.getId());
        ProjectDb projectDb = new ProjectDb(values.getValue());
        roleSubService.create(new RoleDb(userDb, values.getKey(), projectDb));
        return projectDb.getId();

    }

}
