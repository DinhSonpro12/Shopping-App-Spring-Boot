package com.example.demo.services;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;

import java.util.List;
// 8:05
public interface ICategoryService {
    CategoryDTO getCategoryById(Long id);

    List<CategoryDTO> getAllCategory();

    CategoryDTO createCategory(CategoryDTO categoryDto);

    CategoryDTO updateCategory(CategoryDTO categoryDto, Long id);

    void deleteCategory(Long id);
}
