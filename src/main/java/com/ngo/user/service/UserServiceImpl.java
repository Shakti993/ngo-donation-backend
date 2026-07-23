package com.ngo.user.service;

import com.ngo.auth.dto.RegisterRequestDto;
import com.ngo.auth.dto.LoginRequestDto;
import com.ngo.exception.BusinessException;
import com.ngo.role.entity.Role;
import com.ngo.role.repository.RoleRepository;
import com.ngo.user.dto.CurrentUserResponseDto;
import com.ngo.user.entity.User;
import com.ngo.security.jwt.JwtService;
import com.ngo.user.repository.UserRepository;
import com.ngo.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void registerUser(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }

        Role donorRole = roleRepository.findByRoleName("ROLE_DONOR")
                .orElseThrow(() -> new BusinessException("ROLE_DONOR not found"));

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(donorRole);

        userRepository.save(user);
    }

    @Override
    public LoginResponseDto loginUser(
        LoginRequestDto request) {

    User user = userRepository.findByEmail(
            request.getEmail())
            .orElseThrow(() ->
                    new BusinessException(
                            "Invalid email or password"));

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        throw new BusinessException(
                "Invalid email or password");
    }

    String token = jwtService.generateToken(
            user.getEmail());

    return new LoginResponseDto(token);
}

    @Override
public CurrentUserResponseDto getCurrentUser() {

    String email = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new BusinessException("User not found"));

    return new CurrentUserResponseDto(
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getRole().getRoleName()
    );
}

}