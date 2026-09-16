package com.ngo.user.service;

import com.ngo.auth.dto.RegisterRequestDto;
import com.ngo.user.dto.CurrentUserResponseDto;
import com.ngo.user.entity.User;
import com.ngo.auth.dto.LoginRequestDto;
import com.ngo.auth.dto.LoginResponseDto;
import com.ngo.user.dto.UpdateProfileRequestDto;
import com.ngo.user.dto.ChangePasswordRequestDto;


public interface UserService {

    void registerUser(RegisterRequestDto request);
     LoginResponseDto loginUser(LoginRequestDto request);
     CurrentUserResponseDto getCurrentUser();
     User getCurrentAuthenticatedUser();
     void updateProfile(UpdateProfileRequestDto request);
     void changePassword(ChangePasswordRequestDto request);
}