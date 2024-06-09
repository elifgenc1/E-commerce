package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.converter.ProductDTOConverter;
import com.example.ecommerce.dto.request.CreateProductRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
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
    private final ProductDTOConverter productDTOConverter;

    public ProductService(ProductRepository productRepository, PriceHistoryRepository priceHistoryRepository, ProductDTOConverter productDTOConverter) {
        this.productRepository = productRepository;
        this.priceHistoryRepository = priceHistoryRepository;
        this.productDTOConverter = productDTOConverter;
    }

    public ProductDTO createProduct(CreateProductRequest createProductRequest) {
        Product product = productDTOConverter.convertToEntity(createProductRequest);
        Product savedProduct = productRepository.save(product);
        return productDTOConverter.convertToDTO(savedProduct);
    }

    public ProductDTO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productDTOConverter.convertToDTO(product);
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productDTOConverter.convertToDTOList(products);
    }

    public ProductDTO updateProduct(Long id, UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        double oldPrice = product.getPrice();
        productDTOConverter.updateEntity(product, updateProductRequest);

        double newPrice = product.getPrice();
        ProductPriceHistory priceHistory = new ProductPriceHistory();
        priceHistory.setProduct(product);
        priceHistory.setOldPrice(oldPrice);
        priceHistory.setNewPrice(newPrice);
        priceHistoryRepository.save(priceHistory);

        Product updatedProduct = productRepository.save(product);
        return productDTOConverter.convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

}
