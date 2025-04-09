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
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query(
            "SELECT t " +
                    "FROM Task t " +
                    "WHERE t.account.id = :accountId " +
                    "AND (t.isDeleted = false)"
    )
    List<Task> findAllNotDeletedByAccountId(UUID accountId);

    @Query(
            "SELECT t " +
                    "FROM Task t " +
                    "WHERE t.account.id = :accountId " +
                    "AND (t.isDeleted = true)"
    )
    Page<Task> findAllArchivedByAccountId(UUID accountId, Pageable pageable);
    @Query(
            "SELECT t " +
                    "FROM Task  t " +
                    "LEFT JOIN TaskTag tt " +
                    "ON t.id = tt.taskId " +
                    "LEFT JOIN Tag tg " +
                    "ON tt.tagId = tg.id " +
                    "WHERE t.account.id = :accountId " +
                    "AND (t.isDeleted = false) " +
                    "AND (tg.name IN :tasks)"
    )
    List<Task> findAllNotDeletedByAccountIdAndTags(UUID accountId, List<String> tasks);

    @Query(
            "SELECT t " +
                    "FROM Task  t " +
                    "LEFT JOIN TaskTag tt " +
                    "ON t.id = tt.taskId " +
                    "LEFT JOIN Tag tg " +
                    "ON tt.tagId = tg.id " +
                    "WHERE t.account.id = :accountId " +
                    "AND (tg.name IN :tasks)"
    )
    List<Task> findAllByAccountIdAndTags(UUID accountId, List<String> tasks);

    @Modifying
    @Transactional
    @Query(
            "UPDATE Task t " +
                    "SET t.isDeleted = true " +
                    "WHERE t.account.id = :accountId " +
                    "AND (t.isCompleted = true) " +
                    "AND (t.isDeleted = false)"
    )
    void deleteAllCompletedTasksByAccountId(UUID accountId);
}
