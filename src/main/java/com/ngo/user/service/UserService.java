package com.ngo.user.service;

import com.ngo.auth.dto.RegisterRequestDto;
import com.ngo.user.dto.CurrentUserResponseDto;
import com.ngo.auth.dto.LoginRequestDto;
import com.ngo.auth.dto.LoginResponseDto;


public interface UserService {

    void registerUser(RegisterRequestDto request);
     LoginResponseDto loginUser(LoginRequestDto request);
     CurrentUserResponseDto getCurrentUser();
}