package com.pro.list_tick.shopping_list.model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class SharedShoppingListId implements Serializable {

    private UUID account;

    private UUID shoppingList;

}
