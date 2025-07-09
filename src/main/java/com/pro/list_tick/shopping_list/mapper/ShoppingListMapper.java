package com.pro.list_tick.shopping_list.mapper;


import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.model.ShoppingList;

import java.time.LocalDate;

public class ShoppingListMapper {

    private ShoppingListMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ShoppingListDTO toDTO(ShoppingList shoppingList) {
        ShoppingListDTO shoppingListDTO = new ShoppingListDTO();
        shoppingListDTO.setId(shoppingList.getId());
        shoppingListDTO.setActive(shoppingList.getActive());
        shoppingListDTO.setShared(shoppingList.getShared());
        shoppingListDTO.setName(shoppingList.getName());
        shoppingListDTO.setCreationDate(shoppingList.getCreationDate());
        shoppingListDTO.setCategoryId(shoppingList.getCategory().getId());
        shoppingListDTO.setAccountId(shoppingList.getAccountId());
        return shoppingListDTO;
    }

    public static ShoppingList toModel(ShoppingListInputDTO shoppingListInputDTO) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(shoppingListInputDTO.getName());
        shoppingList.setActive(Boolean.TRUE);
        shoppingList.setShared(shoppingListInputDTO.getShared());
        shoppingList.setCreationDate(LocalDate.now());
        return shoppingList;
    }

}
