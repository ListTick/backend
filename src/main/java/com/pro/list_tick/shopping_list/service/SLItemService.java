package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.ItemRequestDTO;
import com.pro.list_tick.shopping_list.dto.ItemRequestUpdateDTO;
import com.pro.list_tick.shopping_list.dto.ItemResponseDTO;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.Item;

import java.util.List;
import java.util.UUID;

public interface SLItemService {

    Item getById(UUID id);
    List<ItemResponseDTO> getAllByShoppingListId(UUID shoppingListId);
    ItemResponseDTO create(ItemRequestDTO itemRequestDTO);
    ItemResponseDTO update(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO);
    ItemResponseDTO updateByFields(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO);
    void deactivate(UUID id);
    Item addExpense(UUID id, Expense expense);

}
