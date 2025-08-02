package com.pro.list_tick.note.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.pro.list_tick.note.dto.NoteRequestDTO;
import com.pro.list_tick.note.dto.NoteResponseDTO;
import com.pro.list_tick.note.exception.NoteException;
import com.pro.list_tick.note.mapper.NoteMapper;
import com.pro.list_tick.note.model.Note;
import com.pro.list_tick.note.repository.NoteRepository;
import com.pro.list_tick.shared.current_user.CurrentAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;
  private final CurrentAccountService currentAccountService;

  public Note getById(UUID id) {
    var accountId = currentAccountService.getCurrentAccountId();
    log.debug("Getting a note by the id: {}", id);

    var note = noteRepository.findById(id)
        .orElseThrow(() -> {
          String errMessage = "Note not found";
          log.error("{}: {}", errMessage, id);
          return new NoteException(HttpStatus.BAD_REQUEST, errMessage);
        });
    validateOwnership(note, accountId);
    return note;
  }

  public List<NoteResponseDTO> getAllByAccountId() {
    var accountId = currentAccountService.getCurrentAccountId();
    log.debug("Getting all notes by the accountId: {}", accountId);

    return noteRepository.findAllByAccountId(accountId)
        .stream()
        .map(NoteMapper::toResponseDTO)
        .toList();
  }

  @Transactional(transactionManager = "noteTransactionManager")
  public NoteResponseDTO create(NoteRequestDTO noteRequestDTO) {
    var accountId = currentAccountService.getCurrentAccountId();
    log.debug("Creating a note for the accountId: {}, note title: {}",
        accountId, noteRequestDTO.title());

    validateTitle(accountId, noteRequestDTO.title());
    var note = NoteMapper.toModel(noteRequestDTO);
    note.setCreatedAt(LocalDate.now());
    note.setAccountId(accountId);

    var savedNote = noteRepository.save(note);
    log.info("The note has been created: {}", note.getId());
    return NoteMapper.toResponseDTO(savedNote);
  }

  @Transactional(transactionManager = "noteTransactionManager")
  public NoteResponseDTO updateByFields(UUID id, NoteRequestDTO noteRequestDTO) {
    var accountId = currentAccountService.getCurrentAccountId();
    log.info("Updating the note by fields: {}", id);

    var note = getById(id);
    if (Objects.nonNull(noteRequestDTO.title())) {
      validateTitle(accountId, noteRequestDTO.title());
      note.setTitle(noteRequestDTO.title());
    }
    if (Objects.nonNull(noteRequestDTO.description())) {
      note.setDescription(noteRequestDTO.description());
    }
    note.setModifiedAt(LocalDate.now());

    return NoteMapper.toResponseDTO(noteRepository.save(note));
  }

  @Transactional(transactionManager = "noteTransactionManager")
  public void delete(UUID id) {
    log.debug("Deleting the note: {}", id);
    final var note = getById(id);

    noteRepository.delete(note);
    log.info("Note has been deleted: {}", note.getId());
  }

  private void validateTitle(UUID accountId, String title) {
    if (noteRepository.existsByTitleAndAccountId(title, accountId)) {
      String errMessage = "Note title already exists";
      log.error("{} :{}", errMessage, title);
      throw new NoteException(HttpStatus.CONFLICT, errMessage);
    }
  }

  private void validateOwnership(Note note, UUID accountId) {
    if (!note.getAccountId().equals(accountId)) {
      String errMessage = "Access denied";
      log.error("{} {}: {}", errMessage, ",noteId: ",note.getId());
      throw new NoteException(HttpStatus.FORBIDDEN, errMessage);
    }
  }

}
