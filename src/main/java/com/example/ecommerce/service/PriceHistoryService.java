package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductPriceHistory;
import com.example.ecommerce.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PriceHistoryService {
    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistoryService(PriceHistoryRepository productPriceHistoryRepository) {
        this.priceHistoryRepository = productPriceHistoryRepository;
    }

    @Transactional
    public void createProductPriceHistory(Product product, double oldPrice, double newPrice) {
        ProductPriceHistory priceHistory = new ProductPriceHistory();
        priceHistory.setProduct(product);
        priceHistory.setOldPrice(oldPrice);
        priceHistory.setNewPrice(newPrice);
        priceHistoryRepository.save(priceHistory);
    }
}
