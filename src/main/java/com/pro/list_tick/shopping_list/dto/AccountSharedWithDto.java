package com.pro.list_tick.shopping_list.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountSharedWithDto {

    @Email
    private String email;

    @NotNull
    @Min(value = 0, message = "Minimum value is 0.")
    @Max(value = 100, message = "Maximum value is 100.")
    private Integer costFactor;

}
