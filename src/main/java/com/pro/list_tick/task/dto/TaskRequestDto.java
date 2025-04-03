package com.pro.list_tick.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TaskRequestDto(

        @NotBlank
        String name,

        @Min(value = 1, message = "There should be at least 1 pomodoro")
        Integer totalPomodoros,

        @PositiveOrZero
        Integer completedPomodoros,

        @Min(value = 5, message = "Pomodoro duration should be at least 5")
        @Max(value = 90, message = "Pomodoro duration should be at max 90")
        Integer pomodoroDuration,

        @Min(value = 5, message = "Break duration should be at least 5")
        @Max(value = 30, message = "Break duration should be at max 30")
        Integer breakDuration,

        @Future
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dueDate,
        boolean isCompleted,
        boolean isDeleted,
        List<UUID> tagIds
) {
}
