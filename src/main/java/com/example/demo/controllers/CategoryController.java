package com.example.demo.controllers;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/categories")
//@Validated
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //hiện tất car các category
    @GetMapping("")
    public ResponseEntity< List <CategoryDTO>> getAllCategories(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit
    ) {
        List <CategoryDTO> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneCategory(@PathVariable("id") Long id, @RequestParam(required = false) String name) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("")
//    tham số truyenef voo là object
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorsMessage = bindingResult.getFieldErrors()
                    .stream().
                    map(FieldError::getDefaultMessage).
                    toList();
            return ResponseEntity.badRequest().body(errorsMessage.toString());
        }
        CategoryDTO newCategory = categoryService.createCategory(categoryDTO);

        return ResponseEntity.ok(newCategory);

    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable("id") Long id,
             @Valid @RequestBody CategoryDTO categoryDTO) {

        categoryService.updateCategory(categoryDTO, id);
        return ResponseEntity.ok("Update category");
}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Delete category with id = " + id + "successfully");
    }
}
