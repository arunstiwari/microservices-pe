package com.tekmentor.orderservice.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
@ConfigurationProperties(prefix = "orders")
public class OrderConfig {
    private String cartservice;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
