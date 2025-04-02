package com.pro.list_tick.shopping_list.service;

import com.pro.list_tick.shopping_list.dto.CategoryDTO;
import com.pro.list_tick.shopping_list.dto.CategoryInputDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDTO> getAll();
    List<CategoryDTO> getAllByAccountId(UUID userId);
    CategoryDTO getById(UUID id);
    CategoryDTO create(UUID userId, CategoryInputDTO categoryInputDTO);
    CategoryDTO update(UUID id, CategoryDTO categoryDTO);
    CategoryDTO updateByFields(UUID id, CategoryInputDTO categoryInputDTO);
    void delete(UUID id);

}
