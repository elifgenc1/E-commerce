package com.example.ecommerce.dto.request;

import com.example.ecommerce.dto.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceOrderRequest {
    private Long customerId;
    private Long cartId;
    private List<Long> cartItemIds;
}

