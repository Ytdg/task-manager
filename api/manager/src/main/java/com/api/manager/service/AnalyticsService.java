package com.api.manager.service;

import com.api.manager.common.StatusObj;
import com.api.manager.dto.AnalyticsDTO;
import com.api.manager.entity.SprintDb;
import com.api.manager.entity.TaskDb;
import com.api.manager.repository.SprintRepository;
import com.api.manager.repository.TaskBoardRepository;
import com.api.manager.repository.TeamRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalyticsService {

    private final SprintRepository sprintRepository;
    private final TaskBoardRepository taskBoardRepository;

    AnalyticsService(SprintRepository sprintRepository, TaskBoardRepository taskBoardRepository,
                     TeamRepository teamRepository) {
        this.sprintRepository = sprintRepository;
        this.taskBoardRepository = taskBoardRepository;
    }

    public AnalyticsDTO getAnalytics(@NonNull Long projectId) {
        AnalyticsDTO analyticsDTO = new AnalyticsDTO();

        // Получаем спринты проекта
        List<SprintDb> sprintDbs = sprintRepository.findSprintDbByIdProject(projectId);

        // Получаем задачи для этих спринтов
        List<TaskDb> taskDbs = taskBoardRepository.findBySprintDbIn(sprintDbs);

        // Считаем общее количество задач
        int totalTasks = taskDbs.size();
        analyticsDTO.setCountTask(totalTasks);

        // Считаем количество задач в статусе TO_DO и COMPLETE
        long toDoTasks = taskDbs.stream().filter(taskDb -> taskDb.getStatus() == StatusObj.TO_DO).count();
        long completedTasks = taskDbs.stream().filter(taskDb -> taskDb.getStatus() == StatusObj.COMPLETE).count();

        analyticsDTO.setCountTaskInToDo((int) toDoTasks);
        analyticsDTO.setCountTaskCompleted((int) completedTasks);

        // Считаем процент завершенных спринтов
        double perCompletedSprint = (double) sprintDbs.stream()
                .filter(s -> s.getStatus() == StatusObj.COMPLETE)
                .count() / sprintDbs.size() * 100;

        // Форматируем процент
        String sprintCompletedPer = (sprintDbs.isEmpty()) ? "0%" : String.format("%.2f%%", perCompletedSprint);

        analyticsDTO.setSprintCompletedPer(sprintCompletedPer);

        return analyticsDTO;
    }

}

