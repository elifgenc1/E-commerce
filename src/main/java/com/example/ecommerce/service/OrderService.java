package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.converter.OrderDTOConverter;
import com.example.ecommerce.dto.request.PlaceOrderRequest;
import com.example.ecommerce.enums.OrderCode;
import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartItem;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final CartService cartService;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, CartService cartService, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getAllOrdersForCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public OrderDTO placeOrder(PlaceOrderRequest request) {
        Cart cart = cartService.getCart(request.getCustomerId());
        if (cart == null || !cart.getCustomer().getId().equals(request.getCustomerId())) {
            throw new IllegalArgumentException("Invalid cart for customer");
        }

        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderCode(OrderCode.CREATED);

        List<OrderItem> orderItems = new ArrayList<>();
        for (Long cartItemId : request.getCartItemIds()) {


            OrderItem orderItem = new OrderItem();
            orderItem.setCartItemId(cartItemId);
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        orderRepository.save(order);

        return OrderDTOConverter.toOrderDTO(order);
    }



    public Order getOrderForCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with code: " + orderCode));
    }


}
