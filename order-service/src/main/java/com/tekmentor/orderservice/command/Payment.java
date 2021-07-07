package com.tekmentor.orderservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    private int id;
    private String transactionId;
    private String paymentStatus;
    private double amount;
    private String orderId;
}