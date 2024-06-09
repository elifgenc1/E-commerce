package com.example.ecommerce.dto;

import com.example.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceHistoryDTO {
    private Long id;
    Product product;
    private double oldPrice;
    private double newPrice;
    private LocalDateTime updateTime;
    private LocalDateTime deletionTime;
}
