package com.tekmentor.orderservice.model;

import lombok.Data;

@Data
public class CartItem {
    private long productId;

    private int quantity;
    private double price;
}
