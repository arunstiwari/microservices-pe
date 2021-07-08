package com.tekmentor.cartservice.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "carts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartConfig {
    private String customerservice;
    private String productservice;
}
