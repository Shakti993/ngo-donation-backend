package com.ngo.donation.dto;

import com.ngo.donation.enums.DonationStatus;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class DonationResponseDto {

    private Long id;
    
    private Integer razorpayAmount;

    private Long campaignId;

    private String campaignTitle;

    private Long userId;

    private String donorName;

    private String donorEmail;

    private String donorMobile;

    private BigDecimal amount;

    private String currency;

    private DonationStatus status;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private LocalDateTime createdAt;
}