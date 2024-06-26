package com.example.ecommerce.dto;

import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Customer;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@Builder
public class CartDTO {
    private Long id;
    private Customer customer;
    private double totalPrice;
    private LocalDateTime updateTime;
    private LocalDateTime deletionTime;

}
