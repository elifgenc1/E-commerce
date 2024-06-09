package com.example.ecommerce.dto;

import com.example.ecommerce.model.ProductPriceHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private double stockQuantity;
    private LocalDateTime updateTime;
    private LocalDateTime deletionTime;
}
