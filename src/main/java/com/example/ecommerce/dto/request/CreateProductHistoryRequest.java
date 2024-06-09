package com.example.ecommerce.dto.request;

import com.example.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateProductHistoryRequest {
    Product product;
    private double oldPrice;
    private double newPrice;
}
