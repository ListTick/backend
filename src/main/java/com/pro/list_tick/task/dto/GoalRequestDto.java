package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record GoalRequestDto(

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 255, message = "Name should be between 1 and 255 characters")
        String name,

        @Size(max = 255, message = "Description should be at most 255 characters")
        String description,
        Integer priority,

        @FutureOrPresent(message = "Start date cannot be in the past")
        LocalDate startDate,

        @FutureOrPresent(message = "End date cannot be in the past")
        LocalDate endDate,
        LocalDate realizationDate
) {
}
