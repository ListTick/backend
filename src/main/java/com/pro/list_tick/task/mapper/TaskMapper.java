package com.pro.list_tick.task.mapper;

import com.pro.list_tick.task.dto.TaskRequestDto;
import com.pro.list_tick.task.dto.TaskResponseDto;
import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.model.Tag;
import com.pro.list_tick.task.model.Task;

import java.util.ArrayList;
import java.util.List;
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
                new ArrayList<>()
                );
    }
    public static TaskResponseDto toDto(Task task, List<Tag> tags) {
        return new TaskResponseDto(
                task.getId(),
                task.getName(),
                task.getTotalPomodoros(),
                task.getCompletedPomodoros(),
                task.getPomodoroDuration(),
                task.getBreakDuration(),
                task.getDueDate(),
                task.isCompleted(),
                TagMapper.toDto(tags)
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

        Account account = new Account();
        account.setId(userId);

        task.setAccount(account);

        return task;
    }
}
