package com.api.manager.service;

import com.api.manager.common.Mapping;
import com.api.manager.common.StatusObj;
import com.api.manager.dto.SprintDTO;
import com.api.manager.entity.SprintDb;
import com.api.manager.exception_handler_contoller.NotSavedResource;
import com.api.manager.repository.SprintRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SprintBoardService {
    private final SprintRepository sprintRepository;

    SprintBoardService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    private LocalDateTime getTimeExpired(Integer interval) {
        return LocalDateTime.now().plusDays(interval);
    }

    private boolean hasTimeExpired(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    public SprintDTO create(@NonNull SprintDTO sprintDTO, long idProject) {
        if (sprintDTO.getDaysInterval() <= 0) {
            throw new NotSavedResource("day <= 0", new Throwable());
        }
        SprintDb sprintDb = new SprintDb(getTimeExpired(sprintDTO.getDaysInterval()),
                sprintDTO.getDaysInterval(), idProject, StatusObj.TO_DO, sprintDTO.getPriority(), sprintDTO.getPurpose());
        return Mapping.toSprintDTO(sprintRepository.save(sprintDb));
    }

    public List<SprintDTO> getAll(long idProject) {
        return sprintRepository.findSprintDbByIdProject(idProject).stream().map(s -> {
            SprintDTO sprintDTO = Mapping.toSprintDTO(s);
            if (hasTimeExpired(s.getTimeExpired())) {
                sprintDTO.setStatus(StatusObj.EXPIRED);
            }
            return sprintDTO;
        }).toList();
    }
}
