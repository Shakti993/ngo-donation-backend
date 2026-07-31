package com.ngo.campaign.service;

import com.ngo.campaign.dto.CampaignResponseDto;
import com.ngo.campaign.dto.CreateCampaignRequestDto;
import com.ngo.campaign.dto.UpdateCampaignRequestDto;

import java.util.List;

public interface CampaignService {

    CampaignResponseDto createCampaign(CreateCampaignRequestDto request,String adminEmail);

    List<CampaignResponseDto> getPublicCampaigns();

    CampaignResponseDto getPublicCampaignById(Long id);

    List<CampaignResponseDto> getCampaignsByCategory(Long categoryId);

    List<CampaignResponseDto> getAllCampaigns();

    CampaignResponseDto getCampaignById(Long id);

    CampaignResponseDto updateCampaign(Long id,UpdateCampaignRequestDto request);
}