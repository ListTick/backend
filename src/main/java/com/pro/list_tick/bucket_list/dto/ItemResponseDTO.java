package com.pro.list_tick.bucket_list.dto;

import java.util.UUID;

import jakarta.annotation.Nullable;

public record ItemResponseDTO (
    UUID id,
    String name
) {
}
