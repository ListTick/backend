package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shopping_list")
@Data
public class ShoppingList {

    @Id
    @UuidGenerator
    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;

    @PastOrPresent(message = "Creation date cannot be in the future")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @NotNull(message = "Cost factor cannot be null")
    @Min(message = "Cost factor minimum value is 0", value = 0)
    @Max(message = "Cost factor maximum value is 100", value = 100)
    @Column(name = "owner_cost_factor")
    private Integer ownerCostFactor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull(message = "Account id cannot be null")
    private UUID accountId;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "shoppingList")
    private List<SharedShoppingList> sharedShoppingLists;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE,
            mappedBy = "shoppingList")
    private List<Item> items;

}
