package com.pro.list_tick.task.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@IdClass(TaskTagId.class)
public class TaskTag {

    @Id
    @Column(name = "task_id")
    private UUID taskId;

    @Id
    @Column(name = "tag_id")
    private UUID tagId;
}
