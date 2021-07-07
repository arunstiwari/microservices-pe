package com.tekmentor.productservice.service;

import com.tekmentor.productservice.config.ProductConfig;
import com.tekmentor.productservice.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ProductConfig config;

    public String showProducts(){
        List<Customer> customers = restTemplate.getForObject(config.getCustomerService() + "/customers",
                List.class);
        log.info("customers : {}",customers);
        return "Products";
    }
}
