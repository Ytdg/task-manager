package com.api.manager.controller;

import com.api.manager.service.TeamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team/{project_id}")
@Tag(name = "TeamController", description = "Контреллер отвечает за обработку команд. Требуется аутентификациия")
public class TeamController {
    private final TeamService teamService;

    TeamController(TeamService teamService) {
        this.teamService = teamService;
    }



}
