package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListInputDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListUpdateDTO;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.model.ShoppingList;

import java.util.List;
import java.util.UUID;

public interface ShoppingListService {

    ShoppingList getById(UUID id);
    List<ShoppingListDTO> getAllDTOByAccountId();
    ShoppingListDTO create(ShoppingListInputDTO shoppingListInputDTO);
    ShoppingListDTO update(UUID id, ShoppingListUpdateDTO shoppingListUpdateDTO);
    ShoppingListDTO updateByFields(UUID id, ShoppingListUpdateDTO shoppingListUpdateDTO);
    void delete(UUID id);
    List<ItemDTO> getItemsByShoppingListId(UUID id);
    Boolean validateAccess(UUID accountId, ShoppingList shoppingList);
    Boolean validateSharedAccess(UUID accountId, ShoppingList shoppingList);

}
