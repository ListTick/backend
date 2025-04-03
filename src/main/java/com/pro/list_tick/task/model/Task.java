package com.pro.list_tick.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private Integer totalPomodoros;
    private Integer completedPomodoros;
    private Integer pomodoroDuration;
    private Integer breakDuration;
    private LocalDate dueDate;
    private boolean isCompleted;
    private boolean isDeleted;

    @ManyToOne
    private Goal goal;

    @ManyToOne
    private Account account;
}
