package com.example.ecommerce.repository;

import com.example.ecommerce.model.ProductCart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartProductRepository extends JpaRepository<ProductCart, Long> {

    Page<ProductCart> findByProductId(Long productId, Pageable pageable);

    List<ProductCart> findByCustomerId(Long customerId);

}
