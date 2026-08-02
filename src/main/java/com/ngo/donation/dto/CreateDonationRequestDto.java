package com.ngo.donation.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateDonationRequestDto {

    @NotNull(message = "Campaign ID is required")
    private Long campaignId;

    @NotBlank(message = "Donor name is required")
    @Size(
        max = 200,
        message = "Donor name cannot exceed 200 characters"
    )
    private String donorName;

    @NotBlank(message = "Donor email is required")
    @Email(message = "Please provide a valid email address")
    @Size(
        max = 255,
        message = "Donor email cannot exceed 255 characters"
    )
    private String donorEmail;

    @Size(
        max = 20,
        message = "Donor mobile cannot exceed 20 characters"
    )
    private String donorMobile;

    @NotNull(message = "Donation amount is required")
    @DecimalMin(
        value = "1.00",
        message = "Donation amount must be at least 1.00"
    )
    private BigDecimal amount;
}