package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductPriceHistory;
import com.example.ecommerce.repository.PriceHistoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PriceHistoryRepository priceHistoryRepository;

    public ProductService(ProductRepository productRepository, PriceHistoryRepository priceHistoryRepository) {
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
    }

    // Yeni bir ürün oluşturma
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // ID'ye göre ürün getirme
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    // Bütün ürünleri getirme
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Ürünü güncelleme
    public Product updateProduct(Long id, Product productDetails) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        double oldPrice = product.getPrice();
        product.setPrice(productDetails.getPrice());
        product.setStockQuantity(productDetails.getStockQuantity());

        //Ürün fiyat geçmişini kaydetme
        double newPrice = productDetails.getPrice();
        ProductPriceHistory priceHistory = new ProductPriceHistory();
        priceHistory.setProduct(product);
        priceHistory.setOldPrice(oldPrice);
        priceHistory.setNewPrice(newPrice);
        priceHistoryRepository.save(priceHistory);

        return productRepository.save(product);
    }

    // Ürünü silme
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

}
