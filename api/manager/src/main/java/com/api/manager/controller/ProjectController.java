package com.api.manager.controller;

import com.api.manager.auth.UserDetailImpl;
import com.api.manager.common.SecurityUtils;
import com.api.manager.model.dto.ProjectDTO;
import com.api.manager.model.dto.UserDTO;
import com.api.manager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//auth/authenticate
//project/get_all
//project/create
//eyJhbGciOiJIUzUxMiJ9.eyJJRCI6NDEsInN1YiI6InRlc3QzNDIyIiwiaWF0IjoxNzQ0NjI2MTgyLCJleHAiOjE3NDQ2NDQxODJ9.0RQQ4ypzoa2F61YWvdUvTEQEmdD8p_KxEeYoDydnoVZBVBIVOupFxzlzGqct1Xghc3jU16o7eQDW6wXRZ17o-Q
//{
//    "login":"test3422",
//    "password":12345678,
//    "name":"test423"
//}
@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/get_all")
    @ResponseBody
    List<ProjectDTO> getAll() {
        return projectService.getAll(SecurityUtils.getCurrentUserDetail());
    }

    @PostMapping("/create")
    @ResponseBody
    ProjectDTO create(@RequestBody @Valid ProjectDTO projectDTO) {
        return projectService.create(projectDTO, SecurityUtils.getCurrentUserDetail());
    }

    @PostMapping("/{project_id}/update")
    @ResponseBody
    @PreAuthorize("@inspectGrantedRole.hasSuperUser(#projectId)")
    ProjectDTO save(@PathVariable("project_id") Long projectId, @RequestBody @Valid ProjectDTO projectDTO) {
        return projectService.save(projectDTO, projectId);
    }
}
