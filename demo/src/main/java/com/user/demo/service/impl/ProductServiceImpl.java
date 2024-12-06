package com.user.demo.service.impl;

import com.user.demo.entity.Product;
import com.user.demo.exception.CustomException;
import com.user.demo.payload.request.ProductRequest;
import com.user.demo.payload.response.ProductResponse;
import com.user.demo.repository.ProductRepository;
import com.user.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setAmount(productRequest.getAmount());
        // product.setQuantity(productRequest.getQuantity());
        product.setName(productRequest.getName());
        product.setCurrency(productRequest.getCurrency());

        Product savedProduct = productRepository.save(product);
        return mapEntityToResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found", "NOT_FOUND"));
        return mapEntityToResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(UUID id, ProductRequest productRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found", "NOT_FOUND"));
        product.setAmount(productRequest.getAmount());
        // product.setQuantity(productRequest.getQuantity());
        product.setName(productRequest.getName());
        product.setCurrency(productRequest.getCurrency());

        Product updatedProduct = productRepository.save(product);
        return mapEntityToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product not found", "NOT_FOUND"));
        productRepository.delete(product);
    }

    private ProductResponse mapEntityToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setAmount(product.getAmount());
        response.setQuantity(product.getQuantity());
        response.setName(product.getName());
        response.setCurrency(product.getCurrency());
        return response;
    }
}
