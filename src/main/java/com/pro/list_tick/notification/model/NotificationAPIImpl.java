package com.pro.list_tick.notification.model;

import java.util.UUID;

import com.pro.list_tick.notification.service.NotificationService;
import com.pro.list_tick.shared.NotificationAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationAPIImpl implements NotificationAPI {

  private final NotificationService notificationService;

  @Override
  public void create(UUID objectId, String objectClass, String description) {
    Notification notification = new Notification();

    if (notification.getObjectId() != null) {
      notification.setObjectId(objectId);
    }
    if (notification.getObjectClass() != null) {
      notification.setObjectClass(objectClass);
    }
    notification.setDescription(description);

    notificationService.create(notification);
  }

}
