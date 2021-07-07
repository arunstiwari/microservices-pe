### 1. Add the following dependency in the `pom`
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

### 2. Add the annotation `@EnableEurekaClient` to the `ApiGatewayApplication`
```java

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```
### 3. Add the following properties to the `application.yaml`
```yaml
spring:
  application:
    name: API-GATEWAY
  cloud:
    gateway:
      routes:
      - id: order-service
        uri: lb://ORDER-SERVICE
        predicates:
        - Path= /orders/**
      - id: payment-service
        uri: lb://PAYMENT-SERVICE
        predicates:
        - Path= /payments/**

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    hostname: localhost
server:
  port: 8989
```
+ There are two parts to this `properties configuration`
    + First part is adding the property to support `registering with eureka client`
    + Second part is configuring the `routes` to redirect to the underlying `apis`

+ Now we do not need to modify the `Order` and `Payment` service anyway
+ Now we start the `APIGatewayApplication` and then use the url `http://localhost:8989/orders` to post the order to `OrderService`
```json
{
    "order": {
    "name": "order-123",
    "qty": 240,
    "price":3000
    },
    "payment": {
        "amount": 3000,
        "orderId": 123
    }
}
```
### References
+ https://cloud.spring.io/spring-cloud-gateway/reference/html/
+ https://ordina-jworks.github.io/spring/2021/02/01/springCloudCircuitBreaker.html#differences-resilience4j-with-netflix-hystrix-and-spring-retry
+ https://github.com/ThomasVitale/java-advent-2020-spring-resilience4j/blob/main/greeting-service/src/main/resources/application.yml
+ https://www.javaadvent.com/2020/12/resilient-applications-spring-resilience4j.html
+ Spring cloud gateway + resilience4j -> https://www.youtube.com/watch?v=5eAQLygfmbg
---
# Resilience4j Integration
### 1. Add the following dependency in the `pom.xml` file as shown below
```xml
 <dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-circuitbreaker-reactor-resilience4j</artifactId>
</dependency>
```

### 2. Now let us add the `FallbackController` class with `two fallback endpoints`
````java
@RestController
public class FallbackController {
    @RequestMapping("/orderFallBack")
    public Mono<String> orderServiceFallBack() {
        return Mono.just("Order Service is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/paymentFallback")
    public Mono<String> paymentServiceFallBack() {
        return Mono.just("Payment Service is taking too long to respond or is down. Please try again later");
    }
}
````

### 3. Next we go and create the `RouteConfig` class and abstract the routing properties from the `application.yml` to the `java` class
```java
@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator ecommerceRoutes(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes()
                .route("order-service", p -> p
                        .path("/orders/**")
                        .uri("lb://ORDER-SERVICE")
                )
                .route("payment-service", p -> p
                                .path("/payments/**")
                                .uri("lb://PAYMENT-SERVICE")
                )
                .build();
    }
}
```
+ Clean up the `application.yml` file as shown below
```yaml
spring:
  application:
    name: API-GATEWAY

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:8761/eureka/
  instance:
    hostname: localhost
server:
  port: 8989


```
+ Next we add the `circuit breaker` filter to the `RouteConfig` class with `fallback option for order service`
```java
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
                .route("payment-service", p -> p
                                .path("/payments/**")
                                .uri("lb://PAYMENT-SERVICE")
                )
                .build();
    }
}
```

+ Now start the application and inorder to test whether the fallback option works correctly or not,
stop the Order service and post the order to the url `http://localhost:8989/orders` following json
```json
{
    "order": {
    "name": "order-223",
    "qty": 240,
    "price":3000
    },
    "payment": {
        "amount": 3000,
        "orderId": 123
    }
}
```
+ We should get the response as 
```text
Order Service is taking too long to respond or is down. Please try again later
```

---
# Spring Cloud Gateway concepts
+ `Route`
    + Basic building block of the gateway
    + It is defined by an ID, a destination URI, a collection of predicates and a collection of filters
    + A route is matched if the aggregate predicate is true
+ `Predicate`
  + This is a java 8 function predicate
  + Input type is a `Spring framework ServerWebExchange`. This lets you match on anything from the Http Request, such as headers or parameters
+ `Filter`
  + These are instances of `Spring Framework GatewayFilter` that have been constructed with a specific factory
  + Here you can modify requests and responses before or after sending the downstream request

