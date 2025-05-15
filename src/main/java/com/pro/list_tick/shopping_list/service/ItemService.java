package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ItemDTO;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    List<ItemDTO> getAllByAccountId();
    List<ItemDTO> getAllByShoppingListId(UUID shoppingListId);
    ItemDTO getById(UUID id);
    ItemDTO create(ItemDTO itemDTO);
    ItemDTO update(ItemDTO itemDTO);
    ItemDTO updateByFields(ItemDTO itemDTO);
    void delete(UUID id);

}
