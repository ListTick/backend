package com.pro.list_tick.task.dto;

import java.util.UUID;

public record TaskTagRequestDto(
        UUID taskId,
        UUID tagId
) {
}
