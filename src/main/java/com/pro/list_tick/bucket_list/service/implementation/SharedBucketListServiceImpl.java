package com.pro.list_tick.bucket_list.service.implementation;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.model.SharedBucketList;
import com.pro.list_tick.bucket_list.repository.SharedBucketListRepository;
import com.pro.list_tick.bucket_list.service.SharedBucketListService;
import com.pro.list_tick.shared.api.AccountAPI;
import com.pro.list_tick.bucket_list.dto.AccountSharedWithRequestDto;
import com.pro.list_tick.bucket_list.exception.BucketListException;
import com.pro.list_tick.bucket_list.model.BucketList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SharedBucketListServiceImpl implements SharedBucketListService {

  private final AccountAPI accountAPI;
  private final SharedBucketListRepository sharedBucketListRepository;

  public List<SharedBucketList> findAllActiveByAccountId(UUID accountId) {
    return sharedBucketListRepository.findAllActiveByIdAccountId(accountId);
  }

  public List<UUID> getAllAccountsByBucketListId(UUID bucketListId) {
    return sharedBucketListRepository.findAllAccountsById(bucketListId);
  }

  public List<SharedBucketList> createSharedBucketLists(BucketList bucketList,
                                                          List<AccountSharedWithRequestDto> sharedWithAccounts) {
    if (sharedWithAccounts == null || sharedWithAccounts.isEmpty()) {
      var errorMessage = "'sharedWithAccounts' cannot be null or empty while 'shared' is set to true";
      log.error(errorMessage);
      throw new BucketListException(HttpStatus.BAD_REQUEST, errorMessage);
    }

    log.debug("Creating shared lists for: {}", sharedWithAccounts);

    return sharedWithAccounts.stream()
        .map(accountSharedWithRequestDto -> {
          final var email = accountSharedWithRequestDto.email();
          final var accountId = accountAPI.getAccountIdByEmail(email);
          if (bucketList.getAccountId().equals(accountId)) {
            var errorMessage = "Bucket list cannot be shared with the owner's own account";
            log.error("{} - accountId: {}", errorMessage, accountId);
            throw new BucketListException(HttpStatus.CONFLICT, errorMessage);
          }
          SharedBucketList shared = new SharedBucketList();
          shared.setBucketListAndAccount(bucketList, accountId);
          return sharedBucketListRepository.save(shared);
        }).toList();
  }

  public String getEmail(UUID accountId) {
    return accountAPI.getEmailByAccountId(accountId);
  }

}
