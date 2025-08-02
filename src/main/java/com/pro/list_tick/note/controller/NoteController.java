package com.pro.list_tick.note.controller;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.note.dto.NoteRequestDTO;
import com.pro.list_tick.note.dto.NoteResponseDTO;
import com.pro.list_tick.note.mapper.NoteMapper;
import com.pro.list_tick.note.service.NoteService;
import com.pro.list_tick.shopping_list.dto.ExpenseRequestDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseRequestUpdateDTO;
import com.pro.list_tick.shopping_list.dto.ExpenseResponseDTO;
import com.pro.list_tick.shopping_list.mapper.ExpenseMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/notes")
@AllArgsConstructor
@Validated
@Slf4j
public class NoteController {

  private final NoteService noteService;
  private final String requestLogTemplate = "Received request, method: {}, context path: /api/expense{}, body {}";


  @GetMapping("/{id}")
  public ResponseEntity<NoteResponseDTO> getById(@PathVariable UUID id) {
    log.debug(String.format(requestLogTemplate),
        "GET", id, "");
    final var note = noteService.getById(id);
    return ResponseEntity.ok(NoteMapper.toResponseDTO(note));
  }

  @GetMapping
  public ResponseEntity<List<NoteResponseDTO>> getAllByAccountId() {
    log.debug(String.format(requestLogTemplate),
        "GET", "", "");
    final var notes = noteService.getAllByAccountId();
    return ResponseEntity.ok(notes);
  }

  @PostMapping
  public ResponseEntity<NoteResponseDTO> create(@Valid @RequestBody NoteRequestDTO noteRequestDTO) {
    log.debug(String.format(requestLogTemplate),
        "POST", "", noteRequestDTO);
    final var note = noteService.create(noteRequestDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(note);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<NoteResponseDTO> updateNoteByFields(@PathVariable UUID id,
                                                            @RequestBody NoteRequestDTO noteRequestDTO) {
    log.debug(String.format(requestLogTemplate),
        "PATCH", id, noteRequestDTO);
    final var note = noteService.updateByFields(id, noteRequestDTO);
    return ResponseEntity.ok(note);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    log.debug(String.format(requestLogTemplate),
        "DELETE", id, "");
    noteService.delete(id);
    return ResponseEntity.status(204).build();
  }

}
