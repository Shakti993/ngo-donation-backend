package com.ngo.campaign.repository;

import com.ngo.campaign.entity.Campaign;
import com.ngo.campaign.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByStatusAndIsActiveTrueAndIsDeletedFalse( CampaignStatus status);

    List<Campaign> findByCategoryIdAndStatusAndIsActiveTrueAndIsDeletedFalse(Long categoryId, CampaignStatus status);

    List<Campaign> findByIsDeletedFalse();
}