package com.tekmentor.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekmentor.orderservice.model.Order;
import com.tekmentor.orderservice.model.OrderItem;
import com.tekmentor.orderservice.model.PaymentInfo;
import com.tekmentor.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OrderListenerTest {

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @InjectMocks
//    private OrderListener orderListener;

    @Test
    public void testOrderConsume() throws JsonProcessingException {
        List<OrderItem> items = List.of(
                new OrderItem(23l,34,12,500, null )
        );
        Order order = new Order(5l, items, "CONFIRMED", "55");
        when(orderRepository.findById(5l)).thenReturn(Optional.of(order));
        PaymentInfo paymentInfo = new PaymentInfo(5l, "SUCCESS");
        ObjectMapper objectMapper = new ObjectMapper();
        String paymentData = objectMapper.writeValueAsString(paymentInfo);


        JsonNode jsonNode = objectMapper.readTree(paymentData);
        String id = jsonNode.get("orderId").asText();
        System.out.println("id = " + id);
        kafkaTemplate.send("payments-topic", paymentData);
    }

}