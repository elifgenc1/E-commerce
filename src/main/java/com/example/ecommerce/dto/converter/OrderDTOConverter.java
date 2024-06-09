package com.example.ecommerce.dto.converter;

import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDTOConverter {


    public static OrderDTO toOrderDTO(Order order) {
        List<Long> cartItemIds = order.getOrderItems()
                .stream()
                .map(OrderItem::getCartItemId)
                .collect(Collectors.toList());
        if (order == null) {
            return null;
        }

        return OrderDTO.builder()
                .id(order.getId())
                .customer(order.getCustomer())
                .orderCode(order.getOrderCode())
                .cartItemIds(cartItemIds)
                .updateTime(order.getUpdateTime())
                .deletionTime(order.getDeletionTime())
                .build();
    }

    public static Order toOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }

        Order order = new Order();
        order.setId(orderDTO.getId());
        order.setCustomer(orderDTO.getCustomer());
        order.setOrderCode(orderDTO.getOrderCode());
        order.setUpdateTime(orderDTO.getUpdateTime());
        order.setDeletionTime(orderDTO.getDeletionTime());

        return order;
    }
}
