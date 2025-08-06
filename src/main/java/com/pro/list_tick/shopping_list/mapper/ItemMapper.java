package com.pro.list_tick.shopping_list.mapper;

import java.util.Objects;

import com.pro.list_tick.shopping_list.dto.ItemRequestDTO;
import com.pro.list_tick.shopping_list.dto.ItemResponseDTO;
import com.pro.list_tick.shopping_list.model.Item;

public class ItemMapper {

    private ItemMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemResponseDTO toResponseDTO(Item item) {
        return new ItemResponseDTO(
            item.getId(),
            item.getName(),
            item.getValue()
        );
    }

    public static Item toModel(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();
        item.setName(itemRequestDTO.name());
        item.setValue(Objects.nonNull(itemRequestDTO.value()) ? itemRequestDTO.value() : null);
        item.setActive(Boolean.TRUE);
        return item;
    }

}
