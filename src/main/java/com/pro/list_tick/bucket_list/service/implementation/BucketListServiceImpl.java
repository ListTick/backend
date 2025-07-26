package com.pro.list_tick.bucket_list.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.pro.list_tick.bucket_list.repository.BucketListRepository;
import com.pro.list_tick.bucket_list.service.BLCategoryService;
import com.pro.list_tick.bucket_list.service.SharedBucketListService;
import com.pro.list_tick.bucket_list.service.BucketListService;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.bucket_list.dto.AccountSharedWithResponseDto;
import com.pro.list_tick.bucket_list.dto.BucketListRequestDTO;
import com.pro.list_tick.bucket_list.dto.BucketListRequestUpdateDTO;
import com.pro.list_tick.bucket_list.dto.BucketListResponseDTO;
import com.pro.list_tick.bucket_list.exception.BucketListException;
import com.pro.list_tick.bucket_list.mapper.BucketListMapper;
import com.pro.list_tick.bucket_list.model.SharedBucketList;
import com.pro.list_tick.bucket_list.model.BucketList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class BucketListServiceImpl implements BucketListService {

    private final BucketListRepository bucketListRepository;

    private final CurrentAccountService currentAccountService;
    private final BLCategoryService categoryService;
    private final SharedBucketListService sharedBucketListService;

    public BucketList getById(UUID id) {
        log.debug("Getting the bucket list: {}", id);
        final var bucketList = bucketListRepository.findById(id)
            .orElseThrow(() -> new BucketListException(HttpStatus.NOT_FOUND, "Bucket list not found"));

        final var accountId = currentAccountService.getCurrentAccountId();
        if (!validateAccess(accountId, bucketList) &&
            !validateSharedAccess(accountId, bucketList)) {
            log.error("User doesn't have access to the bucket list: {}", bucketList.getId());
            throw new BucketListException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return bucketList;
    }

    public List<BucketListResponseDTO> getAllDTOByAccountId() {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.debug("Getting all bucket lists for the accountId: {}", accountId);

        var bucketLists = bucketListRepository.findAllActiveByAccountId(accountId);
        var sharedBucketLists = sharedBucketListService.findAllActiveByAccountId(accountId);
        List<BucketListResponseDTO> dtoList = new ArrayList<>(bucketLists.stream()
            .map(list -> {
                if (list.getShared()) {
                    var sharedWithAccounts = getSharedWithAccounts(list);
                    return BucketListMapper.toResponseDTO(list, sharedWithAccounts);
                } else {
                    return BucketListMapper.toResponseDTO(list);
                }
            })
            .toList());
        dtoList.addAll(sharedBucketLists.stream()
            .map(SharedBucketList::getBucketList)
            .map(list -> {
                var sharedWithAccounts = getSharedWithAccounts(list);
                return BucketListMapper.toResponseDTO(list, sharedWithAccounts);
            })
            .toList());
        return dtoList;
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public BucketListResponseDTO create(BucketListRequestDTO bucketListRequestDTO) {
        final var accountId = currentAccountService.getCurrentAccountId();
        log.info("Creating a bucket list for the account id: {}, name: {}",
            accountId, bucketListRequestDTO.name());

        var bucketList = BucketListMapper.toModel(bucketListRequestDTO);
        var category = categoryService.getById(bucketListRequestDTO.categoryId());

        validateName(bucketListRequestDTO.name(), accountId);

        bucketList.setAccountId(accountId);
        bucketList.setCategory(category);
        bucketList.setItems(new ArrayList<>());
        bucketList.setSharedBucketLists(new ArrayList<>());
        
        var savedBucketList = bucketListRepository.save(bucketList);

        if (bucketListRequestDTO.shared()) {
            List<SharedBucketList> sharedLists = sharedBucketListService
                .createSharedBucketLists(savedBucketList, bucketListRequestDTO.sharedWithAccounts());
            savedBucketList.getSharedBucketLists().addAll(sharedLists);
        }

        log.info("Bucket list has been created: {}, accountId: {}, name: {}",
                savedBucketList.getId(), savedBucketList.getAccountId(), savedBucketList.getName()
        );

        if (savedBucketList.getShared()) {
            var sharedWithDto = getSharedWithAccounts(savedBucketList);
            return BucketListMapper.toResponseDTO(savedBucketList, sharedWithDto);
        }

        return BucketListMapper.toResponseDTO(savedBucketList);
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public BucketListResponseDTO update(UUID id, BucketListRequestUpdateDTO bucketListRequestUpdateDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.info("Updating the bucket list: {}", id);
        var bucketList = getById(id);

        validateName(bucketListRequestUpdateDTO.name(), accountId);
        bucketList.setName(bucketListRequestUpdateDTO.name());

        bucketList.setActive(bucketListRequestUpdateDTO.active());
        bucketList.setCategory(categoryService.getById(bucketListRequestUpdateDTO.categoryId()));
        return BucketListMapper.toResponseDTO(bucketListRepository.save(bucketList));
    }

    private void validateName(String bucketListRequestUpdateDTO, UUID accountId) {
        if (bucketListRepository.existsByNameAndAccountId(bucketListRequestUpdateDTO, accountId)) {
            log.error("Bucket list name already exists: {}", bucketListRequestUpdateDTO);
            throw new BucketListException(HttpStatus.CONFLICT, "Name already exists");
        }
    }

    @Transactional(transactionManager = "bucketListTransactionManager")
    public BucketListResponseDTO updateByFields(UUID id, BucketListRequestUpdateDTO bucketListRequestUpdateDTO) {
        var accountId = currentAccountService.getCurrentAccountId();
        log.info("Updating the bucket list by fields: {}", id);
        var bucketList = getById(id);
        if (Objects.nonNull(bucketListRequestUpdateDTO.name())) {
            validateName(bucketListRequestUpdateDTO.name(), accountId);
            bucketList.setName(bucketListRequestUpdateDTO.name());
        }
        if (Objects.nonNull(bucketListRequestUpdateDTO.active())) {
            bucketList.setActive(bucketListRequestUpdateDTO.active());
        }
        if (Objects.nonNull(bucketListRequestUpdateDTO.categoryId())) {
            var category = categoryService.getById(bucketListRequestUpdateDTO.categoryId());
            bucketList.setCategory(category);
        }

        return BucketListMapper.toResponseDTO(bucketListRepository.save(bucketList));
    }

    public void delete(UUID id) {
        log.info("Deleting the bucket list: {}", id);
        final var bucketList = bucketListRepository.findById(id)
            .orElseThrow(() -> new BucketListException(String.format("Bucket list not found: %s", id)));

        var accountId = currentAccountService.getCurrentAccountId();
        validateAccess(accountId, bucketList);
        bucketListRepository.delete(bucketList);
    }

    public Boolean validateAccess(UUID accountId, BucketList bucketList) {
        log.debug("Validating the bucket list access: {}", bucketList.getId());

        return accountId.equals(bucketList.getAccountId());
    }

    public Boolean validateSharedAccess(UUID accountId, BucketList bucketList) {
        log.debug("Validating the bucket list shared access");

        var accountIds = sharedBucketListService.getAllAccountsByBucketListId(bucketList.getId());
        return accountIds.stream().anyMatch(id -> id.equals(accountId));
    }

    private List<AccountSharedWithResponseDto> getSharedWithAccounts(BucketList bucketList) {
        var accountId = currentAccountService.getCurrentAccountId();
        return bucketList.getSharedBucketLists()
            .stream()
            .map(list -> {
                if (!list.getAccountId().equals(accountId)) {
                    var email = sharedBucketListService.getEmail(list.getAccountId());
                    return new AccountSharedWithResponseDto(email);
                } else {
                    var email = sharedBucketListService.getEmail(bucketList.getAccountId());
                    return new AccountSharedWithResponseDto(email);
                }
            })
            .toList();
    }

}
