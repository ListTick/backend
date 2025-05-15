package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.repository.ItemRepository;
import com.pro.list_tick.shopping_list.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;


    public List<ItemDTO> getAllByAccountId() {
        return List.of();
    }

    public List<ItemDTO> getAllByShoppingListId(UUID shoppingListId) {
        return List.of();
    }

    public ItemDTO getById(UUID id) {
        return null;
    }

    public ItemDTO create(ItemInputDTO itemInputDTO) {
        return null;
    }

    public ItemDTO update(ItemUpdateDTO itemUpdateDTO) {
        return null;
    }

    public ItemDTO updateByFields(ItemUpdateDTO itemUpdateDTO) {
        return null;
    }

    public void delete(UUID id) {

    }

}
