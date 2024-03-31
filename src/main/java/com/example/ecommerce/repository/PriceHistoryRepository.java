package com.example.ecommerce.repository;

import com.example.ecommerce.model.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {
}
