package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductCart;
import com.example.ecommerce.repository.CartProductRepository;
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
    private final ProductService productService;
    private final CartProductRepository cartProductRepository;

    public CartService(CartRepository cartRepository, CustomerRepository customerRepository, ProductRepository productRepository, ProductService productService, CartProductRepository cartProductRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.productService = productService;
        this.cartProductRepository = cartProductRepository;
    }

    //Müşteri ID'sine göre sepeti getirme
    public Cart getCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with id: " + customerId);
        }
        return cart;
    }
    //Müşteri ID'sine göre sepet tutarını güncelleme
    @Transactional
    public Cart updateCart(Long customerId, List<Product> items) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with id: " + customerId);
        }
        double total = 0.0;
        for (Product item : items) {
            total += item.getPrice();
        }
        cart.setTotalPrice(total);

        return cartRepository.save(cart);
    }

    //Müşteriin sepetteki ürünlerini silme
    @Transactional
    public void emptyCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with id: " + customerId);
        }
        List<ProductCart> productCart = cartProductRepository.findByCustomerId(customerId);

        for (ProductCart p : productCart) {
            p.setDeleted(true);
        }
        cart.setTotalPrice(0.0);
        cartRepository.save(cart);
    }

    @Transactional
    public Cart addProductToCart(Long customerId, Long productId, int quantity) {

        //ID'ye göre kullanıcı bulma
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("customer not found with id: " + customerId));

        // Kullanıcıya ait sepeti bul veya yeni bir sepet oluştur
        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
        }

        //Sepete eklenecek ürünü bulma
        Product product = productService.getProduct(productId);
        double totalPrice = cart.getTotalPrice();

        //Ürün stok kontrolü
        if (product.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("There are not enough items in stock.");
        } else {
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productService.updateProduct(productId, product);
        }

        // Sepete ürünü ekle
        cart.setTotalPrice(totalPrice + product.getPrice() * quantity);
        cartRepository.save(cart);

        //Ürün ve Müşteri tablosuna keydetme
        ProductCart productCart = new ProductCart();
        productCart.setCart(cart);
        productCart.setProduct(product);

        cartProductRepository.save(productCart);

        return cart;
    }

    @Transactional
    public Cart removeProductFromCart(Long customerId, Long productId, int quantity) {

        //ID'ye göre kullanıcı bulma
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));

        // Kullanıcıya ait sepeti bulma
        Cart cart = customer.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with id: " + customerId);
        }

        // Ürünü bulma
        Product product = productService.getProduct(productId);

        //Silinecek ürün miktari kadar ürüm getirme
        PageRequest pageable = PageRequest.of(0, quantity);
        Page<ProductCart> productCart = cartProductRepository.findByProductId(productId, pageable);

        if (productCart == null) {
            throw new ResourceNotFoundException("Product not found in cart for customer with id: " + customerId);
        }

        //Ürünleri silme işlemi
        for (ProductCart p : productCart) {
            p.setDeleted(true);
        }

        double currentQuantity = product.getStockQuantity();

        // Eğer çıkarılacak miktar mevcut miktarı aşmıyorsa, miktarı güncelle
        product.setStockQuantity(currentQuantity + quantity);
        productService.updateProduct(productId, product);

        // Sepetin toplam fiyatını güncelleme
        cart.setTotalPrice(cart.getTotalPrice() - (product.getPrice() * quantity));
        cartRepository.save(cart);

        return cart;
    }

}
