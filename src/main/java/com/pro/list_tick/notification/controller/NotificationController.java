package com.pro.list_tick.notification.controller;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.notification.dto.NotificationResponseDTO;
import com.pro.list_tick.notification.mapper.NotificationMapper;
import com.pro.list_tick.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/notifications")
@AllArgsConstructor
@Validated
@Slf4j
public class NotificationController {

  private final NotificationService notificationService;
  private final String requestLogTemplate = "Received request, method: {}, context path: /api/notifications{}, body {}";


  @GetMapping("/{id}")
  public ResponseEntity<NotificationResponseDTO> getById(@PathVariable UUID id) {
    log.debug(String.format(requestLogTemplate),
        "GET", id, "");
    final var notification = notificationService.getById(id);
    return ResponseEntity.ok(NotificationMapper.toResponseDTO(notification));
  }

  @GetMapping
  public ResponseEntity<List<NotificationResponseDTO>> getAllByAccountId() {
    log.debug(String.format(requestLogTemplate),
        "GET", "", "");
    final var notifications = notificationService.getAllByAccountId();
    return ResponseEntity.ok(notifications);
  }

  @PatchMapping("/{id}/acknowledge")
  public ResponseEntity<NotificationResponseDTO> acknowledge(@PathVariable UUID id) {
    log.debug(String.format(requestLogTemplate),
        "GET", id, "");
    final var responseDTO = notificationService.acknowledge(id);
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    log.debug(String.format(requestLogTemplate),
        "DELETE", id, "");
    notificationService.delete(id);
    return ResponseEntity.status(204).build();
  }

}
