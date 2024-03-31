package com.example.ecommerce.service;

import com.example.ecommerce.enums.OrderCode;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductPriceHistory;
import com.example.ecommerce.repository.OrderRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final CartService cartService;


    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getAllOrdersForCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order placeOrder(Long customerId, Long cartId) {
        Cart cart = cartService.getCart(cartId);
        if (cart == null || cart.getCustomer().getId() != customerId) {
            throw new IllegalArgumentException("Invalid cart for customer");
        }

        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderCode(OrderCode.CREATED);

        return orderRepository.save(order);
    }
    public Order getOrderForCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with code: " + orderCode));
    }


}
