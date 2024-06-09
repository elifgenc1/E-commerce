package com.example.ecommerce.dto;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
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
public class CustomerDTO {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Cart cart;
    private LocalDateTime updateTime;
    private LocalDateTime deletionTime;

}
