package com.user.demo.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class CartItemResponse {
    private UUID id;          // ID của mục giỏ hàng
    private UUID userId;      // ID của người dùng
    private UUID productId;   // ID của sản phẩm
    private String productName; // Tên sản phẩm
    private Long productAmount; // Giá sản phẩm
    private String currency;   // Loại tiền tệ (ví dụ: VND, USD)
    private int quantity;     // Số lượng sản phẩm trong giỏ hàng
    private LocalDateTime addedAt;  // Thời gian thêm sản phẩm vào giỏ hàng
    private LocalDateTime expiresAt; // Thời gian hết hạn của mục giỏ hàng

    // Thêm các thông tin khác tùy theo yêu cầu
}
