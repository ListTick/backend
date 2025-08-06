package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private CurrencyCode currency;

    @NotNull(message = "Reimbursed cannot be null")
    private Boolean reimbursed;

    @NotNull(message = "Shared cannot be null")
    private Boolean shared;

    @PastOrPresent(message = "Creation date cannot be in the future")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "expense")
    private List<Item> items;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "expense")
    private List<ExpenseShare> expenseShares;

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
