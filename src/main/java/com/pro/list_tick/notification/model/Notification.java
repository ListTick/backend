package com.pro.list_tick.notification.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "notification")
@Data
public class Notification {

  @Id
  @UuidGenerator
  private UUID id;

  @Column(name = "object_class")
  @Size(max = 255, message = "Object class cannot be longer than 255 characters")
  private String objectClass;

  @Column(name = "object_id")
  private UUID objectId;

  @NotBlank(message = "Description cannot be blank")
  @Size(max = 255, message = "Description cannot be longer than 255 characters")
  private String description;

  @NotNull(message = "Acknowledged cannot be null")
  private Boolean acknowledged;

  @Column(name = "account_id")
  @NotNull(message = "Account Id cannot be null")
  private UUID accountId;

}
