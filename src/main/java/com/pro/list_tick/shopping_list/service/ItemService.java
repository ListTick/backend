package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ItemRequestDTO;
import com.pro.list_tick.shopping_list.dto.ItemResponseDTO;
import com.pro.list_tick.shopping_list.model.Item;

import java.util.List;
import java.util.UUID;

public interface ItemService {

    Item getById(UUID id);
    List<ItemResponseDTO> getAllByShoppingListId(UUID shoppingListId);
    ItemResponseDTO create(ItemRequestDTO itemRequestDTO);
    ItemResponseDTO update(UUID id, ItemRequestDTO itemRequestDTO);
    ItemResponseDTO updateByFields(UUID id, ItemRequestDTO itemRequestDTO);
    void deactivate(UUID id);

}
