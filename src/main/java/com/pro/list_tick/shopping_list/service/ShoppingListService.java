package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ShoppingListService {

    List<ShoppingListDTO> getAllByAccountId();
    ShoppingListDTO getById(UUID id);
    List<ItemDTO> getItemsByShoppingListId(UUID id);
    ShoppingListDTO create(ShoppingListInputDTO shoppingListInputDTO);
    ShoppingListDTO update(UUID id, ShoppingListUpdateDTO shoppingListUpdateDTO);
    ShoppingListDTO updateByFields(UUID id, ShoppingListUpdateDTO shoppingListUpdateDTO);
    void delete(UUID id);

}
