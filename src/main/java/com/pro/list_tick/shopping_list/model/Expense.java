package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "expense")
@Data
public class Expense {

    @Id
    @UuidGenerator
    private UUID id;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount cannot be negative")
    private Double amount;

    @NotBlank(message = "Currency cannot be blank")
    @Min(value = 3, message = "Currency has to have 3 characters")
    @Max(value = 3, message = "Currency has to have 3 characters")
    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;

    @NotNull(message = "Reimbursed cannot be null")
    private Boolean reimbursed;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "expense")
    private List<Item> items;

}
