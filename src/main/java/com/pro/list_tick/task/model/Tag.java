package com.pro.list_tick.task.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Data
public class Tag {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 255)
    String name;

    @Column(length = 7)
    String color;

    @Column(name = "account_id", nullable = false)
    UUID accountId;
}
