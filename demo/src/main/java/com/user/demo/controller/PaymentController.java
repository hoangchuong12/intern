package com.user.demo.controller;

import com.stripe.exception.StripeException;
import com.user.demo.payload.request.PaymentRequest;

import com.user.demo.payload.response.PaymentResponse;
import com.user.demo.service.StripeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000") 
public class PaymentController {

    private final StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/charge")
    public ResponseEntity<?> createCharge(@RequestBody PaymentRequest paymentRequest) {
        try {
            String chargeId = stripeService.createCharge(paymentRequest);
            return ResponseEntity.ok(new PaymentResponse("success", "Charge created successfully", chargeId));
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PaymentResponse("error", e.getMessage(), null));
        }
    }
}

