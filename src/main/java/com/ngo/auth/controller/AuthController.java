package com.ngo.auth.controller;

import com.ngo.auth.dto.LoginRequestDto;
import com.ngo.auth.dto.LoginResponseDto;
import com.ngo.auth.dto.RegisterRequestDto;
import com.ngo.common.dto.ApiResponse;
import com.ngo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Void>> register(
          @Valid @RequestBody RegisterRequestDto request) {

        userService.registerUser(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "User registered successfully",
                        null
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto request) {

        LoginResponseDto response =
                userService.loginUser(request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Login successful",
                        response
                )
        );
    }
}