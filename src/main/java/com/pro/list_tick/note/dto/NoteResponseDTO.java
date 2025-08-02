package com.pro.list_tick.note.dto;

import java.time.LocalDate;
import java.util.UUID;

public record NoteResponseDTO(
    UUID id,
    String name,
    LocalDate createdAt,
    LocalDate modifiedAt,
    String description,
    UUID accountId
) {
}
