package com.ngo.donation.repository;

import com.ngo.donation.entity.Donation;
import com.ngo.donation.enums.DonationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

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

    @Query("""
            SELECT d
            FROM Donation d
            JOIN FETCH d.campaign
            WHERE d.user.id = :userId
              AND d.isDeleted = false
            ORDER BY d.createdAt DESC
            """)
    List<Donation> findUserDonationsWithCampaign(
            @Param("userId") Long userId);

    @Query("""
            SELECT COUNT(d)
            FROM Donation d
            WHERE d.user.id = :userId
              AND d.isDeleted = false
            """)
    Long countUserDonations(
            @Param("userId") Long userId);

    @Query("""
            SELECT COALESCE(SUM(d.amount), 0)
            FROM Donation d
            WHERE d.user.id = :userId
              AND d.status = com.ngo.donation.enums.DonationStatus.SUCCESS
              AND d.isDeleted = false
            """)
    BigDecimal getTotalDonationAmount(
            @Param("userId") Long userId);

    @Query("""
            SELECT COUNT(DISTINCT d.campaign.id)
            FROM Donation d
            WHERE d.user.id = :userId
              AND d.status = com.ngo.donation.enums.DonationStatus.SUCCESS
              AND d.isDeleted = false
            """)
    Long countSupportedCampaigns(
            @Param("userId") Long userId);
            

}