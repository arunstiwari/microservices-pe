package com.tekmentor.orderservice.service;

import com.tekmentor.orderservice.controller.OrderConfig;
import com.tekmentor.orderservice.exception.OrderNotFoundException;
import com.tekmentor.orderservice.model.Cart;
import com.tekmentor.orderservice.model.CartEntity;
import com.tekmentor.orderservice.model.Order;
import com.tekmentor.orderservice.model.OrderItem;
import com.tekmentor.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Supplier;

@Service
public class OrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConfig config;

    public List<Order> fetchAllOrders() {
        return orderRepository.findAll();
    }

    public Order fetchOrdersById(long id) {
        Supplier<OrderNotFoundException> exceptionSupplier = () -> new OrderNotFoundException("Order with id "+id+ " is not found");
        return orderRepository.findById(id).orElseThrow(exceptionSupplier);
    }

    public Order createOrder(Cart cart) {
        CartEntity cartEntity = restTemplate.getForObject(config.getCartservice() + "/carts/" + cart.getId(), CartEntity.class);
        Order order = new Order();
        cartEntity.getCartItems().stream().forEach(cartItem -> {
            OrderItem item = new OrderItem();
            item.setPrice(cartItem.getPrice());
            item.setQuantity(cartItem.getQuantity());
            item.setQuantity(cartItem.getQuantity());
            item.setProductId(cartItem.getProductId());
            item.setOrder(order);
            order.addOrderItem(item);
        });
        order.setStatus("ORDER_INITIATED");
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " does not exist"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
