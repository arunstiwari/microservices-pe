package com.tekmentor.orderservice.controller;

import com.tekmentor.orderservice.command.TransactionRequest;
import com.tekmentor.orderservice.command.TransactionResponse;
import com.tekmentor.orderservice.domain.Order;
import com.tekmentor.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public TransactionResponse save(@RequestBody TransactionRequest request){
        return orderService.save(request);
    }
}
