package com.pro.list_tick.task.service;

import java.util.UUID;

public interface TaskGoalService {
    void connectGoalToTask(UUID taskId, UUID goalId);
    void disconnectGoalFromTask(UUID taskId, UUID goalId);
}
