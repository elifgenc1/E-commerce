package com.example.ecommerce.service;

import com.example.ecommerce.dto.CartDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.converter.CartDTOConverter;
import com.example.ecommerce.dto.converter.ProductDTOConverter;
import com.example.ecommerce.dto.request.AddProductToCartRequest;
import com.example.ecommerce.dto.request.RemoveProductFromCartRequest;
import com.example.ecommerce.dto.request.UpdateProductRequest;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CustomerRepository;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    private final CartDTOConverter cartConverter;
    private final ProductDTOConverter productDTOConverter;

    public CartService(CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository, ProductService productService, CartItemRepository cartItemRepository, CartDTOConverter cartConverter, ProductDTOConverter productDTOConverter) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.cartItemRepository = cartItemRepository;
        this.cartConverter = cartConverter;
        this.productDTOConverter = productDTOConverter;
    }

    @Transactional
    public void emptyCart(Long customerId) {

        Customer customer = findCustomerById(customerId) ;

        Cart cart = getCustomerCart(customer) ;

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            updateProductStock(productService.getProduct(product.getId()), cartItem.getQuantity(), true);
            cartItemRepository.softDelete(cartItem.getId());
        }

        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    @Transactional
    public CartDTO addProductToCart(AddProductToCartRequest request) {

        Customer customer = findCustomerById(request.getCustomerId());

        Cart cart = getOrCreateCart(customer);

        ProductDTO product = productService.getProduct(request.getProductId());

        validateProductStock(product, request.getQuantity());

        updateProductStock(product, request.getQuantity(),false);

        updateCartTotalPrice(cart, product, request.getQuantity(), false);

        CartItem cartItem = createCartItem(cart, product, request.getQuantity());

        cartItemRepository.save(cartItem);

        return cartConverter.convertToDTO(cart);
    }

    @Transactional
    public CartDTO removeProductFromCart(RemoveProductFromCartRequest request) {

        Customer customer = findCustomerById(request.getCustomerId());

        Cart cart = getCustomerCart(customer) ;

        ProductDTO product = productService.getProduct(request.getProductId());

        List<CartItem> cartItems = getCartItems(cart, product, request.getQuantity());

        validateCartItemsQuantity(cartItems, request.getQuantity());

        removeCartItems(cartItems, request.getQuantity());

        updateProductStock(product, request.getQuantity(), true);

        updateCartTotalPrice(cart, product, request.getQuantity(),true);

        return cartConverter.convertToDTO(cart);
    }


    //Müşteri ID'sine göre sepeti getirme
    public Cart getCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with id: " + customerId);
        }
        return cart;
    }


    private Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
    }

    private Cart getOrCreateCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
        }
        return cart;
    }

    private void validateProductStock(ProductDTO product, int quantity) {
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("There are not enough items in stock.");
        }
    }

    private void validateCartItemsQuantity(List<CartItem> cartItems, int quantity) {
        if (cartItems.getFirst().getQuantity() < quantity) {
            throw new IllegalArgumentException("There are not enough items in cart.");
        }
    }

    private void updateProductStock(ProductDTO product, double quantity, boolean isAddition) {
        double updatedStockQuantity = isAddition ?
                product.getStockQuantity() + quantity :
                product.getStockQuantity() - quantity;

        UpdateProductRequest updateRequest = UpdateProductRequest.builder()
                .stockQuantity(updatedStockQuantity)
                .name(product.getName())
                .price(product.getPrice())
                .build();
        productService.updateProduct(product.getId(), updateRequest);
    }


    private void updateCartTotalPrice(Cart cart, ProductDTO product, int quantity, boolean isAddition) {
        double totalPrice = isAddition ?
                cart.getTotalPrice() - product.getPrice() * quantity:
                cart.getTotalPrice() + product.getPrice() * quantity;
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }

    private CartItem createCartItem(Cart cart, ProductDTO product, int quantity) {
        Product p1 = productRepository.getById(product.getId());

        // Cart'taki mevcut cart item'ları kontrol et
        for (CartItem existingCartItem : cart.getCartItems()) {
            if (existingCartItem.getProduct().getId().equals(p1.getId())) {
                // Ürün zaten sepette mevcut, miktarı güncelle
                existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
                return existingCartItem;
            }
        }

        // Ürün sepette mevcut değil, yeni bir cart item oluştur
        CartItem cartItem = new CartItem();
        cartItem.setProduct(p1);
        cartItem.setCart(cart);
        cartItem.setQuantity(quantity);
        return cartItem;
    }



    private void removeCartItems(List<CartItem> cartItems, int quantity) {
        int itemsToRemove = quantity;
        for (CartItem cartItem : cartItems) {
            if (itemsToRemove <= 0) {
                break;
            }
            double itemQuantity = cartItem.getQuantity();
            if (itemQuantity <= itemsToRemove) {
                cartItem.setDeleted(true);
                itemsToRemove -= itemQuantity;
            } else {
                cartItem.setQuantity(itemQuantity - itemsToRemove);
                itemsToRemove = 0;
            }
            cartItemRepository.save(cartItem);
        }
    }

    private List<CartItem> getCartItems(Cart cart, ProductDTO product, int quantity) {
        PageRequest pageable = PageRequest.of(0, quantity);
        Page<CartItem> cartItems = cartItemRepository.findByCartAndProduct_Id(cart, product.getId(), pageable);

        if (cartItems.isEmpty()) {
            throw new ResourceNotFoundException("Product not found in cart for customer with id: " + cart.getCustomer().getId());
        }

        return cartItems.getContent();
    }

    private Cart getCustomerCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with id: " + customer.getId());
        }
        return cart;
    }

}
