package com.ngo.contact.controller;

import com.ngo.common.dto.ApiResponse;
import com.ngo.contact.dto.ContactQueryResponseDto;
import com.ngo.contact.dto.CreateContactQueryRequestDto;
import com.ngo.contact.dto.UpdateContactQueryStatusRequestDto;
import com.ngo.contact.service.ContactQueryService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactQueryController {

    private final ContactQueryService contactQueryService;


    @PostMapping("/api/contact-queries")
    public ResponseEntity<
            ApiResponse<ContactQueryResponseDto>
            > createContactQuery(
            @Valid
            @RequestBody
            CreateContactQueryRequestDto requestDto) {

        ContactQueryResponseDto response =
                contactQueryService
                        .createContactQuery(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        new ApiResponse<>(
                                true,
                                "Contact query submitted successfully",
                                response
                        )
                );
    }


    @GetMapping("/api/admin/contact-queries")
    public ResponseEntity<
            ApiResponse<List<ContactQueryResponseDto>>
            > getAllContactQueries() {

        List<ContactQueryResponseDto> response =
                contactQueryService
                        .getAllContactQueries();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Contact queries fetched successfully",
                        response
                )
        );
    }


    @GetMapping("/api/admin/contact-queries/{id}")
    public ResponseEntity<
            ApiResponse<ContactQueryResponseDto>
            > getContactQueryById(
            @PathVariable Long id) {

        ContactQueryResponseDto response =
                contactQueryService
                        .getContactQueryById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Contact query fetched successfully",
                        response
                )
        );
    }

    @PatchMapping("/api/admin/contact-queries/{id}/status")
public ResponseEntity<
        ApiResponse<ContactQueryResponseDto>
        > updateContactQueryStatus(
        @PathVariable Long id,
        @Valid
        @RequestBody
        UpdateContactQueryStatusRequestDto requestDto) {

    ContactQueryResponseDto response =
            contactQueryService
                    .updateContactQueryStatus(
                            id,
                            requestDto
                    );

    return ResponseEntity.ok(
            new ApiResponse<>(
                    true,
                    "Contact query status updated successfully",
                    response
            )
    );
}
}