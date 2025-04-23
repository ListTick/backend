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
@Data
@Table(name = "account_shopping_list")
public class SharedShoppingList {

    @EmbeddedId
    private SharedShoppingListId id;

    @MapsId("account")
    private UUID accountId;

    @ManyToOne
    @MapsId("shoppingList")
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @NotNull(message = "Cost factor cannot be null")
    @Min(message = "Cost factor minimum value is 0", value = 0)
    @Max(message = "Cost factor maximum value is 100", value = 100)
    @Column(name = "cost_factor")
    private Integer costFactor;

    public void setShoppingListAndAccount(ShoppingList shoppingList, UUID accountId) {
        this.shoppingList = shoppingList;
        this.accountId = accountId;
        this.id = new SharedShoppingListId();
        this.id.setShoppingList(shoppingList.getId());
        this.id.setAccount(accountId);
    }

}
