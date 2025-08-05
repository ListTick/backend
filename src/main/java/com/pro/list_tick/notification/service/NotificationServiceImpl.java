package com.pro.list_tick.notification.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.notification.dto.NotificationResponseDTO;
import com.pro.list_tick.notification.exception.NotificationException;
import com.pro.list_tick.notification.mapper.NotificationMapper;
import com.pro.list_tick.notification.model.Notification;
import com.pro.list_tick.notification.repository.NotificationRepository;
import com.pro.list_tick.shared.CurrentAccountAPI;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final CurrentAccountAPI currentAccountAPI;

  public Notification getById(UUID id) {
    var accountId = currentAccountAPI.getCurrentAccountId();
    log.debug("Getting a notification by the id: {}", id);

    var notification = notificationRepository.findById(id)
        .orElseThrow(() -> {
          String errMessage = "Notification not found";
          log.error("{}: {}", errMessage, id);
          return new NotificationException(HttpStatus.BAD_REQUEST, errMessage);
        });
    validateOwnership(notification, accountId);
    return notification;
  }

  public List<NotificationResponseDTO> getAllByAccountId() {
    var accountId = currentAccountAPI.getCurrentAccountId();
    log.debug("Getting all notifications by the accountId: {}", accountId);

    return notificationRepository.findAllByAccountId(accountId)
        .stream()
        .map(NotificationMapper::toResponseDTO)
        .toList();
  }

  @Transactional(transactionManager = "notificationTransactionManager")
  public void create(Notification notification) {
    log.debug("Creating a notification for the accountId: {}, description: {}",
        notification.getAccountId(), notification.getDescription());

    notification.setAcknowledged(Boolean.FALSE);

    var savedNotification = notificationRepository.save(notification);
    log.info("The notification has been created: {}", savedNotification.getId());
  }

  @Transactional(transactionManager = "notificationTransactionManager")
  public void delete(UUID id) {
    log.debug("Deleting the notification: {}", id);
    final var notification = getById(id);

    notificationRepository.delete(notification);
    log.info("Notification has been deleted: {}", notification.getId());
  }

  @Transactional(transactionManager = "notificationTransactionManager")
  public NotificationResponseDTO acknowledge(UUID id) {
    log.debug("Acknowledging the notification: {}", id);

    final var notification = getById(id);
    notification.setAcknowledged(Boolean.TRUE);

    var savedNotification = notificationRepository.save(notification);
    log.info("The notification has been acknowledged: {}", notification.getId());
    return NotificationMapper.toResponseDTO(savedNotification);
  }

  private void validateOwnership(Notification notification, UUID accountId) {
    if (!notification.getAccountId().equals(accountId)) {
      String errMessage = "Access denied";
      log.error("{} {}: {}", errMessage, ",notificationId: ", notification.getId());
      throw new NotificationException(HttpStatus.FORBIDDEN, errMessage);
    }
  }

}
