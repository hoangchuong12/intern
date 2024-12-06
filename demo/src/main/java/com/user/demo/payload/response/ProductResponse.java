package com.user.demo.payload.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductResponse {
    private UUID id;
    private Long amount;
    private Long quantity;
    private String name;
    private String currency;
}
