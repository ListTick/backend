package com.pro.list_tick.task.service;

import com.pro.list_tick.task.dto.TagRequestDto;
import com.pro.list_tick.task.dto.TagResponseDto;
import com.pro.list_tick.task.model.Tag;

import java.util.List;
import java.util.UUID;

public interface TagService {
    TagResponseDto createTag(TagRequestDto tagRequestDto);
    List<TagResponseDto> getAllTags();
    TagResponseDto updateTag(TagRequestDto tagRequestDto, UUID tagId);
    void deleteTag(UUID tagId);
    List<Tag> getAllTagsByTaskId(UUID id);
}
