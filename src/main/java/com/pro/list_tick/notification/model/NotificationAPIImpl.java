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


  public void create(UUID objectId, String objectClass, String description, UUID accountId) {
    Notification notification = new Notification();

    notification.setObjectId(objectId);
    notification.setObjectClass(objectClass);
    notification.setDescription(description);
    notification.setAccountId(accountId);

    notificationService.create(notification);
  }

}
