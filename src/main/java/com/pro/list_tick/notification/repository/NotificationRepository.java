package com.pro.list_tick.notification.repository;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

  List<Notification> findAllByAccountId(UUID accountId);

}
