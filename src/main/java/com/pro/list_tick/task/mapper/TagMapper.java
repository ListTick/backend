package com.pro.list_tick.task.mapper;

import com.pro.list_tick.task.dto.TagRequestDto;
import com.pro.list_tick.task.dto.TagResponseDto;
import com.pro.list_tick.task.model.Tag;

import java.util.UUID;

public class TagMapper {

    private TagMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static TagResponseDto toDto(Tag tag) {
        return new TagResponseDto(
                tag.getId(),
                tag.getName(),
                tag.getColor()
        );
    }

    public static Tag toEntity(TagRequestDto tagRequestDto, UUID accountId) {
        Tag tag = new Tag();
        tag.setName(tagRequestDto.name());
        tag.setColor(tagRequestDto.color());
        tag.setAccountId(accountId);
        tag.setDeleted(false);

        return tag;
    }
}
