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
        checkIfTagNameExists(tagRequestDto.name(), currentAccountId);

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

        tagRepository.delete(tag);
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
