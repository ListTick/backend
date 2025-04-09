package com.pro.list_tick.task.mapper;

import com.pro.list_tick.task.dto.TagRequestDto;
import com.pro.list_tick.task.dto.TagResponseDto;
import com.pro.list_tick.task.model.Account;
import com.pro.list_tick.task.model.Tag;

import java.util.List;
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

    public static List<TagResponseDto> toDto(List<Tag> tags) {
        return tags.stream().map(TagMapper::toDto).toList();
    }

    public static Tag toEntity(TagRequestDto tagRequestDto, UUID accountId) {
        Tag tag = new Tag();
        tag.setId(UUID.randomUUID());
        tag.setName(tagRequestDto.name());
        tag.setColor(tagRequestDto.color());

        Account account = new Account();
        account.setId(accountId);

        tag.setAccount(account);

        return tag;
    }
}
