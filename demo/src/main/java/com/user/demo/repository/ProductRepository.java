package com.user.demo.repository;

import com.user.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Thêm các phương thức tìm kiếm tùy chỉnh ở đây nếu cần
}
