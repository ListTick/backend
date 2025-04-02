package com.pro.list_tick.shopping_list.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class AccountSharedWithDto {

    @Size(min = 36, max = 36, message = "Not valid id format.")
    private UUID uuid;

    @NotNull
    @Min(value = 0, message = "Minimum value is 0.")
    @Max(value = 100, message = "Maximum value is 100.")
    private Integer costFactor;

}
