package com.ngo.campaign.service;

import com.ngo.campaign.dto.CampaignResponseDto;
import com.ngo.campaign.dto.CreateCampaignRequestDto;
import com.ngo.campaign.dto.UpdateCampaignRequestDto;
import com.ngo.campaign.entity.Campaign;
import com.ngo.campaign.enums.CampaignStatus;
import com.ngo.campaign.repository.CampaignRepository;
import com.ngo.category.entity.Category;
import com.ngo.category.repository.CategoryRepository;
import com.ngo.exception.BusinessException;
import com.ngo.user.entity.User;
import com.ngo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CampaignServiceImpl
        implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    // =========================
    // CREATE
    // =========================

    @Override
    public CampaignResponseDto createCampaign(CreateCampaignRequestDto request,String adminEmail) {

        validateDates(
                request.getStartDate(),
                request.getEndDate()
        );

        Category category =
                findActiveCategory(
                        request.getCategoryId()
                );

        User admin = userRepository
                .findByEmail(adminEmail)
                .orElseThrow(() ->
                        new BusinessException(
                                "Authenticated user not found"
                        )
                );

        Campaign campaign = new Campaign();

        campaign.setTitle(
                request.getTitle().trim()
        );

        campaign.setShortDescription(
                request.getShortDescription().trim()
        );

        campaign.setDescription(
                request.getDescription().trim()
        );

        campaign.setGoalAmount(
                request.getGoalAmount()
        );

        campaign.setRaisedAmount(
                BigDecimal.ZERO
        );

        campaign.setStartDate(
                request.getStartDate()
        );

        campaign.setEndDate(
                request.getEndDate()
        );

        campaign.setImageUrl(
                normalizeText(request.getImageUrl())
        );

        campaign.setCategory(category);

        campaign.setCreatedByUser(admin);

        campaign.setStatus(
                CampaignStatus.DRAFT
        );

        campaign.setIsActive(true);

        Campaign saved =
                campaignRepository.save(campaign);

        return mapToResponse(saved);
    }


    // =========================
    // PUBLIC CAMPAIGNS
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponseDto> getPublicCampaigns() {

        return campaignRepository
                .findByStatusAndIsActiveTrueAndIsDeletedFalse(
                        CampaignStatus.ACTIVE
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public CampaignResponseDto getPublicCampaignById(Long id) {

        Campaign campaign =
                findCampaign(id);

        if (campaign.getStatus()
                != CampaignStatus.ACTIVE
                || !Boolean.TRUE.equals(
                        campaign.getIsActive())) {

            throw new BusinessException(
                    "Campaign not found"
            );
        }

        return mapToResponse(campaign);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponseDto> getCampaignsByCategory(Long categoryId) {

        // Ensure category itself is active
        findActiveCategory(categoryId);

        return campaignRepository
                .findByCategoryIdAndStatusAndIsActiveTrueAndIsDeletedFalse(
                        categoryId,
                        CampaignStatus.ACTIVE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    // =========================
    // ADMIN CAMPAIGNS
    // =========================

    @Override
    @Transactional(readOnly = true)
    public List<CampaignResponseDto> getAllCampaigns() {

        return campaignRepository
                .findByIsDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public CampaignResponseDto
            getCampaignById(Long id) {

        return mapToResponse(
                findCampaign(id)
        );
    }


    @Override
    public CampaignResponseDto updateCampaign(Long id,UpdateCampaignRequestDto request) {

        Campaign campaign =
                findCampaign(id);

        validateDates(
                request.getStartDate(),
                request.getEndDate()
        );

        Category category =
                findActiveCategory(
                        request.getCategoryId()
                );

        campaign.setTitle(
                request.getTitle().trim()
        );

        campaign.setShortDescription(
                request.getShortDescription().trim()
        );

        campaign.setDescription(
                request.getDescription().trim()
        );

        campaign.setGoalAmount(
                request.getGoalAmount()
        );

        campaign.setStartDate(
                request.getStartDate()
        );

        campaign.setEndDate(
                request.getEndDate()
        );

        campaign.setImageUrl(
                normalizeText(request.getImageUrl())
        );

        campaign.setCategory(category);

        campaign.setStatus(
                request.getStatus()
        );

        campaign.setIsActive(
                request.getIsActive()
        );

        Campaign updated =
                campaignRepository.save(campaign);

        return mapToResponse(updated);
    }


    // =========================
    // HELPERS
    // =========================

    private Campaign findCampaign(Long id) {

        Campaign campaign =
                campaignRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Campaign not found"
                                )
                        );

        if (Boolean.TRUE.equals(
                campaign.getIsDeleted())) {

            throw new BusinessException(
                    "Campaign not found"
            );
        }

        return campaign;
    }


    private Category findActiveCategory(Long categoryId) {

        Category category =
                categoryRepository
                        .findById(categoryId)
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Category not found"
                                )
                        );

        if (Boolean.TRUE.equals(
                category.getIsDeleted())
                ||
                !Boolean.TRUE.equals(
                        category.getIsActive())) {

            throw new BusinessException(
                    "Category not found"
            );
        }

        return category;
    }


    private void validateDates(java.time.LocalDate startDate,java.time.LocalDate endDate) {

        if (endDate.isBefore(startDate)) {

            throw new BusinessException(
                    "End date cannot be before start date"
            );
        }
    }


    private String normalizeText(String value) {

        if (value == null) {
            return null;
        }

        String trimmed = value.trim();

        return trimmed.isEmpty()
                ? null
                : trimmed;
    }


    private CampaignResponseDto mapToResponse(Campaign campaign) {
        User creator = campaign.getCreatedByUser();

        String creatorName = creator.getFirstName()
                + " "
                + creator.getLastName();

        return new CampaignResponseDto(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getShortDescription(),
                campaign.getDescription(),
                campaign.getGoalAmount(),
                campaign.getRaisedAmount(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getStatus(),
                campaign.getImageUrl(),
                campaign.getIsActive(),

                campaign.getCategory().getId(),
                campaign.getCategory().getName(),

                creator.getId(),
                creatorName.trim(),

                campaign.getCreatedAt(),
                campaign.getUpdatedAt());
    }
}