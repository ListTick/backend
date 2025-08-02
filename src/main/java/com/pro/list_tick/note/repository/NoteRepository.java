package com.pro.list_tick.note.repository;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

  List<Note> findAllByAccountId(UUID userId);

  boolean existsByTitleAndAccountId(String title, UUID accountId);

}
