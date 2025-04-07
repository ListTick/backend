package com.pro.list_tick.task.service;

import com.pro.list_tick.task.dto.TaskPageDto;
import com.pro.list_tick.task.dto.TaskRequestDto;
import com.pro.list_tick.task.dto.TaskResponseDto;
import com.pro.list_tick.task.model.Task;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponseDto createTask(TaskRequestDto taskRequestDto);
    List<TaskResponseDto> getTasksByAccountId(List<String> tags);
    TaskPageDto getArchivedTasksByAccountId(Pageable pageable);
    TaskResponseDto updateTask(TaskRequestDto taskRequestDto, UUID taskId);
    void softDeleteTask(UUID taskId);
    TaskResponseDto updateCompletedPomodoros(Integer completedPomodoros, UUID taskId);
    void toggleTaskComplete(UUID taskId);
    void deleteAllCompletedTasks();
    Task findTaskById(UUID taskId);
}
