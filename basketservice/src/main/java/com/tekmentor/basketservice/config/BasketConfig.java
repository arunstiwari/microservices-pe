package com.tekmentor.basketservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "baskets")
@Data
public class BasketConfig {
    private String orderservice;
    private String paymentservice;
    private String shippingservice;
    private String notificationservice;
    private String cartservice;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
