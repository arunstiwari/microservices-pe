package com.tekmentor.orderservice.service;

import com.tekmentor.orderservice.command.Payment;
import com.tekmentor.orderservice.command.TransactionRequest;
import com.tekmentor.orderservice.command.TransactionResponse;
import com.tekmentor.orderservice.config.OrderConfig;
import com.tekmentor.orderservice.domain.Order;
import com.tekmentor.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderConfig config;
    @Autowired
    private RestTemplate template;

    public TransactionResponse save(TransactionRequest request){
        String response="";
        String orderId = UUID.randomUUID().toString();
        Order order = request.getOrder();
        order.setId(orderId);
        Payment payment = request.getPayment();
        payment.setOrderId(orderId);
        payment.setAmount(order.getPrice());
        log.info("config: {}",config);
        Payment paymentResponse = template.postForObject(config.getPaymentService()+"/payments",payment, Payment.class);
        response = paymentResponse.getPaymentStatus().equals("success") ?
                "payment processing successfull and order placed":
                "payment processing failed and order added to cart";


        Order createdOrder = repository.save(order);
        return new TransactionResponse(createdOrder,paymentResponse.getTransactionId(), paymentResponse.getAmount(), response);
    }
}
