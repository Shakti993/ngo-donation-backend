package com.ngo.category.controller;

import com.ngo.category.dto.CategoryResponseDto;
import com.ngo.category.service.CategoryService;
import com.ngo.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>>
            getActiveCategories() {

        List<CategoryResponseDto> categories =
                categoryService.getActiveCategories();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Categories fetched successfully",
                        categories
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>>
            getCategoryById(@PathVariable Long id) {

        CategoryResponseDto category =
                categoryService.getActiveCategoryById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Category fetched successfully",
                        category
                )
        );
    }
}