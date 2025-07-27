package com.pro.list_tick.bucket_list.service.implementation;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.pro.list_tick.bucket_list.exception.BucketListException;
import com.pro.list_tick.bucket_list.service.BLItemService;
import com.pro.list_tick.bucket_list.service.BucketListService;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.bucket_list.dto.ItemRequestDTO;
import com.pro.list_tick.bucket_list.dto.ItemRequestUpdateDTO;
import com.pro.list_tick.bucket_list.dto.ItemResponseDTO;
import com.pro.list_tick.bucket_list.exception.ItemException;
import com.pro.list_tick.bucket_list.mapper.ItemMapper;
import com.pro.list_tick.bucket_list.model.Item;
import com.pro.list_tick.bucket_list.repository.BLItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class BLItemServiceImpl implements BLItemService {

    private final BLItemRepository itemRepository;

    private final CurrentAccountService accountService;
    private final BucketListService bucketListService;

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

    public List<ItemResponseDTO> getAllByBucketListId(UUID bucketListId) {
        log.debug("Getting items by shopping list id: {}", bucketListId);
        var bucketList = bucketListService.getById(bucketListId); //validates the shopping list access
        var items = itemRepository.findAllActiveByBucketListId(bucketList.getId());
        return items.stream().map(ItemMapper::toResponseDTO).toList();
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public ItemResponseDTO create(ItemRequestDTO itemRequestDTO) {
        log.debug("Creating the item: {}", itemRequestDTO);
        Item item = ItemMapper.toModel(itemRequestDTO);
        var bucketList = bucketListService.getById(itemRequestDTO.bucketListId());
        if (!bucketList.getActive()) {
            log.error("Cannot add item to the inactive bucket list: {}", bucketList.getId());
            throw new BucketListException("The selected bucket list is inactive.");
        }

        item.setBucketList(bucketList);
        var savedItem = itemRepository.save(item);

        log.info("The item: {}, has been created", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public ItemResponseDTO update(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO) {
        log.debug("Updating the item: {}", id);
        var item = getById(id);

        item.setName(itemRequestUpdateDTO.name());
        item.setActive(itemRequestUpdateDTO.active());

        var savedItem = itemRepository.save(item);
        log.info("The item: {}, has been updated", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public ItemResponseDTO updateByFields(UUID id, ItemRequestUpdateDTO itemRequestUpdateDTO) {
        log.debug("Updating the item by fields: {}", id);
        var item = getById(id);

        if (Objects.nonNull(itemRequestUpdateDTO.name())) {
            item.setName(itemRequestUpdateDTO.name());
        }
        if (Objects.nonNull(itemRequestUpdateDTO.active())) {
            item.setActive(itemRequestUpdateDTO.active());
        }

        var savedItem = itemRepository.save(item);
        log.info("The item: {}, has been updated by fields", savedItem.getId());
        return ItemMapper.toResponseDTO(savedItem);
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public void deactivate(UUID id) {
        log.debug("Deactivating the item: {}", id);
        var item = getById(id);

        item.setActive(Boolean.FALSE);

        itemRepository.save(item);
        log.info("The item: {}, has been deactivated", id);
    }

    private void validateItemAccess(Item item) {
        log.debug("Validating the item access: {}", item.getId());
        var accountId = accountService.getCurrentAccountId();
        var bucketList = item.getBucketList();
        if (!bucketListService.validateAccess(accountId, bucketList) &&
            !bucketListService.validateSharedAccess(accountId, bucketList)) {
            throw new ItemException(HttpStatus.FORBIDDEN,
                String.format("User doesn't have an access to the item: %s", item.getId()));
        }
    }

}

