package com.pro.list_tick.bucket_list.dto;

import java.util.List;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BucketListRequestDTO(

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name has to have between 3 and 255 characters")
    String name,

    @Nullable
    Boolean active,

    @NotNull(message = "Category cannot be null")
    UUID categoryId,

    @NotNull(message = "Shared cannot be null")
    Boolean shared,

    @Nullable
    List<AccountSharedWithRequestDto> sharedWithAccounts

) {
}
