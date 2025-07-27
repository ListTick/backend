package com.pro.list_tick.bucket_list.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.model.BucketList;
import com.pro.list_tick.bucket_list.model.SharedBucketList;
import com.pro.list_tick.bucket_list.dto.AccountSharedWithRequestDto;

public interface SharedBucketListService {

  List<SharedBucketList> findAllActiveByAccountId(UUID accountId);
  List<UUID> getAllAccountsByBucketListId(UUID bucketListId);
  List<SharedBucketList> createSharedBucketLists(BucketList bucketList,
                                                   List<AccountSharedWithRequestDto> sharedWithAccounts);
  String getEmail(UUID accountId);

}
