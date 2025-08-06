package com.pro.list_tick.shopping_list.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
public class SharedShoppingListId implements Serializable {

    @Column(name = "account_id")
    private UUID accountId;

    @Column(name = "shopping_list_id")
    private UUID shoppingListId;

}
