package com.pro.list_tick.bucket_list.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public record BucketListResponseDTO(
    UUID id,
    String name,
    Boolean active,
    Boolean shared,
    LocalDate creationDate,
    CategoryResponseDTO category,
    UUID accountId,
    List<AccountSharedWithResponseDto> sharedWithAccounts
) {
}
