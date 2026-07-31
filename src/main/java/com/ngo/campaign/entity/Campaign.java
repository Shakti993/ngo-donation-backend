package com.ngo.campaign.entity;

import com.ngo.campaign.enums.CampaignStatus;
import com.ngo.category.entity.Category;
import com.ngo.common.entity.BaseEntity;
import com.ngo.user.entity.User;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "campaigns")
public class Campaign extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
        name = "title",
        nullable = false,
        length = 200
    )
    private String title;


    @Column(
        name = "short_description",
        nullable = false,
        length = 500
    )
    private String shortDescription;


    @Column(
        name = "description",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String description;


    @Column(
        name = "goal_amount",
        nullable = false,
        precision = 15,
        scale = 2
    )
    private BigDecimal goalAmount;


    @Column(
        name = "raised_amount",
        nullable = false,
        precision = 15,
        scale = 2
    )
    private BigDecimal raisedAmount = BigDecimal.ZERO;


    @Column(
        name = "start_date",
        nullable = false
    )
    private LocalDate startDate;


    @Column(
        name = "end_date",
        nullable = false
    )
    private LocalDate endDate;


    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        length = 20
    )
    private CampaignStatus status =
            CampaignStatus.DRAFT;


    @Column(
        name = "image_url",
        length = 1000
    )
    private String imageUrl;


    @Column(
        name = "is_active",
        nullable = false
    )
    private Boolean isActive = true;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "category_id",
        nullable = false
    )
    private Category category;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "created_by_user_id",
        nullable = false
    )
    private User createdByUser;
}