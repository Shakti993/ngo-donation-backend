package com.ngo.donation.service;

import com.ngo.campaign.entity.Campaign;
import com.ngo.campaign.enums.CampaignStatus;
import com.ngo.campaign.repository.CampaignRepository;

import com.ngo.donation.dto.CreateDonationRequestDto;
import com.ngo.donation.dto.DonationResponseDto;
import com.ngo.donation.dto.VerifyPaymentRequestDto;
import com.ngo.donation.entity.Donation;
import com.ngo.donation.enums.DonationStatus;
import com.ngo.donation.repository.DonationRepository;

import com.ngo.exception.BusinessException;

import com.ngo.payment.service.RazorpayService;

import com.razorpay.Order;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import com.ngo.user.repository.UserRepository;
import com.ngo.user.entity.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Service
@RequiredArgsConstructor
public class DonationService {

    private final DonationRepository donationRepository;

    private final CampaignRepository campaignRepository;

    private final UserRepository userRepository;

    private final RazorpayService razorpayService;


    // =====================================================
    // CREATE DONATION
    // =====================================================

    @Transactional
    public DonationResponseDto createDonation(CreateDonationRequestDto request ) {

        // -----------------------------------------
        // 1. Find campaign
        // -----------------------------------------

        Campaign campaign = campaignRepository.findById(request.getCampaignId()).orElseThrow(() ->
                        new IllegalArgumentException("Campaign not found"));


        // -----------------------------------------
        // 2. Check soft deletion
        // -----------------------------------------

        if (Boolean.TRUE.equals(campaign.getIsDeleted())) {

            throw new IllegalArgumentException("Campaign not found");
        }


        // -----------------------------------------
        // 3. Check campaign active flag
        // -----------------------------------------

        if (!Boolean.TRUE.equals(campaign.getIsActive())) {
            throw new BusinessException( "Campaign is not active");
        }


        // -----------------------------------------
        // 4. Check campaign status
        // -----------------------------------------

        if (campaign.getStatus() != CampaignStatus.ACTIVE ) {

            throw new BusinessException("Campaign is not accepting donations" );
        }


        // -----------------------------------------
        // 5. Create local donation
        // -----------------------------------------

        Donation donation = new Donation();

        donation.setCampaign(campaign );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        /*
         * Associate the donation with the
         * currently authenticated user.
         *
         * Guest donations remain supported.
         */
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {

            User user = userRepository.findByEmail(authentication.getName()).orElse(null);
            donation.setUser(user);

        } else {

            donation.setUser(null);

        }

        donation.setDonorName(request.getDonorName().trim());


        donation.setDonorEmail(
            request
                .getDonorEmail()
                .trim()
        );


        donation.setDonorMobile(
            normalizeOptional(
                request.getDonorMobile()
            )
        );


        donation.setAmount(
            request.getAmount()
        );


        donation.setCurrency(
            "INR"
        );


        donation.setStatus(
            DonationStatus.PENDING
        );


        // -----------------------------------------
        // 6. Save first to obtain donation ID
        // -----------------------------------------

        Donation savedDonation =
            donationRepository.save(
                donation
            );


        // -----------------------------------------
        // 7. Create Razorpay Order
        // -----------------------------------------

        String receipt =
            "donation_" +
            savedDonation.getId();


        try {

            Order razorpayOrder =
                razorpayService.createOrder(
                    savedDonation.getAmount(),
                    savedDonation.getCurrency(),
                    receipt
                );


            String razorpayOrderId =
                razorpayOrder.get(
                    "id"
                );


            // -------------------------------------
            // 8. Store Razorpay Order ID
            // -------------------------------------

            savedDonation.setRazorpayOrderId(
                razorpayOrderId
            );


            savedDonation =
                donationRepository.save(
                    savedDonation
                );

        } catch (Exception ex) {

            /*
             * Throwing an exception from this
             * @Transactional method causes the
             * database transaction to roll back.
             *
             * Therefore we don't keep a local
             * donation that could not initialize
             * its Razorpay order.
             */

            throw new BusinessException(
                "Unable to initialize payment. Please try again."
            );
        }


        // -----------------------------------------
        // 9. Return donation
        // -----------------------------------------

        return toResponseDto(
            savedDonation
        );
    }


    // =====================================================
    // GET DONATION BY ID
    // =====================================================

