package com.pro.list_tick.shopping_list.repository;

import com.pro.list_tick.shopping_list.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SLCategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findAllByAccountId(UUID userId);

    boolean existsByNameAndAccountId(String name, UUID accountId);

}
