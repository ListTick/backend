package com.pro.list_tick.task.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@IdClass(TaskTagId.class)
public class TaskTag {

    @Id
    private UUID taskId;

    @Id
    private UUID tagId;
}
