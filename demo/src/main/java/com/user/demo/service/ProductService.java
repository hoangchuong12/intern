package com.user.demo.service;

import com.user.demo.payload.request.ProductRequest;
import com.user.demo.payload.response.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse getProductById(UUID id);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(UUID id, ProductRequest productRequest);
    void deleteProduct(UUID id);
}
