package com.ngo.contact.service;

import com.ngo.contact.dto.ContactQueryResponseDto;
import com.ngo.contact.dto.CreateContactQueryRequestDto;
import com.ngo.contact.dto.UpdateContactQueryStatusRequestDto;
import com.ngo.contact.entity.ContactQuery;
import com.ngo.contact.enums.ContactQueryStatus;
import com.ngo.contact.repository.ContactQueryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactQueryService {

    private final ContactQueryRepository contactQueryRepository;


    @Transactional
    public ContactQueryResponseDto createContactQuery(
            CreateContactQueryRequestDto requestDto) {

        ContactQuery contactQuery = ContactQuery.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .mobile(requestDto.getMobile())
                .subject(requestDto.getSubject())
                .message(requestDto.getMessage())
                .status(ContactQueryStatus.NEW)
                .build();

        ContactQuery savedContactQuery =
                contactQueryRepository.save(contactQuery);

        return mapToResponseDto(savedContactQuery);
    }

    @Transactional
public ContactQueryResponseDto updateContactQueryStatus(
        Long id,
        UpdateContactQueryStatusRequestDto requestDto) {

    ContactQuery contactQuery =
            contactQueryRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Contact query not found"
                            )
                    );

    contactQuery.setStatus(
            requestDto.getStatus()
    );

    ContactQuery updatedContactQuery =
            contactQueryRepository.save(
                    contactQuery
            );

    return mapToResponseDto(
            updatedContactQuery
    );
}


    @Transactional(readOnly = true)
    public List<ContactQueryResponseDto> getAllContactQueries() {

        return contactQueryRepository
                .findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }


    @Transactional(readOnly = true)
    public ContactQueryResponseDto getContactQueryById(
            Long id) {

        ContactQuery contactQuery =
                contactQueryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Contact query not found"
                                )
                        );

        return mapToResponseDto(contactQuery);
    }


    private ContactQueryResponseDto mapToResponseDto(
            ContactQuery contactQuery) {

        return ContactQueryResponseDto.builder()
                .id(contactQuery.getId())
                .name(contactQuery.getName())
                .email(contactQuery.getEmail())
                .mobile(contactQuery.getMobile())
                .subject(contactQuery.getSubject())
                .message(contactQuery.getMessage())
                .status(contactQuery.getStatus())
                .createdAt(contactQuery.getCreatedAt())
                .updatedAt(contactQuery.getUpdatedAt())
                .build();
    }
}