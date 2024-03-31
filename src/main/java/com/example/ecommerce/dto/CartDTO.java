package com.example.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {

    private Long customerId;
    private Long productId;
    private int quantity;

}
