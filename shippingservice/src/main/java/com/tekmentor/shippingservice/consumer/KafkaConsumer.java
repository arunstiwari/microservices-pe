package com.tekmentor.shippingservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "shipping-topic")
    public void consumeAnyShippingEvents(String message) throws InterruptedException {
        log.info("New message in shipping topic : {}",message);

        Thread.sleep(1000);
        log.info("Prepairing for dispatch");


        Thread.sleep(1000);
        log.info("Shipped the good");


        Thread.sleep(1000);
        log.info("Goods have been delivered");
    }

    @KafkaListener(topics = "orders-topic")
    public void consumeOrderConfirmationEvents(String message) throws InterruptedException {
        log.info("New message in shipping topic : {}",message);

        Thread.sleep(1000);
        log.info("Prepairing for dispatch");


        Thread.sleep(1000);
        log.info("Shipped the good");


        Thread.sleep(1000);
        log.info("Goods have been delivered");
    }

}
