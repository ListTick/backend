package com.pro.list_tick.notification.dto;

import java.util.UUID;

public record NotificationResponseDTO(

    UUID id,
    String objectClass,
    UUID objectId,
    String description,
    Boolean acknowledged,
    UUID accountId

) {
}
