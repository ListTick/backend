package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TaskTagRequestDto(

        @NotNull(message = "Task ID cannot be null")
        UUID taskId,

        @NotNull(message = "Tag ID cannot be null")
        UUID tagId
) {
}
