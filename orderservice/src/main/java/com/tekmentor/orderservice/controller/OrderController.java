package com.tekmentor.orderservice.controller;

import com.tekmentor.orderservice.model.Cart;
import com.tekmentor.orderservice.model.Order;
import com.tekmentor.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity orders(){
        List<Order> orders = orderService.fetchAllOrders();
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity ordersById(@PathVariable("id") long id){
        Order order = orderService.fetchOrdersById(id);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity updateOrderStatus(@RequestBody String status, @PathVariable("id") long id){
        log.info("Entering updateOrderStatus : {}",status);
        Order order = orderService.updateOrderStatus(id, status);
        return new ResponseEntity(order, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity createOrder(@RequestBody Cart cart){
        log.info("Entering createOrder : {}",cart);
        Order createdOrder = orderService.createOrder(cart);
        return new ResponseEntity(createdOrder, HttpStatus.CREATED);
    }
}
