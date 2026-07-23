package com.ngo.user.controller;

import com.ngo.common.dto.ApiResponse;
import com.ngo.user.dto.CurrentUserResponseDto;
import com.ngo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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
}