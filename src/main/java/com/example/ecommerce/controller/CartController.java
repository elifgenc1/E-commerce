package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CartDTO;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCart(@PathVariable Long id) {
        Cart cart = cartService.getCart(id);
        if (cart != null) {
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addProductToCart")
    public ResponseEntity<Cart> addProductFromCart(@RequestBody CartDTO cartDTO) {
        Cart createdCart = cartService.addProductToCart(cartDTO.getCustomerId(), cartDTO.getProductId(), cartDTO.getQuantity());
        return new ResponseEntity<>(createdCart, HttpStatus.CREATED);
    }

    @PostMapping("/removeProductFromCart")
    public ResponseEntity<Cart> removeProductFromCart(@RequestBody CartDTO cartDTO) {
        Cart deletedProduct = cartService.removeProductFromCart(cartDTO.getCustomerId(), cartDTO.getProductId(), cartDTO.getQuantity());
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }
}
