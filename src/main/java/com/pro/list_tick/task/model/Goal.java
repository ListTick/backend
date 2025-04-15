package com.pro.list_tick.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Goal {

    @Id
    @UuidGenerator
    private UUID id;

    @NotNull
    private String name;
    private String description;
    private Integer priority;

    @Column(name = "start_date")
    @FutureOrPresent
    private LocalDate startDate;

    @Column(name = "end_date")
    @Future
    private LocalDate endDate;

    @Column(name = "realization_date")
    private LocalDate realizationDate;

    @ManyToOne
    private Account account;
}
