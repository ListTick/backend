package com.pro.list_tick.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Entity
@Data
public class Tag {

    @Id
    @UuidGenerator
    private UUID id;

    String name;
    String color;

    @ManyToOne
    Account account;
}
