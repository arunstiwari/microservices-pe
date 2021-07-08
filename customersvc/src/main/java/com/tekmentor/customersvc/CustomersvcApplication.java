package com.tekmentor.customersvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CustomersvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersvcApplication.class, args);
    }

}
