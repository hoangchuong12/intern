package com.user.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long amount; // Số tiền (cents)
    private String currency; // Đơn vị tiền tệ, ví dụ: "usd"
    private String token; // Token từ frontend
    private String description; // Mô tả thanh toán
}