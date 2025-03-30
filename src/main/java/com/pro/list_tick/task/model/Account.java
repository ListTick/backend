package com.pro.list_tick.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name = "account")
public class Account {

    @Id
    private UUID id;

    @UpdateTimestamp
    private Instant lastUpdated;

}
