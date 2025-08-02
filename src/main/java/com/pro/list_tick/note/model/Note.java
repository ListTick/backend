package com.pro.list_tick.note.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "note")
@Data
public class Note {

  @Id
  @UuidGenerator
  private UUID id;

  @NotBlank(message = "Title cannot be blank")
  @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
  private String title;

  @PastOrPresent(message = "Creation date cannot be in the future")
  @Column(name = "created_at")
  private LocalDate createdAt;

  @PastOrPresent(message = "Creation date cannot be in the future")
  @Column(name = "modified_at")
  private LocalDate modifiedAt;

  private String description;

  @Column(name = "account_id")
  private UUID accountId;

}
