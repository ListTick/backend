package com.pro.list_tick.bucket_list.repository;

import java.util.List;
import java.util.UUID;


import com.pro.list_tick.bucket_list.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BLCategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findAllByAccountId(UUID userId);

    boolean existsByNameAndAccountId(String name, UUID accountId);

}
