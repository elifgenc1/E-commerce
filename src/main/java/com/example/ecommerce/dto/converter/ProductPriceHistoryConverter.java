package com.example.ecommerce.dto.converter;

import com.example.ecommerce.dto.ProductPriceHistoryDTO;
import com.example.ecommerce.dto.request.CreateProductHistoryRequest;
import com.example.ecommerce.model.ProductPriceHistory;
import org.springframework.stereotype.Component;

@Component
public class ProductPriceHistoryConverter {

    public ProductPriceHistoryDTO convertToDTO(ProductPriceHistory productPriceHistory) {
        return ProductPriceHistoryDTO.builder()
                .id(productPriceHistory.getId())
                .product(productPriceHistory.getProduct())
                .oldPrice(productPriceHistory.getOldPrice())
                .newPrice(productPriceHistory.getNewPrice())
                .updateTime(productPriceHistory.getUpdateTime())
                .deletionTime(productPriceHistory.getDeletionTime())
                .build();
    }

    public ProductPriceHistory convertToEntity(CreateProductHistoryRequest createProductHistoryRequest) {
        return ProductPriceHistory.builder()
                .product(createProductHistoryRequest.getProduct())
                .oldPrice(createProductHistoryRequest.getOldPrice())
                .newPrice(createProductHistoryRequest.getNewPrice())
                .build();
    }
}
