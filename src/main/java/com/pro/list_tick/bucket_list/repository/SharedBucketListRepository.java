package com.pro.list_tick.bucket_list.repository;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.model.SharedBucketList;
import com.pro.list_tick.bucket_list.model.SharedBucketListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedBucketListRepository extends JpaRepository<SharedBucketList, SharedBucketListId> {

    List<SharedBucketList> findAllByIdAccountId(UUID accountId);

    @Query("SELECT sbl FROM SharedBucketList sbl WHERE sbl.id.accountId = :accountId AND sbl.bucketList.active IS TRUE")
    List<SharedBucketList> findAllActiveByIdAccountId(UUID accountId);

    @Query("SELECT sbl.id.accountId FROM SharedBucketList sbl WHERE  sbl.bucketList.id = :bucketListId")
    List<UUID> findAllAccountsById(@Param("bucketListId")UUID bucketListId);
}
