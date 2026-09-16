package com.ngo.contact.dto;

import com.ngo.contact.enums.ContactQueryStatus;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContactQueryResponseDto {

    private Long id;

    private String name;

    private String email;

    private String mobile;

    private String subject;

    private String message;

    private ContactQueryStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}