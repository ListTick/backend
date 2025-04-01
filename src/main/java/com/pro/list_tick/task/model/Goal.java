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
public class Goal {

    @Id
    @UuidGenerator
    private UUID id;
    private String description;
    private Integer priority;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate realizationDate;

    @ManyToOne
    private Account account;
}
