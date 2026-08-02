package com.ngo.payment.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import lombok.RequiredArgsConstructor;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class RazorpayService {

    private final RazorpayClient razorpayClient;


    @Value("${razorpay.key-secret}")
    private String keySecret;


    // =====================================================
    // CREATE RAZORPAY ORDER
    // =====================================================

    public Order createOrder(
        BigDecimal amount,
        String currency,
        String receipt
    ) throws Exception {

        BigDecimal amountInPaise =
            amount.multiply(
                BigDecimal.valueOf(100)
            );


        JSONObject orderRequest =
            new JSONObject();

        orderRequest.put(
            "amount",
            amountInPaise.longValueExact()
        );

        orderRequest.put(
            "currency",
            currency
        );

        orderRequest.put(
            "receipt",
            receipt
        );


        return razorpayClient
            .orders
            .create(orderRequest);
    }


    // =====================================================
    // VERIFY PAYMENT SIGNATURE
    // =====================================================

    public boolean verifyPaymentSignature(String razorpayOrderId,String razorpayPaymentId, String razorpaySignature ) {

        try {

            JSONObject attributes =  new JSONObject();

            attributes.put( "razorpay_order_id", razorpayOrderId );

            attributes.put( "razorpay_payment_id", razorpayPaymentId  );

            attributes.put("razorpay_signature", razorpaySignature );


            return Utils.verifyPaymentSignature( attributes, keySecret );

        } catch (Exception ex) {

            return false;
        }
    }
}