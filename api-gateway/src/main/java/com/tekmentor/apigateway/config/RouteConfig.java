package com.tekmentor.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator ecommerceRoutes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("order-service", p -> p
                        .path("/orders/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("orders fallback").setFallbackUri("/orderFallBack")))
                        .uri("lb://ORDER-SERVICE")
                )

                .route("product-service", p -> p
                        .path("/products/**")
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route("customer-service", p -> p
                        .path("/customers/**")
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .route("category-service", p -> p
                        .path("/categories/**")
                        .uri("lb://PRODUCT-SERVICE")
                ).route("cart-service", p -> p.path("/carts/**")
                        .uri("lb://CART-SERVICE")
                ).route("basket-service", p -> p.path("/checkout/**")
                        .uri("lb://BASKET-SERVICE")
                ).route("payment-service", p -> p.path("/payments/**")
                        .uri("lb://PAYMENT-SERVICE")
                ).route("notification-service", p -> p.path("/notifications/**")
                        .uri("lb://NOTIFICATION-SERVICE")
                ).route("shipping-service", p -> p.path("/shippings/**")
                        .uri("lb://SHIPPING-SERVICE")
                )
                .build();
    }
}
