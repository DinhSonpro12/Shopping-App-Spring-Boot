package com.example.demo.services;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return mapToDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryDTO> categoryDTOList = categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();

        return categoryDTOList;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDto) {
        Category newCategory = categoryRepository.save(mapToEntity(categoryDto));

        return mapToDTO(newCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDto, Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setName(categoryDto.getName());
        return mapToDTO(categoryRepository.save(existingCategory));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO mapToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    private Category mapToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        return category;
    }
}
