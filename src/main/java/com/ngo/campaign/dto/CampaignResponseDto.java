package com.ngo.campaign.dto;

import com.ngo.campaign.enums.CampaignStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignResponseDto {

    private Long id;

    private String title;

    private String shortDescription;

    private String description;

    private BigDecimal goalAmount;

    private BigDecimal raisedAmount;

    private LocalDate startDate;

    private LocalDate endDate;

    private CampaignStatus status;

    private String imageUrl;

    private Boolean isActive;


    // Category information

    private Long categoryId;

    private String categoryName;


    // Creator information

    private Long createdByUserId;

    private String createdByUserName;


    // Audit information

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}