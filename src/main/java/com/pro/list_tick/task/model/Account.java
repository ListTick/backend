package com.pro.list_tick.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Account {

    @Id
    private UUID id;
}
