package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CartDTO;
import com.example.ecommerce.dto.request.AddProductToCartRequest;
import com.example.ecommerce.dto.request.RemoveProductFromCartRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.apache.velocity.exception.ResourceNotFoundException;
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
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody AddProductToCartRequest request) {
        try {
            CartDTO updatedCart = cartService.addProductToCart(request);
            return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/empty/{customerId}")
    public ResponseEntity<Void> emptyCart(@PathVariable Long customerId) {
        cartService.emptyCart(customerId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    @DeleteMapping("/removeProductFromCart")
    public ResponseEntity<CartDTO> removeProductFromCart(@Valid @RequestBody RemoveProductFromCartRequest request) {
        try {
            CartDTO updatedCart = cartService.removeProductFromCart(request);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
