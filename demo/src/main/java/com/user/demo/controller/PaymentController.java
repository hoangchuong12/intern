package com.user.demo.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Refund;
import com.user.demo.payload.request.PaymentRequest;
import com.user.demo.payload.response.PaymentResponse;
import com.user.demo.payload.response.RefundResponse;
import com.user.demo.service.StripeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;// Thay bằng package thực tế của bạn


import java.util.List;
import java.util.Map;

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

    @GetMapping("/charges/{id}")
    public Map<String, Object> getChargeById(@PathVariable String id) throws StripeException {
        // Gọi phương thức từ StripeService
        return stripeService.getChargeByIdAsMap(id);
    }
    @GetMapping("/charges")
    public ResponseEntity<?> getAllCharges(@RequestParam(required = false) Integer limit) {
        try {
            List<Charge> charges = stripeService.getAllCharges(limit);
            return ResponseEntity.ok(charges);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PaymentResponse("error", e.getMessage(), null));
        }
    }

    @PostMapping("/charges/{id}/capture")
    public ResponseEntity<?> captureCharge(@PathVariable String id) {
        try {
            Charge charge = stripeService.captureCharge(id);
            return ResponseEntity.ok(charge);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PaymentResponse("error", e.getMessage(), null));
        }
    }

    @GetMapping("/charges/search")
    public ResponseEntity<?> searchCharges(@RequestParam String query) {
        try {
            List<Charge> charges = stripeService.searchCharges(query);
            return ResponseEntity.ok(charges);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PaymentResponse("error", e.getMessage(), null));
        }
    }

    @PostMapping("/refunds")
    public ResponseEntity<?> createRefund(@RequestParam String chargeId, @RequestParam(required = false) Long amount) {
        try {
            // Tạo refund
            Refund refund = stripeService.createRefund(chargeId, amount);
            return ResponseEntity.ok(new RefundResponse(
                refund.getId(),
                refund.getAmount(),
                refund.getStatus(),
                refund.getCharge()
            ));
        } catch (StripeException e) {
            // Xử lý lỗi nếu refund không thành công
            return ResponseEntity.badRequest().body(new PaymentResponse("error", e.getMessage(), null));
        }
    }
}
