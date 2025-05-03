package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDTO> getAllByAccountId();
    CategoryDTO getById(UUID id);
    CategoryDTO create(CategoryInputDTO categoryInputDTO);
    CategoryDTO update(UUID id, CategoryDTO categoryDTO);
    CategoryDTO updateByFields(UUID id, CategoryInputDTO categoryInputDTO);
    void delete(UUID id);

}
