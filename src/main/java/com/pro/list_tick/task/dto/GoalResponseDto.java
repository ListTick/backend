package com.pro.list_tick.task.dto;

import java.time.LocalDate;
import java.util.UUID;

public record GoalResponseDto(
        UUID id,
        String description,
        Integer priority,
        LocalDate startDate,
        LocalDate endDate,
        LocalDate realizationDate
) {
}
