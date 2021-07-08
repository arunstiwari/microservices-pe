package com.tekmentor.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
@Entity
public class OrderItem {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long productId;

    private int quantity;
    private double price;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id" ,nullable = false)
    private Order order;

}