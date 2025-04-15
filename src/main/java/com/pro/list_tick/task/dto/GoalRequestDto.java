package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record GoalRequestDto(

        @NotNull(message = "Name cannot be null")
        String name,

        @Size(max = 512, message = "Description should be less than 512 characters")
        String description,
        Integer priority,

        @FutureOrPresent(message = "Start date cannot be in the past")
        LocalDate startDate,

        @FutureOrPresent(message = "End date cannot be in the past")
        LocalDate endDate,
        LocalDate realizationDate
) {
}
