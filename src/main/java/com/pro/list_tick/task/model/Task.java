package com.pro.list_tick.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Task {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 1024)
    private String description;

    @Column(name = "total_pomodoros")
    private Integer totalPomodoros;

    @Column(name = "completed_pomodoros")
    private Integer completedPomodoros;

    @Column(name = "pomodoro_duration")
    private Integer pomodoroDuration;

    @Column(name = "break_duration")
    private Integer breakDuration;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "is_completed", nullable = false)
    private boolean isCompleted;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private Tag tag;

    @ManyToOne
    private Goal goal;
}
