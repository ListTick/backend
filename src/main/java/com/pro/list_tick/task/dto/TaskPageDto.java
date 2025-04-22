package com.pro.list_tick.task.dto;

import java.util.List;

public record TaskPageDto(
        int totalPages,
        long totalElements,
        List<TaskResponseDto> tasks
) {
}
