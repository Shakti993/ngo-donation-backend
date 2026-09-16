package com.ngo.contact.dto;

import com.ngo.contact.enums.ContactQueryStatus;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateContactQueryStatusRequestDto {

    @NotNull(message = "Status is required")
    private ContactQueryStatus status;
}