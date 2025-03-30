package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.dialect.VarcharUUIDJdbcType;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "shopping_list")
public class ShoppingList {

    @Id
    @UuidGenerator
    @JdbcType(VarcharUUIDJdbcType.class)
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Min(value = 3, message = "Name has to have at least 3 characters long")
    @Max(value = 255, message = "Name cannot be more than 255 characters long")
    private String name;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;

    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDate creationDate;

    @NotNull(message = "Cost factor cannot be null")
    @Min(message = "Cost factor minimum value is 0", value = 0)
    @Max(message = "Cost factor maximum value is 100", value = 100)
    private Integer ownerCostFactor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
