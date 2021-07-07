package com.tekmentor.paymentservice.service;

import com.tekmentor.paymentservice.domain.Payment;
import com.tekmentor.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository repository;

    public Payment payForOrder(Payment payment){
        String paymentStatus = paymentProcessing();
        payment.setPaymentStatus(paymentStatus);
        String transactionId = UUID.randomUUID().toString();
        payment.setTransactionId(transactionId);
        return repository.save(payment);
    }
    public Payment findPaymentByOrderId(String orderId) {
        return repository.findByOrderId(orderId);
    }

    //In real world this is going to be status of call made to third party payment gateway
    private String paymentProcessing() {
        return new Random().nextBoolean()? "success": "failure";
    }


}
