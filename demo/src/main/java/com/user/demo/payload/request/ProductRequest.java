package com.user.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private Long amount;  // Số tiền tính bằng cents
    private String currency;  // Loại tiền tệ (ví dụ: "USD")
    private String name;  // Tên sản phẩm

    
}
