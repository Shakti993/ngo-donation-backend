package com.ngo.campaign.controller;

import com.ngo.campaign.dto.CampaignResponseDto;
import com.ngo.campaign.service.CampaignService;
import com.ngo.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;


    // GET ALL PUBLIC ACTIVE CAMPAIGNS
    @GetMapping
    public ResponseEntity<ApiResponse<List<CampaignResponseDto>>> getPublicCampaigns() {
        List<CampaignResponseDto> campaigns =
                campaignService.getPublicCampaigns();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Campaigns fetched successfully",
                        campaigns
                )
        );
    }


    // GET PUBLIC CAMPAIGN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignResponseDto>>getPublicCampaignById(@PathVariable Long id) {
        CampaignResponseDto campaign =
                campaignService
                        .getPublicCampaignById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Campaign fetched successfully",
                        campaign
                )
        );
    }


    // GET PUBLIC CAMPAIGNS BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<CampaignResponseDto>>> getCampaignsByCategory(@PathVariable Long categoryId) {
        List<CampaignResponseDto> campaigns =
                campaignService
                        .getCampaignsByCategory(
                                categoryId
                        );

        return ResponseEntity.ok(new ApiResponse<>(
                        true,
                        "Campaigns fetched successfully",
                        campaigns
                )
        );
    }
}