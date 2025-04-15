package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GoalRequestDto(

        @NotNull
        String name,
        String description,
        Integer priority,

        @FutureOrPresent
        LocalDate startDate,

        @Future
        LocalDate endDate,
        LocalDate realizationDate
) {
}
