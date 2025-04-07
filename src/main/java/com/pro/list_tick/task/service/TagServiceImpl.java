package com.pro.list_tick.task.service;

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
    private final TaskTagServiceImpl taskTagServiceImpl;
    private final TagRepository tagRepository;

    @Transactional
    public TagResponseDto createTag(TagRequestDto tagRequestDto) {
        UUID currentUserAccountId = UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc"); //TODO how do we check the currentUser
        Tag tagEntity = tagRepository.findByName(tagRequestDto.name(), currentUserAccountId);

        if (tagEntity != null && tagEntity.getName().equals(tagRequestDto.name())) {
            throw new TagNameAlreadyUsedException("Tag name is already used!");
        }

        Tag tag = TagMapper.toEntity(tagRequestDto, currentUserAccountId);
        tagRepository.save(tag);
        return TagMapper.toDto(tag);
    }

    public List<TagResponseDto> getAllTags() {
        UUID currentUserAccountId = UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc"); //TODO how do we check the currentUser
        List<Tag> tags = tagRepository.findAllByAccountId(currentUserAccountId);

        return tags.stream().map(TagMapper::toDto).toList();
    }

    @Transactional
    public TagResponseDto updateTag(TagRequestDto tagRequestDto, UUID tagId) {
        Tag tag = findTagById(tagId);
        UUID currentUserAccountId = UUID.fromString("0a247225-f9b9-4021-8848-75f56fb6fedc");  //TODO how do we check the currentUser

        Tag tagEntity = tagRepository.findByName(tagRequestDto.name(), currentUserAccountId);

        if (tagEntity != null && !tagEntity.getId().equals(tag.getId())) {
            throw new TagNameAlreadyUsedException("Tag name is already used!");
        }

        tag.setName(tagRequestDto.name());
        tag.setColor(tagRequestDto.color());

        tagRepository.save(tag);

        return TagMapper.toDto(tag);
    }

    @Transactional
    public void deleteTag(UUID tagId) {
        Tag tag = findTagById(tagId);

        tagRepository.delete(tag);
    }

    public List<Tag> getAllTagsByTaskId(UUID id) {
        List<TaskTag> taskTags = taskTagServiceImpl.getAllTaskTagsByTaskId(id);
        List<UUID> tagIds = taskTags.stream().map(TaskTag::getTagId).toList();

        return tagRepository.findAllById(tagIds);
    }

    private Tag findTagById(UUID tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag doesn't exist!"));
    }
}
