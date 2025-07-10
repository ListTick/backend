package com.pro.list_tick.shopping_list.mapper;


import com.pro.list_tick.shopping_list.dto.ShoppingListResponseDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListRequestDTO;
import com.pro.list_tick.shopping_list.model.ShoppingList;

import java.time.LocalDate;

public class ShoppingListMapper {

    private ShoppingListMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ShoppingListResponseDTO toResponseDTO(ShoppingList shoppingList) {
      return new ShoppingListResponseDTO(
          shoppingList.getId(),
          shoppingList.getName(),
          shoppingList.getActive(),
          shoppingList.getShared(),
          shoppingList.getCreationDate(),
          CategoryMapper.toResponseDTO(shoppingList.getCategory()),
          shoppingList.getAccountId()
      );
    }

    public static ShoppingList toModel(ShoppingListRequestDTO shoppingListRequestDTO) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(shoppingListRequestDTO.name());
        shoppingList.setActive(Boolean.TRUE);
        shoppingList.setShared(shoppingListRequestDTO.shared());
        shoppingList.setCreationDate(LocalDate.now());
        return shoppingList;
    }

}
