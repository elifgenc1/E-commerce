package com.example.ecommerce.dto.request;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddProductToCartRequest {
    private Long customerId;
    private Long productId;
    private int quantity;
}
