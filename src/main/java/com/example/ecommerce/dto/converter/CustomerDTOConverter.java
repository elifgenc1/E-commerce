package com.example.ecommerce.dto.converter;

import com.example.ecommerce.dto.CustomerDTO;
import com.example.ecommerce.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerDTOConverter {
    public CustomerDTO convertToDTO(Customer customer) {
        return CustomerDTO.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .cart(customer.getCart())
                .updateTime(customer.getUpdateTime())
                .deletionTime(customer.getDeletionTime())
                .build();
    }
}
