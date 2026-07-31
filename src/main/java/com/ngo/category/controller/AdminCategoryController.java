package com.ngo.category.controller;

import java.util.List;
import com.ngo.category.dto.CategoryResponseDto;
import com.ngo.category.dto.CreateCategoryRequestDto;
import com.ngo.category.dto.UpdateCategoryRequestDto;
import com.ngo.category.service.CategoryService;
import com.ngo.common.dto.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponseDto>>> getAllCategories() {

        List<CategoryResponseDto> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Categories fetched successfully",
                        categories));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDto>>
            createCategory(
                    @Valid
                    @RequestBody CreateCategoryRequestDto request) {

        CategoryResponseDto category =
                categoryService.createCategory(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Category created successfully",
                                category
                        )
                );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>>
            getCategoryById(
                    @PathVariable Long id) {

        CategoryResponseDto category =
                categoryService.getCategoryById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Category fetched successfully",
                        category
                )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDto>>
            updateCategory(
                    @PathVariable Long id,
                    @Valid
                    @RequestBody UpdateCategoryRequestDto request) {

        CategoryResponseDto category =
                categoryService.updateCategory(
                        id,
                        request
                );

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Category updated successfully",
                        category
                )
        );
    }
}