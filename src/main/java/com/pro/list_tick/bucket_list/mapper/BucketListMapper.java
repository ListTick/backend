package com.pro.list_tick.bucket_list.mapper;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.pro.list_tick.bucket_list.dto.AccountSharedWithResponseDto;
import com.pro.list_tick.bucket_list.dto.BucketListRequestDTO;
import com.pro.list_tick.bucket_list.dto.BucketListResponseDTO;
import com.pro.list_tick.bucket_list.model.BucketList;

public class BucketListMapper {

    private BucketListMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static BucketListResponseDTO toResponseDTO(BucketList shoppingList) {
      return new BucketListResponseDTO(
          shoppingList.getId(),
          shoppingList.getName(),
          shoppingList.getActive(),
          shoppingList.getShared(),
          shoppingList.getCreationDate(),
          CategoryMapper.toResponseDTO(shoppingList.getCategory()),
          shoppingList.getAccountId(),
          new ArrayList<>()
      );
    }

    public static BucketListResponseDTO toResponseDTO(
        BucketList bucketList, List<AccountSharedWithResponseDto> accountSharedWithResponsesDto) {
        return new BucketListResponseDTO(
            bucketList.getId(),
            bucketList.getName(),
            bucketList.getActive(),
            bucketList.getShared(),
            bucketList.getCreationDate(),
            CategoryMapper.toResponseDTO(bucketList.getCategory()),
            bucketList.getAccountId(),
            accountSharedWithResponsesDto
        );
    }

    public static BucketList toModel(BucketListRequestDTO bucketListRequestDTO) {
        BucketList bucketList = new BucketList();
        bucketList.setName(bucketListRequestDTO.name());
        bucketList.setActive(Boolean.TRUE);
        bucketList.setShared(bucketListRequestDTO.shared());
        bucketList.setCreationDate(LocalDate.now());
        return bucketList;
    }

}