    @Transactional(readOnly = true)
    public DonationResponseDto getDonation(
        Long id
    ) {

        Donation donation =
            donationRepository
                .findById(id)
                .orElseThrow(
                    () ->
                        new IllegalArgumentException(
                            "Donation not found"
                        )
                );


        if (
            Boolean.TRUE.equals(
                donation.getIsDeleted()
            )
        ) {

            throw new IllegalArgumentException(
                "Donation not found"
            );
        }


        return toResponseDto(
            donation
        );
    }


    // =====================================================
    // GET CAMPAIGN DONATIONS
    // =====================================================

    @Transactional(readOnly = true)
    public List<DonationResponseDto>getCampaignDonations(Long campaignId ) {

        return donationRepository
            .findByCampaignId(
                campaignId
            )
            .stream()

            .filter(
                donation ->
                    !Boolean.TRUE.equals(
                        donation.getIsDeleted()
                    )
            )

            .map(
                this::toResponseDto
            )

            .toList();
    }


    // =====================================================
    // ENTITY -> DTO
    // =====================================================

    private DonationResponseDto toResponseDto(
        Donation donation
    ) {

        return DonationResponseDto
            .builder()

            .id(
                donation.getId()
            )

            .campaignId(
                donation
                    .getCampaign()
                    .getId()
            )

            .campaignTitle(
                donation
                    .getCampaign()
                    .getTitle()
            )

            .userId(
                donation.getUser() != null
                    ? donation
                        .getUser()
                        .getId()
                    : null
            )

            .donorName(
                donation.getDonorName()
            )

            .donorEmail(
                donation.getDonorEmail()
            )

            .donorMobile(
                donation.getDonorMobile()
            )

            .amount(
                donation.getAmount()
            )

            .razorpayAmount(donation
                     .getAmount()
                     .multiply(BigDecimal.valueOf(100))
                     .intValue()
                    )

            .currency(
                donation.getCurrency()
            )

            .status(
                donation.getStatus()
            )

            .razorpayOrderId(
                donation.getRazorpayOrderId()
            )

            .razorpayPaymentId(
                donation.getRazorpayPaymentId()
            )

            .createdAt(
                donation.getCreatedAt()
            )

            .build();
    }


    // =====================================================
    // NORMALIZE OPTIONAL STRING
    // =====================================================

    private String normalizeOptional(
        String value
    ) {

        if (
            value == null ||
            value.isBlank()
        ) {

            return null;
        }

        return value.trim();
    }

    @Transactional
    public DonationResponseDto verifyPayment(
            VerifyPaymentRequestDto request) {

        Donation donation = donationRepository
                .findById(
                        request.getDonationId())
                .orElseThrow(
                        () -> new IllegalArgumentException(
                                "Donation not found"));

        if (Boolean.TRUE.equals(
                donation.getIsDeleted())) {

            throw new IllegalArgumentException(
                    "Donation not found");
        }

        // Make verification idempotent.
        if (donation.getStatus() == DonationStatus.SUCCESS) {

            return toResponseDto(
                    donation);
        }

        /*
         * Order ID received from the browser must
         * match the order associated with this
         * donation in our database.
         */
        if (donation.getRazorpayOrderId() == null ||
                !donation
                        .getRazorpayOrderId()
                        .equals(
                                request.getRazorpayOrderId())) {

            throw new BusinessException(
                    "Invalid Razorpay order");
        }

        boolean signatureValid = razorpayService
                .verifyPaymentSignature(
                        request.getRazorpayOrderId(),
                        request.getRazorpayPaymentId(),
                        request.getRazorpaySignature());

        if (!signatureValid) {

            throw new BusinessException(
                    "Payment verification failed");
        }

        /*
         * Payment is now verified.
         */
        donation.setRazorpayPaymentId(
                request.getRazorpayPaymentId());

        donation.setRazorpaySignature(
                request.getRazorpaySignature());

        donation.setStatus(
                DonationStatus.SUCCESS);

        /*
         * Increase raised amount only after
         * successful payment verification.
         */
        Campaign campaign = donation.getCampaign();

        BigDecimal currentRaisedAmount = campaign.getRaisedAmount() != null
                ? campaign.getRaisedAmount()
                : BigDecimal.ZERO;

        campaign.setRaisedAmount(
                currentRaisedAmount.add(
                        donation.getAmount()));

        campaignRepository.save(
                campaign);

        Donation savedDonation = donationRepository.save(
                donation);

        return toResponseDto(
                savedDonation);
    }
}