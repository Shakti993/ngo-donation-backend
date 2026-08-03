package com.ngo.donation.service;

import com.ngo.donation.dto.UserDonationDto;
import com.ngo.donation.entity.Donation;
import com.ngo.donation.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import com.ngo.user.entity.User;
import com.ngo.user.service.UserService;

@Service
@RequiredArgsConstructor
public class UserDonationService {

    private final DonationRepository donationRepository;
    private final UserService userService;

public List<UserDonationDto> getUserDonations() {

    User currentUser =  userService.getCurrentAuthenticatedUser();

    List<Donation> donations =
           donationRepository.findUserDonationsWithCampaign(
        currentUser.getId()
        );

    return donations.stream()
            .map(this::toDto)
            .toList();
}

    private UserDonationDto toDto(Donation donation) {

        return new UserDonationDto(
                donation.getId(),
                donation.getCampaign().getTitle(),
                donation.getAmount(),
                donation.getStatus(),
                donation.getCreatedAt()
        );
    }

}