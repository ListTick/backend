package com.pro.list_tick.task.model;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskTagId {
    private UUID taskId;
    private UUID tagId;
}
