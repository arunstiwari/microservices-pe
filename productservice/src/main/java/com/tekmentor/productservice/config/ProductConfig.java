package com.tekmentor.productservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
@ConfigurationProperties(prefix = "products")
public class ProductConfig {
    private String customerService;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
