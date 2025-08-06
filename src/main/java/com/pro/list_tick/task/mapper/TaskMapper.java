package com.pro.list_tick.task.mapper;

import com.pro.list_tick.task.dto.TaskRequestDto;
import com.pro.list_tick.task.dto.TaskResponseDto;
import com.pro.list_tick.task.model.Task;

import java.util.UUID;

public class TaskMapper {

    private TaskMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TaskResponseDto toDto(Task task) {
        return new TaskResponseDto(
            task.getId(),
            task.getName(),
            task.getTotalPomodoros(),
            task.getCompletedPomodoros(),
            task.getPomodoroDuration(),
            task.getBreakDuration(),
            task.getDueDate(),
            task.isCompleted(),
            task.getTag() != null ? TagMapper.toDto(task.getTag()) : null
        );
    }

    public static Task toEntity(TaskRequestDto taskDto, UUID userId) {
        Task task = new Task();

        task.setName(taskDto.name());
        task.setTotalPomodoros(taskDto.totalPomodoros());
        task.setCompletedPomodoros(taskDto.completedPomodoros());
        task.setPomodoroDuration(taskDto.pomodoroDuration());
        task.setBreakDuration(taskDto.breakDuration());
        task.setDueDate(taskDto.dueDate());
        task.setCompleted(false);
        task.setDeleted(false);
        task.setAccountId(userId);

        return task;
    }
}
