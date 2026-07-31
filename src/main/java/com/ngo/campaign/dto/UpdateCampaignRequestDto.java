package com.ngo.campaign.dto;

import com.ngo.campaign.enums.CampaignStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class UpdateCampaignRequestDto {

    @NotBlank(message = "Campaign title is required")
    @Size(
        max = 200,
        message = "Campaign title cannot exceed 200 characters"
    )
    private String title;


    @NotBlank(message = "Short description is required")
    @Size(
        max = 500,
        message = "Short description cannot exceed 500 characters"
    )
    private String shortDescription;


    @NotBlank(message = "Campaign description is required")
    private String description;


    @NotNull(message = "Goal amount is required")
    @DecimalMin(
        value = "1.00",
        message = "Goal amount must be greater than 0"
    )
    private BigDecimal goalAmount;


    @NotNull(message = "Start date is required")
    private LocalDate startDate;


    @NotNull(message = "End date is required")
    private LocalDate endDate;


    @Size(
        max = 1000,
        message = "Image URL cannot exceed 1000 characters"
    )
    private String imageUrl;


    @NotNull(message = "Category is required")
    private Long categoryId;


    @NotNull(message = "Campaign status is required")
    private CampaignStatus status;


    @NotNull(message = "Active status is required")
    private Boolean isActive;
}