package com.tekmentor.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private String name;
    private  double price;

    @OneToOne
    private Category category;

    public Product(Long id, String description, String name, double price, Category category) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.price = price;
        this.category = category;
    }
}