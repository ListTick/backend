package com.pro.list_tick.shopping_list.mapper;


import com.pro.list_tick.shopping_list.dto.CategoryResponseDTO;
import com.pro.list_tick.shopping_list.dto.CategoryRequestDTO;
import com.pro.list_tick.shopping_list.model.Category;

import java.util.Objects;

public class CategoryMapper {

    private CategoryMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CategoryResponseDTO toResponseDTO(Category category) {
        return new CategoryResponseDTO(
            category.getId(),
            category.getName(),
            category.getColour()
        );
    }

    public static Category toModel(CategoryRequestDTO categoryRequestDTO) {
        Category category = new Category();
        category.setName(categoryRequestDTO.name());
        if(Objects.nonNull(categoryRequestDTO.colour())) {
            category.setColour(categoryRequestDTO.colour());
        }
        return category;
    }

}
