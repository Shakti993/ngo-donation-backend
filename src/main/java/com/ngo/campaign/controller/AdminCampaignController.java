package com.ngo.campaign.controller;

import com.ngo.campaign.dto.CampaignResponseDto;
import com.ngo.campaign.dto.CreateCampaignRequestDto;
import com.ngo.campaign.dto.UpdateCampaignRequestDto;
import com.ngo.campaign.service.CampaignService;
import com.ngo.common.dto.ApiResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/campaigns")
@RequiredArgsConstructor
public class AdminCampaignController {

    private final CampaignService campaignService;


    // CREATE CAMPAIGN
    @PostMapping
    public ResponseEntity<ApiResponse<CampaignResponseDto>>createCampaign(
                    @Valid
                    @RequestBody CreateCampaignRequestDto request,
                    Authentication authentication) {

        CampaignResponseDto campaign = campaignService.createCampaign( request, authentication.getName());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Campaign created successfully",
                                campaign
                        )
                );
    }


    // GET ALL CAMPAIGNS FOR ADMIN
    @GetMapping
    public ResponseEntity<ApiResponse<List<CampaignResponseDto>>>getAllCampaigns() {

        List<CampaignResponseDto> campaigns =
                campaignService.getAllCampaigns();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Campaigns fetched successfully",
                        campaigns
                )
        );
    }


    // GET CAMPAIGN BY ID FOR ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignResponseDto>> getCampaignById(@PathVariable Long id) {

        CampaignResponseDto campaign =
                campaignService.getCampaignById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Campaign fetched successfully",
                        campaign
                )
        );
    }


    // UPDATE CAMPAIGN
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CampaignResponseDto>>updateCampaign(
                    @PathVariable Long id,
                    @Valid
                    @RequestBody UpdateCampaignRequestDto request) {

        CampaignResponseDto campaign =
                campaignService.updateCampaign(
                        id,
                        request
                );

        return ResponseEntity.ok(new ApiResponse<>(
                        true,
                        "Campaign updated successfully",
                        campaign
                )
        );
    }
}