package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record TaskRequestDto(

        @NotBlank
        @Size(min = 1, max = 255, message = "Task name should be between 1 and 255 characters")
        String name,

        @Min(value = 1, message = "There should be at least 1 pomodoro")
        Integer totalPomodoros,

        @PositiveOrZero(message = "Completed pomodoros should not be negative")
        Integer completedPomodoros,

        @Min(value = 5, message = "Pomodoro duration should be at least 5")
        @Max(value = 90, message = "Pomodoro duration should be at max 90")
        Integer pomodoroDuration,

        @Min(value = 5, message = "Break duration should be at least 5")
        @Max(value = 30, message = "Break duration should be at max 30")
        Integer breakDuration,

        @Future(message = "Due date should be in the future")
        LocalDate dueDate,

        boolean isCompleted,
        boolean isDeleted,
        UUID tagId
) {
}
