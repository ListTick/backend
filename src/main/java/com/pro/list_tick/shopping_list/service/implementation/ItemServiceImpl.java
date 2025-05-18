package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.ItemDTO;
import com.pro.list_tick.shopping_list.exception.ItemException;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.mapper.ItemMapper;
import com.pro.list_tick.shopping_list.model.Item;
import com.pro.list_tick.shopping_list.model.ShoppingList;
import com.pro.list_tick.shopping_list.repository.ItemRepository;
import com.pro.list_tick.shopping_list.repository.ShoppingListRepository;
import com.pro.list_tick.shopping_list.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ShoppingListRepository shoppingListRepository;
    private final CurrentAccountService accountService;

    public List<ItemDTO> getAllByShoppingListId(UUID shoppingListId) {
        var accountId = accountService.getCurrentAccountId();
        var shoppingList = getShoppingList(shoppingListId);
        validateShoppingListAccess(accountId, shoppingList.getAccountId());
        var items = shoppingList.getItems();
        return items.stream().map(ItemMapper::toDTO).toList();
    }

    public ItemDTO getById(UUID id) {
        var accountId = accountService.getCurrentAccountId();
        var item = getItem(id);
        validateItemAccess(accountId, item.getShoppingList().getAccountId());
        return ItemMapper.toDTO(item);
    }

    public ItemDTO create(ItemDTO itemDTO) {
        var accountId = accountService.getCurrentAccountId();
        Item item = ItemMapper.toModel(itemDTO);
        var shoppingList = getShoppingList(itemDTO.getShoppingListId());
        validateShoppingListAccess(accountId, shoppingList.getAccountId());
        item.setShoppingList(shoppingList);
        var savedItem = itemRepository.save(item);
        return ItemMapper.toDTO(savedItem);
    }

    public ItemDTO update(UUID id, ItemDTO itemDTO) {
        var accountId = accountService.getCurrentAccountId();
        var item = getItem(id);
        validateItemAccess(accountId, item.getShoppingList().getAccountId());

        item.setName(itemDTO.getName());
        item.setValue(itemDTO.getValue());
        item.setActive(itemDTO.getActive());

        return ItemMapper.toDTO(itemRepository.save(item));
    }

    public ItemDTO updateByFields(UUID id, ItemDTO itemDTO) {
        var accountId = accountService.getCurrentAccountId();
        var item = getItem(id);
        validateItemAccess(accountId, item.getShoppingList().getAccountId());

        if (Objects.nonNull(itemDTO.getName())) {
            item.setName(itemDTO.getName());
        }
        if (Objects.nonNull(itemDTO.getValue())) {
            item.setValue(itemDTO.getValue());
        }
        if (Objects.nonNull(itemDTO.getActive())) {
            item.setActive(itemDTO.getActive());
        }

        return ItemMapper.toDTO(itemRepository.save(item));
    }

    public void delete(UUID id) {
        var accountId = accountService.getCurrentAccountId();
        var item = getItem(id);
        validateItemAccess(accountId, item.getShoppingList().getAccountId());
        itemRepository.delete(item);
    }

    private ShoppingList getShoppingList(UUID id) {
        return shoppingListRepository.findById(id)
                .orElseThrow(() -> new ShoppingListException(
                        HttpStatus.NOT_FOUND,
                        String.format("Couldn't find a shopping list: %s", id))
                );
    }

    private Item getItem(UUID id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemException(
                        HttpStatus.NOT_FOUND,
                        String.format("Couldn't find an item: %s", id))
                );
    }

    private static void validateItemAccess(UUID accountId, UUID itemAccountId) {
        if (!accountId.equals(itemAccountId)) {
            throw new ItemException(
                    HttpStatus.FORBIDDEN,
                    String.format("User doesn't have access to the item: %s", itemAccountId));
        }
    }

    private static void validateShoppingListAccess(UUID accountId, UUID shoppingListAccountId) {
        if (!accountId.equals(shoppingListAccountId)) {
            throw new ShoppingListException(
                    HttpStatus.FORBIDDEN,
                    String.format("User doesn't have access to the shopping list: %s", shoppingListAccountId));
        }
    }

}

