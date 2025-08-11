package com.pro.list_tick.shopping_list.service.implementation;

import com.pro.list_tick.shared.CurrentAccountAPI;
import com.pro.list_tick.shopping_list.dto.ItemRequestDTO;
import com.pro.list_tick.shopping_list.dto.ItemRequestUpdateDTO;
import com.pro.list_tick.shopping_list.dto.ItemResponseDTO;
import com.pro.list_tick.shopping_list.exception.ItemException;
import com.pro.list_tick.shopping_list.exception.ShoppingListException;
import com.pro.list_tick.shopping_list.mapper.ItemMapper;
import com.pro.list_tick.shopping_list.model.Expense;
import com.pro.list_tick.shopping_list.model.Item;
import com.pro.list_tick.shopping_list.repository.SLItemRepository;
import com.pro.list_tick.shopping_list.service.SLItemService;
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
public class SLItemServiceImpl implements SLItemService {

    private final SLItemRepository itemRepository;

    private final CurrentAccountAPI accountService;
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
        var items = itemRepository.findAllActiveByShoppingListId(shoppingList.getId());
        return items.stream().map(ItemMapper::toResponseDTO).toList();
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ItemResponseDTO create(ItemRequestDTO itemRequestDTO) {
        log.debug("Creating the item: {}", itemRequestDTO);
        Item item = ItemMapper.toModel(itemRequestDTO);
        var shoppingList = shoppingListService.getById(itemRequestDTO.shoppingListId());
        if (!shoppingList.getActive()) {
            log.error("Cannot add item to the inactive shopping list: {}", shoppingList.getId());
            throw new ShoppingListException("The selected shopping list is inactive.");
        }

        item.setShoppingList(shoppingList);
        var savedItem = itemRepository.save(item);

        log.info("The item: {}, has been created", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ItemResponseDTO update(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO) {
        log.debug("Updating the item: {}", id);
        var item = getById(id);

        item.setName(itemRequestUpdateDTO.name());
        item.setValue(itemRequestUpdateDTO.value());
        item.setActive(itemRequestUpdateDTO.active());

        var savedItem = itemRepository.save(item);
        log.info("The item: {}, has been updated", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public ItemResponseDTO updateByFields(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO) {
        log.debug("Updating the item by fields: {}", id);
        var item = getById(id);

        if (Objects.nonNull(itemRequestUpdateDTO.name())) {
            item.setName(itemRequestUpdateDTO.name());
        }
        if (Objects.nonNull(itemRequestUpdateDTO.value())) {
            item.setValue(itemRequestUpdateDTO.value());
        }
        if (Objects.nonNull(itemRequestUpdateDTO.active())) {
            item.setActive(itemRequestUpdateDTO.active());
        }

        var savedItem = itemRepository.save(item);
        log.info("The item: {}, has been updated by fields", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "shoppingListTransactionManager")
    public void deactivate(UUID id) {
        log.debug("Deactivating the item: {}", id);
        var item = getById(id);

        item.setActive(Boolean.FALSE);

        itemRepository.save(item);
        log.info("The item: {}, has been deactivated", id);
    }

    public Item addExpense(UUID id, Expense expense) {
        log.debug("Adding expense: {} the item: {}", expense.getId(), id);
        var item = getById(id);

        item.setExpense(expense);

        var savedItem = itemRepository.save(item);
        log.info("Added expense: {} to the item: {}", expense.getId(), savedItem.getId());
        return savedItem;
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

