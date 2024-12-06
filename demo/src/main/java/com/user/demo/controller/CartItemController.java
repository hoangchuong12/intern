package com.user.demo.controller;

import com.user.demo.payload.request.CartItemRequest;
import com.user.demo.payload.response.CartItemResponse;
import com.user.demo.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user-services/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    @Autowired
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponse> addCartItem(@RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse response = cartItemService.addCartItem(cartItemRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemResponse> getCartItem(@PathVariable UUID id) {
        CartItemResponse response = cartItemService.getCartItemById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItemResponse>> getAllCartItems(@PathVariable UUID userId) {
        List<CartItemResponse> response = cartItemService.getAllCartItems(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponse> updateCartItem(@PathVariable UUID id, @RequestBody CartItemRequest cartItemRequest) {
        CartItemResponse response = cartItemService.updateCartItem(id, cartItemRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable UUID id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }
}
