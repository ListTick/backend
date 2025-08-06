package com.pro.list_tick.task.controller;

import com.pro.list_tick.task.service.TaskGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/task-goal")
@RequiredArgsConstructor
public class TaskGoalController {
    private final TaskGoalService taskGoalService;

    @PostMapping("/connect")
    public void connectGoalToTask(UUID taskId, UUID goalId) {
        taskGoalService.connectGoalToTask(taskId, goalId);
    }

    @PostMapping("/disconnect")
    public void disconnectGoalFromTask(UUID taskId, UUID goalId) {
        taskGoalService.disconnectGoalFromTask(taskId, goalId);
    }
}
