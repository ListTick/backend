package com.pro.list_tick.task.controller;

import com.pro.list_tick.task.dto.TagRequestDto;
import com.pro.list_tick.task.dto.TagResponseDto;
import com.pro.list_tick.task.service.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagServiceImpl tagServiceImpl;

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagRequestDto tagRequestDto) {
        TagResponseDto tagResponseDto = tagServiceImpl.createTag(tagRequestDto);

        return ResponseEntity.ok(tagResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<TagResponseDto>> getTags() {
        List<TagResponseDto> tags = tagServiceImpl.getAllTags();

        return ResponseEntity.ok(tags);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagResponseDto> updateTag(@RequestBody TagRequestDto tagRequestDto,
                                                    @PathVariable UUID tagId) {
        TagResponseDto tagResponseDto = tagServiceImpl.updateTag(tagRequestDto, tagId);

        return ResponseEntity.ok(tagResponseDto);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<String> deleteTag(@PathVariable UUID tagId) {
        tagServiceImpl.deleteTag(tagId);

        return ResponseEntity.ok("Tag deleted");
    }
}
