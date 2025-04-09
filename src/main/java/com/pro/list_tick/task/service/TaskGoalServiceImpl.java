package com.pro.list_tick.task.service;

import com.pro.list_tick.task.model.Goal;
import com.pro.list_tick.task.model.Task;
import com.pro.list_tick.task.repository.GoalRepository;
import com.pro.list_tick.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskGoalServiceImpl implements TaskGoalService {
    private final TaskRepository taskRepository;
    private final GoalRepository goalRepository;

    @Override
    public void connectGoalToTask(UUID taskId, UUID goalId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task doesn't exist!")); //TODO custom exception

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal doesn't exist!")); //TODO custom exception

        task.setGoal(goal);

        taskRepository.save(task);
    }

    @Override
    public void disconnectGoalFromTask(UUID taskId, UUID goalId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task doesn't exist!")); //TODO custom exception

        task.setGoal(null);

        taskRepository.save(task);
    }
}
