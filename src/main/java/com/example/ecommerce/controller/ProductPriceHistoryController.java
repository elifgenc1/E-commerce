package com.example.ecommerce.controller;

import com.example.ecommerce.dto.ProductPriceHistoryDTO;
import com.example.ecommerce.dto.request.CreateProductHistoryRequest;
import com.example.ecommerce.service.ProductPriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-price-history")
public class ProductPriceHistoryController {

    private final ProductPriceHistoryService productPriceHistoryService;

    public ProductPriceHistoryController(ProductPriceHistoryService productPriceHistoryService) {
        this.productPriceHistoryService = productPriceHistoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductPriceHistoryDTO> createProductPriceHistory(@RequestBody CreateProductHistoryRequest createProductHistoryRequest) {
        try {
            ProductPriceHistoryDTO createdPriceHistory = productPriceHistoryService.createProductPriceHistory(createProductHistoryRequest);
            return new ResponseEntity<>(createdPriceHistory, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
