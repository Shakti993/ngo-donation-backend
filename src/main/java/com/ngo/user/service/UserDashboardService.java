package com.ngo.user.service;

import com.ngo.donation.repository.DonationRepository;
import com.ngo.exception.BusinessException;
import com.ngo.user.dto.UserDashboardDto;
import com.ngo.user.entity.User;
import com.ngo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDashboardService {

    private final DonationRepository donationRepository;

    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public UserDashboardDto getDashboard() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getName())) {

            throw new BusinessException(
                    "User is not authenticated");
        }

        User user =
                userRepository
                        .findByEmail(authentication.getName())
                        .orElseThrow(() ->
                                new BusinessException(
                                        "User not found"));

        Long totalDonations =
                donationRepository.countUserDonations(
                        user.getId());

        return UserDashboardDto
                .builder()
                .totalDonations(
                        totalDonations)
                .totalAmount(
                        donationRepository.getTotalDonationAmount(
                                user.getId()))
                .campaignsSupported(
                        donationRepository.countSupportedCampaigns(
                                user.getId()))
                .build();
    }
}