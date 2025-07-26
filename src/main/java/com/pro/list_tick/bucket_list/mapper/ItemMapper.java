package com.pro.list_tick.bucket_list.mapper;

import com.pro.list_tick.bucket_list.dto.ItemRequestDTO;
import com.pro.list_tick.bucket_list.dto.ItemResponseDTO;
import com.pro.list_tick.bucket_list.model.Item;

public class ItemMapper {

    private ItemMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static ItemResponseDTO toResponseDTO(Item item) {
        return new ItemResponseDTO(
            item.getId(),
            item.getName()
        );
    }

    public static Item toModel(ItemRequestDTO itemRequestDTO) {
        Item item = new Item();
        item.setName(itemRequestDTO.name());
        item.setActive(Boolean.TRUE);
        return item;
    }

}
