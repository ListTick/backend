package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;

import java.util.List;
import java.util.UUID;

public interface ShoppingListService {

    List<ShoppingListDTO> getAll();
    List<ShoppingListDTO> getAllByAccountId(UUID accountId);
    ShoppingListDTO getById(UUID id);
    List<ItemDTO> getItemsByShoppingListId(UUID id);
    ShoppingListDTO create(ShoppingListInputDTO shoppingListInputDTO);
    ShoppingListDTO update(UUID id, ShoppingListDTO shoppingListDTO);
    ShoppingListDTO updateByFields(UUID id, ShoppingListDTO shoppingListDTO);
    void delete(UUID id);

}
