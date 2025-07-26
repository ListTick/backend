package com.pro.list_tick.bucket_list.repository;

import java.util.List;
import java.util.UUID;


import com.pro.list_tick.bucket_list.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BLItemRepository extends JpaRepository<Item, UUID> {

  @Query("SELECT i FROM Item i WHERE i.bucketList.id = :bucketListId")
  List<Item> findAllByBucketListId(UUID bucketListId);

  @Query("SELECT i FROM Item i WHERE i.bucketList.id = :bucketListId AND i.active IS TRUE")
  List<Item> findAllActiveByBucketListId(UUID bucketListId);

}
