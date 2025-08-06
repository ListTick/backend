package com.pro.list_tick.bucket_list.controller;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.dto.BucketListRequestDTO;
import com.pro.list_tick.bucket_list.dto.BucketListRequestUpdateDTO;
import com.pro.list_tick.bucket_list.dto.BucketListResponseDTO;
import com.pro.list_tick.bucket_list.mapper.BucketListMapper;
import com.pro.list_tick.bucket_list.service.BucketListService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bucket-lists")
@AllArgsConstructor
@Slf4j
@Validated
public class BucketListController {

    private final BucketListService bucketListService;
    private final String requestLogTemplate = "Received request, method: {}, context path: /api/bucket-lists/{}, body {}";

    @GetMapping
    public ResponseEntity<List<BucketListResponseDTO>> getAllByAccountId() {
        log.debug(String.format(requestLogTemplate),
                "GET", "", "");
        var bucketListDTOs = bucketListService.getAllDTOByAccountId();
        return ResponseEntity.ok(bucketListDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BucketListResponseDTO> getById(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "GET", id, "");
        var bucketLists = bucketListService.getById(id);
        return ResponseEntity.ok(BucketListMapper.toResponseDTO(bucketLists));
    }

    @PostMapping
    public ResponseEntity<BucketListResponseDTO> create(@Valid @RequestBody
                                                          BucketListRequestDTO bucketListRequestDTO) {
        log.debug(String.format(requestLogTemplate),
                "POST", "", bucketListRequestDTO);
        var bucketList = bucketListService.create(bucketListRequestDTO);
        return ResponseEntity.status(201).body(bucketList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BucketListResponseDTO> update(@PathVariable UUID id,
                                                          @Valid @RequestBody
                                                          BucketListRequestUpdateDTO bucketListRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PUT", id, bucketListRequestUpdateDTO);
        var bucketList = bucketListService.update(id, bucketListRequestUpdateDTO);
        return ResponseEntity.ok(bucketList);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BucketListResponseDTO> updateByFields(@PathVariable UUID id,
                                                                  @RequestBody
                                                                  BucketListRequestUpdateDTO bucketListRequestUpdateDTO) {
        log.debug(String.format(requestLogTemplate),
                "PATCH", id, bucketListRequestUpdateDTO);
        var bucketList = bucketListService.updateByFields(id, bucketListRequestUpdateDTO);
        return ResponseEntity.ok(bucketList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.debug(String.format(requestLogTemplate),
                "DELETE", id, "");
        bucketListService.delete(id);
        return ResponseEntity.status(204).build();
    }

}
