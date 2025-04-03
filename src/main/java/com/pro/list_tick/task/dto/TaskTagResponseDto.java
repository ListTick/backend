package com.pro.list_tick.task.dto;

import java.util.UUID;

public record TaskTagResponseDto(
        UUID taskId,
        UUID tagId
) {
}

