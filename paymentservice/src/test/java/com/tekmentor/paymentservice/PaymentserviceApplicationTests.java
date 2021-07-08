package com.tekmentor.paymentservice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PaymentserviceApplicationTests {

   @RegisterExtension
    public StubRunnerExtension stubRunner = new StubRunnerExtension()
           .downloadStub("com.tekmentor","customersvc","0.0.1-SNAPSHOT", "stubs")
           .withPort(9098)
           .stubsMode(StubRunnerProperties.StubsMode.LOCAL);

   @Test
    public void shouldReturnCustomerForAnIdwithValue1(){
       RestTemplate restTemplate = new RestTemplate();
       ResponseEntity<Customer> customerResponseEntity = restTemplate.getForEntity("http://localhost:9098/customers/1", Customer.class);
       assertEquals("1", customerResponseEntity.getBody().getId());
       assertEquals("Customer-1", customerResponseEntity.getBody().getName());

   }

}
