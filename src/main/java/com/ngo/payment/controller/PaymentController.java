package com.ngo.payment.controller;

import com.ngo.common.dto.ApiResponse;
import com.ngo.payment.dto.PaymentConfigResponseDto;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("${razorpay.key-id}")
    private String razorpayKeyId;


    @GetMapping("/config")
    public ResponseEntity<ApiResponse<PaymentConfigResponseDto>>
        getPaymentConfiguration() {

        PaymentConfigResponseDto response =
                new PaymentConfigResponseDto(
                        razorpayKeyId
                );

        return ResponseEntity.ok(

                new ApiResponse<>(

                        true,

                        "Payment configuration loaded successfully",

                        response
                )
        );
    }

}