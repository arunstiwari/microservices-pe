package com.tekmentors.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator ecommerceRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route("product-service", p -> p
                                .path("/products/**")
//                        .filters(f -> f.circuitBreaker(c -> c.setName("orders fallback").setFallbackUri("/orderFallBack")))
                                .uri("lb://PRODUCT-SERVICE")
                )
                .route("customer-service", p -> p
                        .path("/customers/**")
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .build();
    }

}