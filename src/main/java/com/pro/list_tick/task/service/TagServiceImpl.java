package com.pro.list_tick.task.service;

import com.pro.list_tick.shared.current_user.CurrentAccountService;
import com.pro.list_tick.task.dto.TagRequestDto;
import com.pro.list_tick.task.dto.TagResponseDto;
import com.pro.list_tick.task.exception.TagNameAlreadyUsedException;
import com.pro.list_tick.task.mapper.TagMapper;
import com.pro.list_tick.task.model.Tag;
import com.pro.list_tick.task.model.TaskTag;
import com.pro.list_tick.task.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TaskTagService taskTagService;
    private final TagRepository tagRepository;
    private final CurrentAccountService currentAccountService;

    @Transactional
    public TagResponseDto createTag(TagRequestDto tagRequestDto) {
        UUID currentAccountId = currentAccountService.getCurrentAccountId();
        checkIfTagNameExists(tagRequestDto.name(), currentAccountId);

        Tag tag = TagMapper.toEntity(tagRequestDto, currentAccountId);
        tagRepository.save(tag);
        return TagMapper.toDto(tag);
    }

    public List<TagResponseDto> getAllTags() {
        UUID currentAccountId = currentAccountService.getCurrentAccountId();
        List<Tag> tags = tagRepository.findAllByAccountId(currentAccountId);

        return tags.stream().map(TagMapper::toDto).toList();
    }

    @Transactional
    public TagResponseDto updateTag(TagRequestDto tagRequestDto, UUID tagId) {
        Tag tag = getTagById(tagId);
        UUID currentAccountId = currentAccountService.getCurrentAccountId();
        checkIfTagNameExists(tagRequestDto.name(), currentAccountId);

        tag.setName(tagRequestDto.name());
        tag.setColor(tagRequestDto.color());

        tagRepository.save(tag);

        return TagMapper.toDto(tag);
    }

    @Transactional
    public void deleteTag(UUID tagId) {
        Tag tag = getTagById(tagId);

        tagRepository.delete(tag);
    }

    public List<Tag> getAllTagsByTaskId(UUID id) {
        List<TaskTag> taskTags = taskTagService.getAllTaskTagsByTaskId(id);
        List<UUID> tagIds = taskTags.stream().map(TaskTag::getTagId).toList();

        return tagRepository.findAllById(tagIds);
    }

    private void checkIfTagNameExists(String name, UUID currentAccountId) {
        if (tagRepository.existsByName(name, currentAccountId)) {
            throw new TagNameAlreadyUsedException("Tag name is already used!");
        }
    }

    private Tag getTagById(UUID tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag doesn't exist!"));
    }
}
