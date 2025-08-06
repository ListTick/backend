package com.pro.list_tick.bucket_list.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.dto.ItemRequestDTO;
import com.pro.list_tick.bucket_list.dto.ItemRequestUpdateDTO;
import com.pro.list_tick.bucket_list.dto.ItemResponseDTO;
import com.pro.list_tick.bucket_list.model.Item;

public interface BLItemService {

    Item getById(UUID id);
    List<ItemResponseDTO> getAllByBucketListId(UUID bucketListId);
    ItemResponseDTO create(ItemRequestDTO itemRequestDTO);
    ItemResponseDTO update(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO);
    ItemResponseDTO updateByFields(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO);
    void deactivate(UUID id);

}
