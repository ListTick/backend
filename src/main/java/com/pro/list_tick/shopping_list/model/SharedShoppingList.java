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

@Entity
@Data
@Table(name = "account_shopping_list")
public class SharedShoppingList {

    @EmbeddedId
    private SharedShoppingListId id;

    @ManyToOne
    @MapsId("account")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @MapsId("shoppingList")
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    @NotNull(message = "Cost factor cannot be null")
    @Min(message = "Cost factor minimum value is 0", value = 0)
    @Max(message = "Cost factor maximum value is 100", value = 100)
    @Column(name = "cost_factor")
    private Integer costFactor;

    public void setShoppingListAndAccount(ShoppingList shoppingList, Account account) {
        this.shoppingList = shoppingList;
        this.account = account;
        this.id = new SharedShoppingListId();
        this.id.setShoppingList(shoppingList.getId());
        this.id.setAccount(account.getId());
    }

}
