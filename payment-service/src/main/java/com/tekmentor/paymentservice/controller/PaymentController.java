package com.tekmentor.paymentservice.controller;

import com.tekmentor.paymentservice.domain.Payment;
import com.tekmentor.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    public Payment doThePayment(@RequestBody Payment payment){
        log.info(" Going to do the payment : {}",payment);
        return paymentService.payForOrder(payment);
    }

    @GetMapping("/payments/{orderId}")
    public Payment findPaymentByOrderId(@PathVariable String orderId){
        log.info("Going to return the payment by order id {}",orderId);
        return paymentService.findPaymentByOrderId(orderId);
    }
}
