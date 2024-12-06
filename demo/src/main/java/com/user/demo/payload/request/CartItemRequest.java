package com.user.demo.payload.request;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class CartItemRequest {
    private UUID userId;      // ID của người dùng
    private UUID productId;   // ID của sản phẩm
    private int quantity;     // Số lượng sản phẩm
    private LocalDateTime expiresAt; // Thời điểm hết hạn của mục giỏ hàng (nếu cần)
}
