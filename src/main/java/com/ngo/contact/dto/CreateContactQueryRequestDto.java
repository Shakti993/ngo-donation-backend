package com.ngo.contact.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateContactQueryRequestDto {

    @NotBlank(message = "Full name is required")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])[A-Za-z ]+$",
            message = "Enter a valid full name"
    )
    @Size(
            max = 150,
            message = "Full name must not exceed 150 characters"
    )
    private String name;


    @NotBlank(message = "Email is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Enter a valid email address"
    )
    @Size(
            max = 255,
            message = "Email must not exceed 255 characters"
    )
    private String email;


    @Pattern(
            regexp = "^$|^[0-9]{10}$",
            message = "Enter a valid 10-digit mobile number"
    )
    private String mobile;


    @NotBlank(message = "Subject is required")
    @Size(
            max = 200,
            message = "Subject must not exceed 200 characters"
    )
    private String subject;


    @NotBlank(message = "Message is required")
    @Size(
            max = 2000,
            message = "Message must not exceed 2000 characters"
    )
    private String message;
}