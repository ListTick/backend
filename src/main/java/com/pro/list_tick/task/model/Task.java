package com.pro.list_tick.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jdk.jfr.Name;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Task {

    @Id
    @UuidGenerator
    private UUID id;

    private String name;
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

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    private Goal goal;

    @ManyToOne
    private Account account;
}
