package com.pro.list_tick.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.UUID;

public record TaskResponseDto(
        UUID id,
        String name,
        Integer totalPomodoros,
        Integer completedPomodoros,
        Integer pomodoroDuration,
        Integer breakDuration,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dueDate,
        boolean isCompleted,
        TagResponseDto tag
) {
}
