package com.ngo.donation.repository;

import com.ngo.donation.entity.Donation;
import com.ngo.donation.enums.DonationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {


    /*
     * Find donation using Razorpay Order ID.
     *
     * This will be important during
     * payment verification.
     */
    Optional<Donation> findByRazorpayOrderId(String razorpayOrderId);


    /*
     * Donation history for a campaign.
     */
    List<Donation> findByCampaignId(Long campaignId);


    /*
     * Donation history for a registered user.
     */
    List<Donation> findByUserId(Long userId );


    /*
     * Useful for filtering donations
     * by payment status.
     */
    List<Donation> findByStatus(DonationStatus status );

}