package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.shopping_list.dto.ItemRequestDTO;
import com.pro.list_tick.shopping_list.dto.ItemResponseDTO;
import com.pro.list_tick.shopping_list.exception.ItemException;
import com.pro.list_tick.shopping_list.mapper.ItemMapper;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.Item;
import com.pro.list_tick.shopping_list.repository.ItemRepository;
import com.pro.list_tick.shopping_list.service.ItemService;
import com.pro.list_tick.shopping_list.service.ShoppingListService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final CurrentAccountService accountService;
    private final ShoppingListService shoppingListService;

    public Item getById(UUID id) {
        log.debug("Getting the item by id: {}", id);
        var item = itemRepository.findById(id)
            .orElseThrow(() -> new ItemException(
                HttpStatus.NOT_FOUND,
                String.format("Couldn't find the item: %s", id))
            );
        validateItemAccess(item);
        return item;
    }

    public List<ItemResponseDTO> getAllByShoppingListId(UUID shoppingListId) {
        log.debug("Getting items by shopping list id: {}", shoppingListId);
        var shoppingList = shoppingListService.getById(shoppingListId); //validates the shopping list access
        var items = itemRepository.findAllByShoppingListId(shoppingList.getId());
        return items.stream().map(ItemMapper::toResponseDTO).toList();
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ItemResponseDTO create(ItemRequestDTO itemRequestDTO) {
        log.debug("Creating the item: {}", itemRequestDTO);
        Item item = ItemMapper.toModel(itemRequestDTO);
        var shoppingList = shoppingListService.getById(itemRequestDTO.shoppingListId());
        item.setShoppingList(shoppingList);
        var savedItem = itemRepository.save(item);

        log.info("The item: {}, has been created", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ItemResponseDTO update(UUID id, ItemRequestDTO itemRequestDTO) {
        log.debug("Updating the item: {}", id);
        var item = getById(id);

        item.setName(itemRequestDTO.name());
        item.setValue(itemRequestDTO.value());
        item.setActive(itemRequestDTO.active());

        var savedItem = itemRepository.save(item);
        log.info("The item: {}, has been updated", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ItemResponseDTO updateByFields(UUID id, ItemRequestDTO itemRequestDTO) {
        log.debug("Updating the item by fields: {}", id);
        var item = getById(id);

        if (Objects.nonNull(itemRequestDTO.name())) {
            item.setName(itemRequestDTO.name());
        }
        if (Objects.nonNull(itemRequestDTO.value())) {
            item.setValue(itemRequestDTO.value());
        }
        if (Objects.nonNull(itemRequestDTO.active())) {
            item.setActive(itemRequestDTO.active());
        }

        var savedItem = itemRepository.save(item);
        log.info("The item: {}, has been updated by fields", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public void delete(UUID id) {
        log.debug("Deleting the item: {}", id);
        var item = getById(id);
        itemRepository.delete(item);
        log.info("The item: {}, has been deleted", id);
    }

    public void addExpenseAndDeactivate(Expense expense, Item item) {
        //todo
    }

    private void validateItemAccess(Item item) {
        log.debug("Validating the item access: {}", item.getId());
        var accountId = accountService.getCurrentAccountId();
        var shoppingList = item.getShoppingList();
        if (!shoppingListService.validateAccess(accountId, shoppingList) &&
            !shoppingListService.validateSharedAccess(accountId, shoppingList)) {
            throw new ItemException(HttpStatus.FORBIDDEN,
                String.format("User doesn't have an access to the item: %s", item.getId()));
        }
    }

}

