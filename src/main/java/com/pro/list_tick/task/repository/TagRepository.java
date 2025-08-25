package com.pro.list_tick.task.repository;

import com.pro.list_tick.task.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT t FROM Tag t WHERE t.accountId = :accountId AND t.isDeleted = false ORDER BY t.createdAt ASC")
    List<Tag> findAllByAccountId(UUID accountId);

    @Query("SELECT COUNT(t) > 0 FROM Tag t WHERE t.name = :name AND t.accountId = :accountId")
    boolean existsByName(String name, UUID accountId);

    @Query("SELECT t FROM Tag t WHERE t.name = :name AND t.accountId = :currentAccountId")
    Tag findByName(String name, UUID currentAccountId);
}
