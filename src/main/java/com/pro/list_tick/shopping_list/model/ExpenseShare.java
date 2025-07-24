package com.pro.list_tick.shopping_list.model;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "expense_share")
@Data
public class ExpenseShare {

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

  @Column(name = "account_id")
  private UUID accountId;

  @ManyToOne
  @JoinColumn(name = "expense_id")
  private Expense expense;

  @Override
  public String toString() {
    return "ExpenseShare{" +
        "id=" + id +
        ", amount=" + amount +
        ", currency='" + currency + '\'' +
        ", reimbursed=" + reimbursed +
        ", accountId=" + accountId +
        '}';
  }

}
