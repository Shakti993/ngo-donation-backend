package com.ngo.category.service;

import com.ngo.category.dto.CategoryResponseDto;
import com.ngo.category.dto.CreateCategoryRequestDto;
import com.ngo.category.dto.UpdateCategoryRequestDto;
import com.ngo.category.entity.Category;
import com.ngo.category.repository.CategoryRepository;
import com.ngo.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public CategoryResponseDto createCategory(CreateCategoryRequestDto request) {

        String name = request.getName().trim();

        if (categoryRepository
                .existsByNameIgnoreCase(name)) {

            throw new BusinessException(
                    "Category already exists"
            );
        }

        Category category = new Category();

        category.setName(name);
        category.setDescription(
                normalizeDescription(
                        request.getDescription()
                )
        );

        Category savedCategory =
                categoryRepository.save(category);

        return mapToResponse(savedCategory);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {

        return categoryRepository
                .findAll()
                .stream()
                .filter(category -> !Boolean.TRUE.equals(
                        category.getIsDeleted()))
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<CategoryResponseDto> getActiveCategories() {

        return categoryRepository
                .findByIsActiveTrueAndIsDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    public CategoryResponseDto getCategoryById(Long id) {

        Category category = findCategory(id);

        return mapToResponse(category);
    }

    @Override
    public CategoryResponseDto getActiveCategoryById(Long id) {

        Category category = findCategory(id);

        if (!Boolean.TRUE.equals(category.getIsActive())) {
            throw new BusinessException(
                    "Category not found");
        }

        return mapToResponse(category);
    }


    @Override
    public CategoryResponseDto updateCategory(Long id,UpdateCategoryRequestDto request) {

        Category category = findCategory(id);

        String name = request.getName().trim();

        categoryRepository
                .findByNameIgnoreCase(name)
                .filter(existing ->
                        !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessException(
                            "Category already exists"
                    );
                });

        category.setName(name);

        category.setDescription(
                normalizeDescription(
                        request.getDescription()
                )
        );

        category.setIsActive(
                request.getIsActive()
        );

        Category updatedCategory =
                categoryRepository.save(category);

        return mapToResponse(updatedCategory);
    }


    private Category findCategory(Long id) {

        Category category =
                categoryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Category not found"
                                )
                        );

        if (Boolean.TRUE.equals(
                category.getIsDeleted())) {

            throw new BusinessException(
                    "Category not found"
            );
        }

        return category;
    }


    private String normalizeDescription(String description) {

        if (description == null) {
            return null;
        }

        String trimmed =
                description.trim();

        return trimmed.isEmpty()
                ? null
                : trimmed;
    }


    private CategoryResponseDto mapToResponse( Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getIsActive(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}