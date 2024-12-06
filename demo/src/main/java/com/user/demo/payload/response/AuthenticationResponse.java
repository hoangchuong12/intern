package com.user.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
    private UUID userId;
    private String token;
}
