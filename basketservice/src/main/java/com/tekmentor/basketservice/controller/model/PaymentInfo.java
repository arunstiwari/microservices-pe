package com.tekmentor.basketservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfo {
    private CreditCardInfo creditCardInfo;
    private double totalValue;
    private long customerId;
    private long orderId;
}
