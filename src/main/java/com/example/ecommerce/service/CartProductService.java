package com.example.ecommerce.service;

import com.example.ecommerce.model.ProductCart;
import com.example.ecommerce.repository.CartProductRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartProductService {

    private final CartProductRepository cartProductRepository;

    public CartProductService(CartProductRepository cartProductRepository) {
        this.cartProductRepository = cartProductRepository;
    }

    public ProductCart addCartProduct(ProductCart ProductCart) {
        return cartProductRepository.save(ProductCart);
    }

    public void deleteCartProduct(Long ProductCartId) {
        cartProductRepository.deleteById(ProductCartId);
    }

    public ProductCart updateCartProduct(Long ProductCartId, ProductCart ProductCartDetails) {
        ProductCart ProductCart = cartProductRepository.findById(ProductCartId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCart not found with id: " + ProductCartId));

        // Diğer gerekli alanları da güncelleyebilirsiniz

        return cartProductRepository.save(ProductCart);
    }

    public List<ProductCart> getAllCartProducts() {
        return cartProductRepository.findAll();
    }

    public ProductCart getCartProductById(Long ProductCartId) {
        return cartProductRepository.findById(ProductCartId)
                .orElseThrow(() -> new ResourceNotFoundException("ProductCart not found with id: " + ProductCartId));
    }
}