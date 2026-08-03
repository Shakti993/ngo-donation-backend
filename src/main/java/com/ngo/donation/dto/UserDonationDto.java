package com.ngo.donation.dto;

import com.ngo.donation.enums.DonationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserDonationDto {

    private Long id;

    private String campaignTitle;

    private BigDecimal amount;

    private DonationStatus status;

    private LocalDateTime createdAt;

}