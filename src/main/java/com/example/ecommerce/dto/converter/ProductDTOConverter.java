package com.example.ecommerce.dto.converter;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.request.CreateProductRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOConverter {

    public ProductDTO convertToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .stockQuantity(product.getStockQuantity())
                .price(product.getPrice())
                .updateTime(product.getUpdateTime())
                .deletionTime(product.getDeletionTime())
                .build();
    }

    public Product convertToEntity(CreateProductRequest createProductRequest) {
        return Product.builder()
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .stockQuantity(createProductRequest.getStockQuantity())
                .build();
    }

    public void updateEntity(Product product, UpdateProductRequest updateProductRequest) {
        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setStockQuantity(updateProductRequest.getStockQuantity());
    }

    public List<ProductDTO> convertToDTOList(List<Product> products) {
        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
