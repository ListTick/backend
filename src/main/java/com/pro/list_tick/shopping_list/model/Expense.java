package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;

    @NotNull(message = "Reimbursed cannot be null")
    private Boolean reimbursed;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "expense")
    private List<Item> items;

    @Override
    public String toString() {
        return "Expense{" +
            "id=" + id +
            ", amount=" + amount +
            ", currency='" + currency + '\'' +
            ", reimbursed=" + reimbursed +
            '}';
    }

}
