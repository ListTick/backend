package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "shared_shopping_list")
@Data
public class SharedShoppingList {

    @EmbeddedId
    private SharedShoppingListId id;

    @ManyToOne
    @MapsId("shoppingListId")
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @NotNull(message = "Cost factor cannot be null")
    @Min(message = "Cost factor minimum value is 0", value = 0)
    @Max(message = "Cost factor maximum value is 100", value = 100)
    @Column(name = "cost_factor")
    private Integer costFactor;

    public UUID getAccountId() {
        return id != null ? id.getAccountId() : null;
    }

    public void setShoppingListAndAccount(ShoppingList shoppingList, UUID accountId) {
        if (this.id == null) {
            this.id = new SharedShoppingListId();
        }
        this.id.setShoppingListId(shoppingList.getId());
        this.id.setAccountId(accountId);
        this.shoppingList = shoppingList;
    }

    @Override
    public String toString() {
        return "SharedShoppingList{" +
            "id=" + id +
            ", costFactor=" + costFactor +
            '}';
    }

}
