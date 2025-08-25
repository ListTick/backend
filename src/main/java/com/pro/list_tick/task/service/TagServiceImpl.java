package com.pro.list_tick.task.service;

import com.pro.list_tick.shared.CurrentAccountAPI;
import com.pro.list_tick.task.dto.TagRequestDto;
import com.pro.list_tick.task.dto.TagResponseDto;
import com.pro.list_tick.task.exception.TagNameAlreadyUsedException;
import com.pro.list_tick.task.mapper.TagMapper;
import com.pro.list_tick.task.model.Tag;
import com.pro.list_tick.task.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final CurrentAccountAPI currentAccountAPI;

    @Transactional
    public TagResponseDto createTag(TagRequestDto tagRequestDto) {
        UUID currentAccountId = currentAccountAPI.getCurrentAccountId();

         if (checkIfTagNameExists(tagRequestDto.name(), currentAccountId)) {
             if (checkIfTagIsDeleted(tagRequestDto.name(), currentAccountId)) {
                 Tag tag = undeleteTag(tagRequestDto.name(), currentAccountId);

                 tagRepository.save(tag);
                 return TagMapper.toDto(tag);
             } else {
                 throw new TagNameAlreadyUsedException("Tag name already exists");
             }
         }

        Tag tag = TagMapper.toEntity(tagRequestDto, currentAccountId);
        tagRepository.save(tag);
        return TagMapper.toDto(tag);
    }
    public List<TagResponseDto> getAllTags() {
        UUID currentAccountId = currentAccountAPI.getCurrentAccountId();
        List<Tag> tags = tagRepository.findAllByAccountId(currentAccountId);

        return tags.stream().map(TagMapper::toDto).toList();
    }

    @Transactional
    public TagResponseDto updateTag(TagRequestDto tagRequestDto, UUID tagId) {
        Tag tag = getTagById(tagId);
        UUID currentAccountId = currentAccountAPI.getCurrentAccountId();

        if (!Objects.equals(tagRequestDto.name(),tag.getName())) {
            checkIfTagNameExists(tagRequestDto.name(), currentAccountId);
        }

        tag.setName(tagRequestDto.name());
        tag.setColor(tagRequestDto.color());

        tagRepository.save(tag);

        return TagMapper.toDto(tag);
    }

    @Transactional
    public void deleteTag(UUID tagId) {
        Tag tag = getTagById(tagId);
        tag.setDeleted(true);

        tagRepository.save(tag);
    }

    private Tag undeleteTag(String name, UUID currentAccountId) {
        Tag tag = tagRepository.findByName(name, currentAccountId);

        tag.setDeleted(false);
        tagRepository.save(tag);

        return tag;
    }

    private boolean checkIfTagNameExists(String name, UUID currentAccountId) {
        return tagRepository.existsByName(name, currentAccountId);
    }

    private boolean checkIfTagIsDeleted(String name, UUID currentAccountId) {
        Tag tag = tagRepository.findByName(name, currentAccountId);

        return tag.isDeleted();
    }

    private Tag getTagById(UUID tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag doesn't exist!"));
    }
}
