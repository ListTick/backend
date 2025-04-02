package com.pro.list_tick.shopping_list.mapper;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.model.Item;

public class ItemMapper {

    private ItemMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setValue(item.getValue());
        itemDTO.setActive(item.getActive());
        itemDTO.setExpenseId(item.getExpense() != null ? item.getExpense().getId() : null);
        itemDTO.setShoppingListId(item.getShoppingList().getId());
        return itemDTO;
    }

    public static Item toModel(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setValue(itemDTO.getValue());
        item.setActive(itemDTO.getActive());
        return item;
    }

}
