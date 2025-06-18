package com.api.manager.subservice;

import com.api.manager.common.Mapping;
import com.api.manager.common.StatusObj;
import com.api.manager.dto.SprintDTO;
import com.api.manager.entity.SprintDb;
import com.api.manager.entity.TaskDb;
import com.api.manager.exception_handler_contoller.NotSavedResource;
import com.api.manager.repository.SprintRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.cglib.core.internal.Function;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;


/**
 * SubService-class
 */
@Component
public class SprintSubService {
    private final SprintRepository sprintRepository;

    SprintSubService(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    private LocalDateTime getTimeExpired(Integer interval) {
        return LocalDateTime.now().plusDays(interval);
    }

    private boolean hasTimeExpired(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }

    public SprintDb create(@NonNull SprintDTO sprintDTO, long idProject) {
        if (sprintDTO.getDaysInterval() <= 0) {
            throw new NotSavedResource("day <= 0", new Throwable());
        }
        SprintDb sprintDb = new SprintDb(getTimeExpired(sprintDTO.getDaysInterval()),
                sprintDTO.getDaysInterval(), idProject, StatusObj.TO_DO, sprintDTO.getPriority(), sprintDTO.getPurpose());
        return sprintRepository.save(sprintDb);
    }

    public Stream<SprintDb> getAll(long idProject, @NonNull Function<SprintDb, Collection<TaskDb>> tasks) {
        return sprintRepository.findSprintDbByIdProject(idProject).stream().peek(s -> {
            Collection<TaskDb> taskDbs = tasks.apply(s);
            boolean isCompleted = !taskDbs.isEmpty() && taskDbs.stream().allMatch(task -> task.getStatus() == StatusObj.COMPLETE);
            if (isCompleted) {
                s.setStatus(StatusObj.COMPLETE);
            }
            if (hasTimeExpired(s.getTimeExpired())) {
                s.setStatus(StatusObj.EXPIRED);
            }
        });
    }

    public void delete(long idSprint) {
        SprintDb sprintDb = new SprintDb();
        sprintDb.setId(idSprint);
        if (!sprintRepository.existsById(idSprint)) {
            throw new NoSuchElementException("Sprint not found");
        }
        sprintRepository.delete(sprintDb);
    }
}
