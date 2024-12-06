package com.user.demo.service;

import com.user.demo.payload.request.CartItemRequest;
import com.user.demo.payload.response.CartItemResponse;

import java.util.List;
import java.util.UUID;

public interface CartItemService {
    CartItemResponse addCartItem(CartItemRequest cartItemRequest);
    CartItemResponse getCartItemById(UUID id);
    List<CartItemResponse> getAllCartItems(UUID userId);
    CartItemResponse updateCartItem(UUID id, CartItemRequest cartItemRequest);
    void deleteCartItem(UUID id);
}
