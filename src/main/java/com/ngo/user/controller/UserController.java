package com.ngo.user.controller;

import com.ngo.common.dto.ApiResponse;
import com.ngo.user.dto.CurrentUserResponseDto;
import com.ngo.user.service.UserDashboardService;
import com.ngo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ngo.user.dto.UserDashboardDto;
import com.ngo.donation.dto.UserDonationDto;
import com.ngo.donation.service.UserDonationService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDashboardService userDashboardService;
    private final UserDonationService userDonationService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<CurrentUserResponseDto>> getCurrentUser() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User details fetched successfully",
                        userService.getCurrentUser()
                )
        );
    }

    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<UserDashboardDto>> getDashboard() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Dashboard loaded successfully",
                        userDashboardService.getDashboard()));
    }

    @GetMapping("/donations")
    public ResponseEntity<ApiResponse<List<UserDonationDto>>> getUserDonations() {

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Donations fetched successfully",
                        userDonationService.getUserDonations()));
    }
}