package com.pro.list_tick.notification.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.notification.dto.NotificationResponseDTO;
import com.pro.list_tick.notification.model.Notification;

public interface NotificationService {

  Notification getById(UUID id);
  List<NotificationResponseDTO> getAllByAccountId();
  void create(Notification notification);
  void delete(UUID id);
  NotificationResponseDTO acknowledge(UUID id);

}
