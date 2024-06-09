package com.example.ecommerce.dto.converter;

import com.example.ecommerce.dto.CartDTO;
import com.example.ecommerce.dto.CustomerDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Product;
import org.springframework.stereotype.Component;

@Component
public class CartDTOConverter {

    private CustomerDTOConverter customerDTOConverter;
    public CartDTO convertToDTO(Cart cart) {
        return CartDTO.builder()
                .id(cart.getId())
                .customer(cart.getCustomer())
                .totalPrice(cart.getTotalPrice())
                .updateTime(cart.getUpdateTime())
                .deletionTime(cart.getDeletionTime())
                .build();
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .cart(customer.getCart())
                .updateTime(customer.getUpdateTime())
                .deletionTime(customer.getDeletionTime())
                .build();
    }


    private ProductDTO convertToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .updateTime(product.getUpdateTime())
                .deletionTime(product.getDeletionTime())
                .build();
    }
}