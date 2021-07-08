package com.tekmentor.orderservice.model;

import lombok.Data;

import java.util.List;

@Data
public class CartEntity {
    private long id;
    private List<CartItem> cartItems ;
    private long customerId;
}