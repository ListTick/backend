package com.pro.list_tick.note.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.note.dto.NoteRequestDTO;
import com.pro.list_tick.note.dto.NoteResponseDTO;
import com.pro.list_tick.note.model.Note;

public interface NoteService {

  Note getById(UUID id);
  List<NoteResponseDTO> getAllByAccountId();
  NoteResponseDTO create(NoteRequestDTO noteRequestDTO);
  NoteResponseDTO updateByFields(UUID id, NoteRequestDTO noteRequestDTO);
  void delete(UUID id);

}
