package com.pro.list_tick.notification.model;

import jakarta.persistence.Column;
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
    @Column(name = "last_updated")
    private Instant lastUpdated;

}
