package com.pro.list_tick.shopping_list.mapper;

import java.util.Objects;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.dto.ItemNameDTO;
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
        item.setName(itemDTO.getName());
        item.setValue(itemDTO.getValue());
        item.setActive(itemDTO.getActive());
        return item;
    }

    public static ItemNameDTO toItemNameDto(Item item) {
        ItemNameDTO itemNameDTO = new ItemNameDTO();
        itemNameDTO.setId(item.getId());
        itemNameDTO.setName(item.getName());
        if (Objects.nonNull(item.getValue())) {
            itemNameDTO.setValue(item.getValue());
        }
        return itemNameDTO;
    }

}
