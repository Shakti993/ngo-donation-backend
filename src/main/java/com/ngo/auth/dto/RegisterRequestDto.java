package com.ngo.auth.dto;

import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDto {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Mobile number must be 10 digits"
    )
    private String mobile;

    @Size(
        min = 8,
        message = "Password must be at least 8 characters"
    )
    private String password;
}