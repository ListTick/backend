package com.pro.list_tick.bucket_list.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BucketListRequestUpdateDTO(

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name has to have between 3 and 255 characters")
    String name,

    @NotNull(message = "Active cannot be null")
    Boolean active,

    @NotNull(message = "Category cannot be null")
    UUID categoryId

) {
}
