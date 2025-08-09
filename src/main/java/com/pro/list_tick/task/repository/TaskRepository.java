package com.pro.list_tick.task.repository;

import com.pro.list_tick.task.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(
            "SELECT t " +
            "FROM Task t " +
            "WHERE t.accountId = :accountId " +
            "AND (t.isDeleted = false)" +
            "ORDER BY t.createdAt ASC"
    )
    List<Task> findAllNotDeletedByAccountId(UUID accountId);

    @Query(
            "SELECT t " +
            "FROM Task t " +
            "LEFT JOIN Tag tg " +
            "ON t.tag.id = tg.id " +
            "WHERE t.accountId = :accountId " +
            "AND tg.id = :tagId " +
            "AND (t.isDeleted = false) " +
            "ORDER BY t.createdAt ASC"
    )
    List<Task> findAllNotDeletedByAccountIdAndTag(UUID accountId, UUID tagId);

    @Query(
            "SELECT t " +
            "FROM Task t " +
            "WHERE t.accountId = :accountId " +
            "AND (t.isDeleted = true)" +
            "ORDER BY t.createdAt DESC"
    )
    Page<Task> findAllArchivedByAccountId(UUID accountId, Pageable pageable);

    @Query(
            "SELECT t " +
            "FROM Task t " +
            "WHERE t.accountId = :accountId " +
            "AND t.tag.id = :tagId " +
            "AND (t.isDeleted = true)" +
            "ORDER BY t.createdAt DESC"
    )
    Page<Task> findAllArchivedByAccountIdAndTag(UUID accountId, Pageable pageable, UUID tagId);

    @Modifying
    @Transactional
    @Query(
            "UPDATE Task t " +
                    "SET t.isDeleted = true " +
                    "WHERE t.accountId = :accountId " +
                    "AND (t.isCompleted = true) " +
                    "AND (t.isDeleted = false)"
    )
    void deleteAllCompletedTasksByAccountId(UUID accountId);
}
