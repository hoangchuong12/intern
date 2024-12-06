package com.user.demo.service.impl;

import com.user.demo.entity.CartItem;
import com.user.demo.entity.Product;
import com.user.demo.entity.User;
import com.user.demo.exception.CustomException;
import com.user.demo.payload.request.CartItemRequest;
import com.user.demo.payload.response.CartItemResponse;
import com.user.demo.repository.CartItemRepository;
import com.user.demo.repository.ProductRepository;
import com.user.demo.repository.UserRepository;
import com.user.demo.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartItemServiceImpl(CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartItemResponse addCartItem(CartItemRequest cartItemRequest) {
        User user = userRepository.findById(cartItemRequest.getUserId())
                .orElseThrow(() -> new CustomException("User not found", "NOT_FOUND"));
        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElseThrow(() -> new CustomException("Product not found", "NOT_FOUND"));

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setAddedAt(LocalDateTime.now());
        cartItem.setExpiresAt(LocalDateTime.now().plusDays(7)); // Giới hạn giỏ hàng trong 7 ngày

        CartItem savedCartItem = cartItemRepository.save(cartItem);

        return mapEntityToResponse(savedCartItem);
    }

    @Override
    public CartItemResponse getCartItemById(UUID id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart item not found", "NOT_FOUND"));
        return mapEntityToResponse(cartItem);
    }

    @Override
    public List<CartItemResponse> getAllCartItems(UUID userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserId(userId);
        return cartItems.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CartItemResponse updateCartItem(UUID id, CartItemRequest cartItemRequest) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart item not found", "NOT_FOUND"));
        cartItem.setQuantity(cartItemRequest.getQuantity());
        CartItem updatedCartItem = cartItemRepository.save(cartItem);
        return mapEntityToResponse(updatedCartItem);
    }

    @Override
    public void deleteCartItem(UUID id) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CustomException("Cart item not found", "NOT_FOUND"));
        cartItemRepository.delete(cartItem);
    }

    private CartItemResponse mapEntityToResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setUserId(cartItem.getUser().getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setProductAmount(cartItem.getProduct().getAmount());
        response.setCurrency(cartItem.getProduct().getCurrency());
        response.setQuantity(cartItem.getQuantity());
        response.setAddedAt(cartItem.getAddedAt());
        response.setExpiresAt(cartItem.getExpiresAt());
        return response;
    }
}
