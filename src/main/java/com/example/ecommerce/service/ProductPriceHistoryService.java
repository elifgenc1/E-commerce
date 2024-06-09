package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductPriceHistoryDTO;
import com.example.ecommerce.dto.converter.ProductPriceHistoryConverter;
import com.example.ecommerce.dto.request.CreateProductHistoryRequest;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductPriceHistory;
import com.example.ecommerce.repository.PriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductPriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;
    private final ProductPriceHistoryConverter productPriceHistoryConverter;
    public ProductPriceHistoryService(PriceHistoryRepository productPriceHistoryRepository, ProductPriceHistoryConverter productPriceHistoryConverter) {
        this.priceHistoryRepository = productPriceHistoryRepository;
        this.productPriceHistoryConverter = productPriceHistoryConverter;
    }

    @Transactional
    public ProductPriceHistoryDTO createProductPriceHistory(CreateProductHistoryRequest createProductHistoryRequest) {
        ProductPriceHistory priceHistory = productPriceHistoryConverter.convertToEntity(createProductHistoryRequest);
        ProductPriceHistory savedPriceHistory = priceHistoryRepository.save(priceHistory);
        return productPriceHistoryConverter.convertToDTO(savedPriceHistory);
    }
}
