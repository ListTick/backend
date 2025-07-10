package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ShoppingListResponseDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListRequestDTO;
import com.pro.list_tick.shopping_list.dto.ShoppingListRequestUpdateDTO;
import com.pro.list_tick.shopping_list.model.ShoppingList;

import java.util.List;
import java.util.UUID;

public interface ShoppingListService {

    ShoppingList getById(UUID id);
    List<ShoppingListResponseDTO> getAllDTOByAccountId();
    ShoppingListResponseDTO create(ShoppingListRequestDTO shoppingListRequestDTO);
    ShoppingListResponseDTO update(UUID id, ShoppingListRequestUpdateDTO shoppingListRequestUpdateDTO);
    ShoppingListResponseDTO updateByFields(UUID id, ShoppingListRequestUpdateDTO shoppingListRequestUpdateDTO);
    void delete(UUID id);
    Boolean validateAccess(UUID accountId, ShoppingList shoppingList);
    Boolean validateSharedAccess(UUID accountId, ShoppingList shoppingList);

}
