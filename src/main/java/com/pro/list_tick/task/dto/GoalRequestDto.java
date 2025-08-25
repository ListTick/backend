package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record GoalRequestDto(

        @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
        String name,

        @Size(max = 512, message = "Description should be at most 512 characters")
        String description,

        @Min(value = 1, message = "Priority should be at least 1")
        @Max(value = 6, message = "Priority should be at most 6")
        Integer priority,

        @FutureOrPresent(message = "Start date cannot be in the past")
        LocalDate startDate,

        @FutureOrPresent(message = "End date cannot be in the past")
        LocalDate endDate,
        LocalDate realizationDate
) {
}
