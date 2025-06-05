package com.api.manager.service;

import com.api.manager.common.Mapping;
import com.api.manager.dto.RoleDTO;
import com.api.manager.entity.ProjectDb;
import com.api.manager.repository.HiredEmployeeRepository;
import com.api.manager.repository.RoleRepository;
import com.api.manager.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final HiredEmployeeRepository hiredEmployeeRepository;

    private final RoleRepository roleRepository;

    TeamService(TeamRepository teamRepository, HiredEmployeeRepository hiredEmployeeRepository, RoleRepository roleRepository) {
        this.teamRepository = teamRepository;
        this.hiredEmployeeRepository = hiredEmployeeRepository;
        this.roleRepository = roleRepository;
    }


}
