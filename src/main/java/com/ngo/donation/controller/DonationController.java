package com.ngo.donation.controller;

import com.ngo.common.dto.ApiResponse;
import com.ngo.donation.dto.CreateDonationRequestDto;
import com.ngo.donation.dto.DonationResponseDto;
import com.ngo.donation.dto.VerifyPaymentRequestDto;
import com.ngo.donation.service.DonationService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;


    // =====================================================
    // CREATE DONATION
    // =====================================================

    @PostMapping
    public ResponseEntity<ApiResponse<DonationResponseDto>> createDonation(
            @Valid
            @RequestBody
            CreateDonationRequestDto request
        ) {

        DonationResponseDto donation = donationService.createDonation(request);


        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                new ApiResponse<>(
                    true,
                    "Donation created successfully",
                    donation
                )
            );
    }


    // =====================================================
    // GET DONATION BY ID
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DonationResponseDto>> getDonation(@PathVariable Long id ) 
    {

        DonationResponseDto donation =  donationService.getDonation(id);


        return ResponseEntity.ok(
            new ApiResponse<>(
                true,
                "Donation fetched successfully",
                donation
            )
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<DonationResponseDto>> verifyPayment(
            @Valid @RequestBody VerifyPaymentRequestDto request) {

        DonationResponseDto donation = donationService.verifyPayment(
                request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Payment verified successfully",
                        donation));
    }
}