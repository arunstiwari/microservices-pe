package com.tekmentor.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Cart {
    private long id;
    private long customerId;
}