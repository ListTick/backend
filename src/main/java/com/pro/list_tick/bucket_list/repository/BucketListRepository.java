package com.pro.list_tick.bucket_list.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.pro.list_tick.bucket_list.model.BucketList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketListRepository extends JpaRepository<BucketList, UUID> {

    List<BucketList> findAllByAccountId(UUID id);

    @Query("SELECT bl from BucketList bl WHERE bl.accountId = :id AND bl.active IS TRUE")
    List<BucketList> findAllActiveByAccountId(UUID id);

    @Query("SELECT bl FROM BucketList bl LEFT JOIN FETCH bl.items WHERE bl.id = :id")
    Optional<BucketList> findByIdWithItems(UUID id);

    boolean existsByNameAndAccountId(String name, UUID accountId);

    boolean existsByIdAndAccountId(UUID id, UUID accountId);

}
