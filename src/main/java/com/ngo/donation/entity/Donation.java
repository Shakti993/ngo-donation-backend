package com.ngo.donation.entity;

import com.ngo.campaign.entity.Campaign;
import com.ngo.common.entity.BaseEntity;
import com.ngo.donation.enums.DonationStatus;
import com.ngo.user.entity.User;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@Entity
@Table(name = "donations")
public class Donation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /*
     * Campaign receiving the donation.
     *
     * Every donation must belong to
     * exactly one campaign.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id",nullable = false )
    private Campaign campaign;


    /*
     * Registered donor.
     *
     * Nullable because the platform
     * supports guest donations.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" )
    private User user;


    /*
     * Donor information is stored on the
     * donation itself even when a User exists.
     *
     * This preserves the donor information
     * used for this specific transaction.
     */
    @Column(name = "donor_name",nullable = false,length = 200 )
    private String donorName;


    @Column(name = "donor_email", nullable = false,length = 255 )
    private String donorEmail;


    @Column(name = "donor_mobile",length = 20)
    private String donorMobile;


    /*
     * Donation amount.
     */
    @Column(name = "amount",nullable = false, precision = 15,scale = 2)
    private BigDecimal amount;


    /*
     * ISO currency code.
     *
     * Sprint 3 will use INR.
     */
    @Column(name = "currency", nullable = false,length = 3)
    private String currency = "INR";


    /*
     * Internal payment state.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false,length = 20)
    private DonationStatus status =  DonationStatus.PENDING;


    /*
     * Razorpay Order ID returned when
     * the backend creates an order.
     */
    @Column(name = "razorpay_order_id",length = 100)
    private String razorpayOrderId;


    /*
     * Razorpay Payment ID received after
     * the customer completes payment.
     */
    @Column(name = "razorpay_payment_id",length = 100)
    private String razorpayPaymentId;


    /*
     * Signature received from Razorpay.
     *
     * Backend verification will determine
     * whether the payment is valid.
     */
    @Column(name = "razorpay_signature",length = 255)
    private String razorpaySignature;

}