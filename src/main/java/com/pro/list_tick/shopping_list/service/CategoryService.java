package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;
import com.pro.list_tick.shopping_list.model.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    Category getById(UUID id);
    List<CategoryDTO> getAllDTOByAccountId();
    CategoryDTO create(CategoryInputDTO categoryInputDTO);
    CategoryDTO update(UUID id, CategoryInputDTO categoryInputDTO);
    CategoryDTO updateByFields(UUID id, CategoryInputDTO categoryInputDTO);
    void delete(UUID id);

}
