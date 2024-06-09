package com.example.ecommerce.dto;

import com.example.ecommerce.enums.OrderCode;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Customer;
import jakarta.persistence.*;
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
public class OrderDTO {

    private Long id;
    private Customer customer;
    private OrderCode orderCode;
    private List<Long> cartItemIds;
    private LocalDateTime updateTime;
    private LocalDateTime deletionTime;
}
