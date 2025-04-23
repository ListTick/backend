package com.pro.list_tick.shopping_list.mapper;


import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;
import com.pro.list_tick.shopping_list.model.Category;

public class CategoryMapper {

    private CategoryMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CategoryDTO toDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setColour(category.getColour());
        return categoryDTO;
    }

    public static Category toModel(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setColour(categoryDTO.getColour());
        return category;
    }

    public static Category toModel(CategoryInputDTO categoryInputDTO) {
        Category category = new Category();
        category.setName(categoryInputDTO.getName());
        category.setColour("BLACK"); //todo
        return category;
    }

}
