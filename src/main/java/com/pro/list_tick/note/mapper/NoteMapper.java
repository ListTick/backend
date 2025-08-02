package com.pro.list_tick.note.mapper;

import com.pro.list_tick.note.dto.NoteRequestDTO;
import com.pro.list_tick.note.dto.NoteResponseDTO;
import com.pro.list_tick.note.model.Note;

public class NoteMapper {

  private NoteMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static NoteResponseDTO toResponseDTO(Note note) {
    return new NoteResponseDTO(
        note.getId(),
        note.getName(),
        note.getCreatedAt(),
        note.getModifiedAt(),
        note.getDescription(),
        note.getAccountId()
    );
  }

  public static Note toModel(NoteRequestDTO noteRequestDTO) {
    Note note = new Note();
    note.setName(noteRequestDTO.name());
    note.setDescription(noteRequestDTO.description());
    return note;
  }

}
