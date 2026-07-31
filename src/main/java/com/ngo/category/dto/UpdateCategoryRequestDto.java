package com.ngo.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequestDto {

    @NotBlank(message = "Category name is required")
    @Size(
        max = 100,
        message = "Category name cannot exceed 100 characters"
    )
    private String name;

    @Size(
        max = 500,
        message = "Description cannot exceed 500 characters"
    )
    private String description;

    @NotNull(message = "Active status is required")
    private Boolean isActive;
}