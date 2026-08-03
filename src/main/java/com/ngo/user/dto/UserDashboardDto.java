package com.ngo.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class UserDashboardDto {

    private Long totalDonations;

    private BigDecimal totalAmount;

    private Long campaignsSupported;

}