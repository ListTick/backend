package com.pro.list_tick.bucket_list.service;

import java.util.List;
import java.util.UUID;

import com.pro.list_tick.bucket_list.dto.CategoryRequestDTO;
import com.pro.list_tick.bucket_list.dto.CategoryResponseDTO;
import com.pro.list_tick.bucket_list.model.Category;

public interface CategoryService {

    Category getById(UUID id);
    List<CategoryResponseDTO> getAllDTOByAccountId();
    CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO update(UUID id, CategoryRequestDTO categoryRequestDTO);
    CategoryResponseDTO updateByFields(UUID id, CategoryRequestDTO categoryRequestDTO);
    void delete(UUID id);
    Category getSharedCategory();
}
