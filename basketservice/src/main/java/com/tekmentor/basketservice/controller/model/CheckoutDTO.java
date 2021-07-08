package com.tekmentor.basketservice.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutDTO {
    private long cartId;
    private CreditCardInfo creditCardInfo;
}
