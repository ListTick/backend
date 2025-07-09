package com.pro.list_tick.shopping_list.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "item")
@Data
public class Item {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name has to have 3-255 characters")
    private String name;

    @Nullable
    @Positive(message = "Value cannot be negative")
    private Double value;

    @NotNull(message = "Active field cannot be null")
    private Boolean active;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

}
