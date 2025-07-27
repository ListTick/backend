package com.pro.list_tick.bucket_list.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.dto.BucketListRequestDTO;
import com.pro.list_tick.bucket_list.dto.BucketListRequestUpdateDTO;
import com.pro.list_tick.bucket_list.dto.BucketListResponseDTO;
import com.pro.list_tick.bucket_list.model.BucketList;

public interface BucketListService {

    BucketList getById(UUID id);
    List<BucketListResponseDTO> getAllDTOByAccountId();
    BucketListResponseDTO create(BucketListRequestDTO bucketListRequestDTO);
    BucketListResponseDTO update(UUID id, BucketListRequestUpdateDTO bucketListRequestDTOUpdateDTO);
    BucketListResponseDTO updateByFields(UUID id, BucketListRequestUpdateDTO bucketListRequestDTOUpdateDTO);
    void delete(UUID id);
    Boolean validateAccess(UUID accountId, BucketList bucketList);
    Boolean validateSharedAccess(UUID accountId, BucketList bucketList);

}
