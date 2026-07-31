package com.ngo.category.service;

import com.ngo.category.dto.CategoryResponseDto;
import com.ngo.category.dto.CreateCategoryRequestDto;
import com.ngo.category.dto.UpdateCategoryRequestDto;

import java.util.List;

public interface CategoryService {

    CategoryResponseDto createCategory(CreateCategoryRequestDto request);

    List<CategoryResponseDto> getActiveCategories();

    CategoryResponseDto getCategoryById(Long id);

    CategoryResponseDto updateCategory(Long id, UpdateCategoryRequestDto request);
   
    CategoryResponseDto getActiveCategoryById(Long id);

    List<CategoryResponseDto> getAllCategories();
}