package com.tekmentor.productservice.controller;

import com.tekmentor.productservice.config.ProductConfig;
import com.tekmentor.productservice.model.Customer;
import com.tekmentor.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConfig config;

    @GetMapping("/products")
    public String showProducts(){
        log.info("Showing the products {}",config.getCustomerService());
            productService.showProducts();
        return "Products";
    }
}
