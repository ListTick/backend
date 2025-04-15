package com.pro.list_tick.task.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TaskTagRequestDto(

        @NotNull
        UUID taskId,

        @NotNull
        UUID tagId
) {
}
