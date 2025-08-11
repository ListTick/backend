package com.pro.list_tick.notification.mapper;

import com.pro.list_tick.notification.dto.NotificationResponseDTO;
import com.pro.list_tick.notification.model.Notification;

public class NotificationMapper {

  private NotificationMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static NotificationResponseDTO toResponseDTO(Notification notification) {
    return new NotificationResponseDTO(
        notification.getId(),
        notification.getObjectClass(),
        notification.getObjectId(),
        notification.getDescription(),
        notification.getAcknowledged(),
        notification.getAccountId()
    );
  }

}
